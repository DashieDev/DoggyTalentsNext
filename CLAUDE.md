# DoggyTalentsNext Migration Plan: Minecraft 1.21 → 26.1.2

**Status**: Mod Loading — Runtime Warnings Remaining
**Date**: 2026-04-25
**Target**: NeoForge 26.1.2.x for Minecraft 26.1

## Executive Summary

This document outlines the complete migration strategy for DoggyTalentsNext from Minecraft 1.21.1 to Minecraft 26.1.2. The migration addresses breaking API changes across 8 major systems.

### Current Migration Status

✅ **Phase 1 - Build System** (COMPLETED)
- ✅ Gradle upgraded to 9.1.0
- ✅ ModDevGradle 2.0.141 configured
- ✅ NeoForge version updated to 26.1.2.11-beta
- ✅ Java 25
- ✅ pack_format updated to 84 with min/max fields
- ✅ Block/item registration migrated to `DeferredRegister.createBlocks/createItems()`

✅ **Phase 3 - GUI Rendering** (COMPLETED)
- ✅ All RenderLayer subclasses: `render()` → `submit()`, typed on `DogRenderState`
- ✅ Entity renderers updated: `cameraOrientation()` → `camera.rotation()`, removed `getTextureLocation` overrides
- ✅ Model files: `copyFrom()` → `loadPose/storePose()`, removed illegal `renderToBuffer` overrides
- ✅ `DTNModelLoader`: `SimpleJsonResourceReloadListener` → `SimplePreparableReloadListener`
- ✅ `DTNAnimationCodec`: `Keyframe.target()` → `preTarget()`
- ✅ Screen widgets: `renderWidget` → `extractWidgetRenderState/extractContents`, `onPress()` → `onPress(InputWithModifiers)`
- ✅ Blit calls: old 7-arg → new 10-arg with `RenderPipelines.GUI_TEXTURED`
- ✅ `DogMouthItemRenderer`: `ItemInHandRenderer` → `ItemModelResolver/ItemStackRenderState`
- ✅ `FoodBowlScreen`: `imageHeight` final, `extractContents/extractLabels` signatures fixed

✅ **Phase 6 - Attribute Modifiers** (ALREADY COMPLIANT — no changes needed)

⚠️ **Remaining Runtime Warnings** (see Known Issues below)
- `@OnlyIn` annotation member-stripping no longer active in NeoForge 26.1.2

---

## Remaining Work

---

## Phase 2: Inventory System Migration (LOWER PRIORITY — ItemStackHandler still works)

### 2.1 Replace ItemStackHandler with ResourceHandler

**Impact**: 5 core inventory files, ~500 lines of code

#### Files to Migrate:

1. `/src/api/java/doggytalents/api/inferface/DogArmorItemHandler.java`
2. `/src/main/java/doggytalents/common/inventory/DogArmorItemHandlerImpl.java`
3. `/src/main/java/doggytalents/common/inventory/PackPuppyItemHandler.java`
4. `/src/main/java/doggytalents/common/inventory/TreatBagItemHandler.java`
5. `/src/main/java/doggytalents/common/inventory/DoggyToolsItemHandler.java`

#### Migration Pattern:

**OLD CODE** (1.21):
```java
package doggytalents.api.inferface;

import net.neoforged.neoforge.items.ItemStackHandler;

public abstract class DogArmorItemHandler extends ItemStackHandler {

    public DogArmorItemHandler(AbstractDog dog) {
        super(4); // 4 armor slots
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        // Logic here
    }
}
```

**NEW CODE** (26.1.2):
```java
package doggytalents.api.inferface;

import net.neoforged.neoforge.items.item.ItemResource;
import net.neoforged.neoforge.items.wrapper.ResourceHandler;
import net.neoforged.neoforge.common.transaction.Transaction;

public abstract class DogArmorItemHandler implements ResourceHandler<ItemResource> {

    protected final List<ItemStack> stacks;
    protected final AbstractDog dog;

    public DogArmorItemHandler(AbstractDog dog) {
        this.dog = dog;
        this.stacks = NonNullList.withSize(4, ItemStack.EMPTY);
    }

    @Override
    public int size() {
        return 4;
    }

    @Override
    public long getCapacityAsLong(int slot) {
        return 64; // Stack size
    }

    @Override
    public ItemResource getResource(int slot) {
        ItemStack stack = stacks.get(slot);
        return stack.isEmpty() ? null : ItemResource.of(stack);
    }

    @Override
    public long getAmountAsLong(int slot) {
        return stacks.get(slot).getCount();
    }

    @Override
    public long extract(int slot, ItemResource resource, long amount, Transaction tx) {
        if (slot < 0 || slot >= size()) return 0;

        ItemStack existing = stacks.get(slot);
        if (existing.isEmpty() || !resource.matches(existing)) return 0;

        long extracted = Math.min(amount, existing.getCount());

        tx.addOuterCloseCallback(result -> {
            if (result.wasCommitted()) {
                existing.shrink((int) extracted);
                if (existing.isEmpty()) {
                    stacks.set(slot, ItemStack.EMPTY);
                }
                onContentsChanged(slot);
            }
        });

        return extracted;
    }

    @Override
    public long insert(int slot, ItemResource resource, long amount, Transaction tx) {
        if (slot < 0 || slot >= size()) return 0;
        if (!isItemValid(slot, resource.toStack())) return 0;

        ItemStack existing = stacks.get(slot);

        if (existing.isEmpty()) {
            long toInsert = Math.min(amount, getCapacityAsLong(slot));

            tx.addOuterCloseCallback(result -> {
                if (result.wasCommitted()) {
                    stacks.set(slot, resource.toStack((int) toInsert));
                    onContentsChanged(slot);
                }
            });

            return toInsert;
        } else if (resource.matches(existing)) {
            long toInsert = Math.min(amount, getCapacityAsLong(slot) - existing.getCount());

            tx.addOuterCloseCallback(result -> {
                if (result.wasCommitted()) {
                    existing.grow((int) toInsert);
                    onContentsChanged(slot);
                }
            });

            return toInsert;
        }

        return 0;
    }

    protected abstract boolean isItemValid(int slot, ItemStack stack);
    protected abstract void onContentsChanged(int slot);
}
```

#### Transactional Logic Updates

**Every inventory operation must use transactions**:

```java
// OLD
ItemStack extracted = dogInventory.extractItem(slot, 1, false);
if (!extracted.isEmpty()) {
    dog.heal(4.0F);
}

// NEW
try (Transaction tx = Transaction.openRoot()) {
    ItemResource resource = dogInventory.getResource(slot);
    if (resource != null) {
        long extracted = dogInventory.extract(slot, resource, 1, tx);
        if (extracted > 0) {
            dog.heal(4.0F);
            tx.commit();
        }
    }
}
```

#### Legacy Compatibility Wrappers

For code that cannot be immediately refactored:

```java
import net.neoforged.neoforge.items.wrapper.IItemHandler;

// Wrap ResourceHandler as legacy IItemHandler
IItemHandler legacyHandler = IItemHandler.of(resourceHandler);
```

**⚠️ WARNING**: Wrappers will be removed in future NeoForge versions. Migrate ASAP.

---

## Phase 3: GUI Rendering System (HIGH PRIORITY)

### 3.1 Screen Method Refactoring

**Impact**: 28 screen files

#### Files Requiring Changes:

**Main Screens**:
- `DogInventoriesScreen.java`
- `DogArmorScreen.java`
- `DogNewInfoScreen/DogNewInfoScreen.java`
- `PackPuppyScreen.java`
- `TreatBagScreen.java`
- `WhistleScreen.java`
- `CanineTrackerScreen.java`
- `ConductingBoneScreen.java`
- ... (20 more files)

#### Method Rename Mapping:

| Old Method (1.21)                          | New Method (26.1.2)                                  |
|--------------------------------------------|-----------------------------------------------------|
| `render(GuiGraphics, int, int, float)`     | `extractRenderState(GuiGraphicsExtractor, ...)`     |
| `renderBackground(...)`                    | `extractBackground(...)`                            |
| `renderBg(...)`                           | `extractBackground(...)`                            |
| `renderLabels(...)`                       | `extractLabels(...)`                                |

#### Migration Example:

**OLD CODE**:
```java
public class DogInventoriesScreen extends AbstractContainerScreen<DogInventoriesContainer> {

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, 8, 6, 4210752, false);
        graphics.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }
}
```

**NEW CODE**:
```java
public class DogInventoriesScreen extends AbstractContainerScreen<DogInventoriesContainer> {

    @Override
    protected void extractBackground(GuiGraphicsExtractor extractor, float partialTicks, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        extractor.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor extractor, int mouseX, int mouseY) {
        extractor.drawString(this.font, this.title, 8, 6, 4210752, false);
        extractor.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
    }
}
```

### 3.2 Entity Render State Pattern

For screens that render dog models (e.g., Talent GUI):

**Create Render State Record**:
```java
public record DogRenderState(
    ResourceLocation texture,
    DogPose pose,
    float ageInTicks,
    int talentLevel,
    List<AccessoryRenderData> accessories
) {}
```

**Implement State Extraction**:
```java
public class DogRenderer extends EntityRenderer<Dog> {

    @Override
    public DogRenderState createRenderState() {
        return new DogRenderState(null, DogPose.STANDING, 0, 0, List.of());
    }

    @Override
    public void extractRenderState(Dog dog, DogRenderState state, float partialTick) {
        state = new DogRenderState(
            dog.getTexture(),
            dog.getPose(),
            dog.tickCount + partialTick,
            dog.getTalentLevel(),
            dog.getAccessories()
        );
    }
}
```

---

## Phase 4: Networking System Refactor (MEDIUM-HIGH PRIORITY)

### 4.1 Packet System Migration

**Impact**: 73+ packet classes

#### Current Architecture (Compatibility Wrapper):

```
PacketHandler (registers 73+ packets)
    ↓
DTNNetworkHandler (compatibility layer)
    ↓
CustomPacketPayload + StreamCodec (26.1.2 API)
```

#### Target Architecture (Direct):

```
RegisterPayloadHandlersEvent
    ↓
PayloadRegistrar
    ↓
CustomPacketPayload records with StreamCodec
```

#### Migration Steps:

**Step 1**: Convert packet classes to records

**OLD**:
```java
public class DogModePacket extends DogPacket<DogModeData> {
    @Override
    public void encode(DogModeData data, FriendlyByteBuf buf) {
        super.encode(data, buf);
        buf.writeInt(data.mode.getIndex());
    }

    @Override
    public DogModeData decode(FriendlyByteBuf buf) {
        int entityId = buf.readInt();
        int modeIndex = buf.readInt();
        return new DogModeData(entityId, DogMode.byIndex(modeIndex));
    }

    @Override
    public void handleDog(Dog dog, DogModeData data, Supplier<Context> ctx) {
        if (!dog.canInteract(ctx.get().getSender())) return;
        dog.setMode(data.mode);
    }
}
```

**NEW**:
```java
public record DogModePayload(int dogId, int modeIndex) implements CustomPacketPayload {

    public static final Type<DogModePayload> TYPE =
        new Type<>(ResourceLocation.fromNamespaceAndPath("doggytalents", "dog_mode"));

    public static final StreamCodec<ByteBuf, DogModePayload> STREAM_CODEC =
        StreamCodec.composite(
            ByteBufCodecs.VAR_INT, DogModePayload::dogId,
            ByteBufCodecs.VAR_INT, DogModePayload::modeIndex,
            DogModePayload::new
        );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(DogModePayload payload, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.flow() == PacketFlow.SERVERBOUND) {
                ServerPlayer sender = (ServerPlayer) ctx.player();
                Entity entity = sender.level().getEntity(payload.dogId());
                if (entity instanceof Dog dog && dog.canInteract(sender)) {
                    dog.setMode(DogMode.byIndex(payload.modeIndex()));
                }
            }
        });
    }
}
```

**Step 2**: Register payloads in event

**Delete**: `PacketHandler.java`, `DTNNetworkHandler.java` (compatibility wrappers)

**Create**: `DoggyTalentsNetworking.java`

```java
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DoggyTalentsNetworking {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(Constants.PROTOCOL_VERSION);

        // Server-bound (client → server)
        registrar.playToServer(
            DogModePayload.TYPE,
            DogModePayload.STREAM_CODEC,
            DogModePayload::handle
        );

        registrar.playToServer(
            DogObeyPayload.TYPE,
            DogObeyPayload.STREAM_CODEC,
            DogObeyPayload::handle
        );

        // Client-bound (server → client)
        registrar.playToClient(
            DogSyncDataPayload.TYPE,
            DogSyncDataPayload.STREAM_CODEC,
            DogSyncDataPayload::handle
        );

        // Bidirectional
        registrar.playBidirectional(
            DogTexturePayload.TYPE,
            DogTexturePayload.STREAM_CODEC,
            DogTexturePayload::handle
        );

        // ... register remaining 70+ payloads
    }
}
```

**Step 3**: Use RegistryFriendlyByteBuf for ItemStack packets

```java
public record DogEquipmentPayload(int dogId, ItemStack equipment) implements CustomPacketPayload {

    // Use RegistryFriendlyByteBuf for ItemStack codec
    public static final StreamCodec<RegistryFriendlyByteBuf, DogEquipmentPayload> STREAM_CODEC =
        StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            DogEquipmentPayload::dogId,
            ItemStack.STREAM_CODEC,  // Requires RegistryFriendlyByteBuf
            DogEquipmentPayload::equipment,
            DogEquipmentPayload::new
        );

    // ... rest of implementation
}
```

### 4.2 Packet Migration Checklist

73 packets to migrate. Create subtasks for each:

- [ ] `DogModePayload` (replaces DogModePacket)
- [ ] `DogObeyPayload` (replaces DogObeyPacket)
- [ ] `DogTalentPayload` (replaces DogTalentPacket)
- [ ] `DogNamePayload` (replaces DogNamePacket)
- [ ] `DogTexturePayload` (replaces DogTexturePacket)
- [ ] ... (68 more packets)

---

## Phase 5: Data Components Migration (MEDIUM PRIORITY)

### 5.1 Replace NBT with DataComponentType

**Impact**: All custom ItemStack data (whistles, accessories, collars, artifacts)

#### Create Data Components Registry

**File**: `/src/main/java/doggytalents/common/register/DoggyDataComponents.java`

```java
@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DoggyDataComponents {

    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
        DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);

    // Whistle state
    public static final Supplier<DataComponentType<WhistleState>> WHISTLE_STATE =
        DATA_COMPONENTS.register("whistle_state", () ->
            DataComponentType.<WhistleState>builder()
                .persistent(WhistleState.CODEC)
                .networkSynchronized(WhistleState.STREAM_CODEC)
                .build()
        );

    // Dog collar color
    public static final Supplier<DataComponentType<Integer>> COLLAR_COLOR =
        DATA_COMPONENTS.register("collar_color", () ->
            DataComponentType.<Integer>builder()
                .persistent(Codec.INT)
                .networkSynchronized(ByteBufCodecs.VAR_INT)
                .build()
        );

    // Accessory data
    public static final Supplier<DataComponentType<AccessoryData>> ACCESSORY_DATA =
        DATA_COMPONENTS.register("accessory_data", () ->
            DataComponentType.<AccessoryData>builder()
                .persistent(AccessoryData.CODEC)
                .networkSynchronized(AccessoryData.STREAM_CODEC)
                .build()
        );

    // Artifact data
    public static final Supplier<DataComponentType<ArtifactData>> ARTIFACT_DATA =
        DATA_COMPONENTS.register("artifact_data", () ->
            DataComponentType.<ArtifactData>builder()
                .persistent(ArtifactData.CODEC)
                .networkSynchronized(ArtifactData.STREAM_CODEC)
                .build()
        );

    // Dog owner UUID (for items that store owner)
    public static final Supplier<DataComponentType<UUID>> DOG_OWNER =
        DATA_COMPONENTS.register("dog_owner", () ->
            DataComponentType.<UUID>builder()
                .persistent(Codec.UUID)
                .networkSynchronized(ByteBufCodecs.UUID)
                .build()
        );
}
```

#### Migration Pattern

**OLD (NBT)**:
```java
// Writing
ItemStack whistle = new ItemStack(Items.WHISTLE);
CompoundTag tag = whistle.getOrCreateTag();
tag.putInt("mode", 1);
tag.putString("name", "Home");

// Reading
if (whistle.hasTag() && whistle.getTag().contains("mode")) {
    int mode = whistle.getTag().getInt("mode");
}
```

**NEW (Data Components)**:
```java
// Writing
ItemStack whistle = new ItemStack(Items.WHISTLE);
whistle.set(DoggyDataComponents.WHISTLE_STATE.get(),
    new WhistleState(1, "Home"));

// Reading
WhistleState state = whistle.getOrDefault(
    DoggyDataComponents.WHISTLE_STATE.get(),
    WhistleState.DEFAULT
);
int mode = state.mode();
```

### 5.2 Define Data Records

```java
public record WhistleState(int mode, String name) {
    public static final WhistleState DEFAULT = new WhistleState(0, "");

    public static final Codec<WhistleState> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("mode").forGetter(WhistleState::mode),
            Codec.STRING.fieldOf("name").forGetter(WhistleState::name)
        ).apply(instance, WhistleState::new)
    );

    public static final StreamCodec<ByteBuf, WhistleState> STREAM_CODEC =
        StreamCodec.composite(
            ByteBufCodecs.VAR_INT, WhistleState::mode,
            ByteBufCodecs.STRING_UTF8, WhistleState::name,
            WhistleState::new
        );
}
```

---

## Phase 6: Attribute Modifiers (LOW PRIORITY - ALREADY COMPLIANT)

### 6.1 Status Check

✅ **GOOD NEWS**: The codebase already uses `ResourceLocation` for attribute modifiers!

**Verified in**: `/src/api/java/doggytalents/api/inferface/AbstractDog.java:46-67`

```java
AttributeModifier currentModifier = attributeInst.getModifier(modifierLoc);  // ✅ ResourceLocation
attributeInst.removeModifier(modifierLoc);  // ✅ ResourceLocation
```

**No changes needed** for this phase.

---

## Phase 7: Codec Updates (LOW PRIORITY)

### 7.1 Replace Deprecated ExtraCodecs

**Find**: All uses of `ExtraCodecs`
**Replace with**: Standard `Codec` methods

```java
// OLD
ExtraCodecs.strictOptionalField(codec, "fieldName")

// NEW
codec.optionalFieldOf("fieldName")  // Now strict by default in 26.1.2
```

**Files likely affected**: Talent configuration, recipe serializers

---

## Phase 8: Testing & Validation

### 8.1 Unit Tests

Create tests for:
- [ ] Inventory transactions (commit/abort scenarios)
- [ ] Packet serialization/deserialization
- [ ] Data component persistence
- [ ] Screen rendering (mock tests)

### 8.2 Integration Tests

- [ ] Dog inventory operations (add/remove armor)
- [ ] Multiplayer synchronization (spawn dog, sync to clients)
- [ ] Save/load world with dogs
- [ ] GUI interactions (all 28 screens)
- [ ] Talent system functionality

### 8.3 Performance Tests

- [ ] Inventory transaction overhead vs. old system
- [ ] Rendering performance (extraction pattern)
- [ ] Network packet size comparison

---

## Critical Files Checklist

### Inventory System (5 files)
- [ ] `DogArmorItemHandler.java` (API)
- [ ] `DogArmorItemHandlerImpl.java`
- [ ] `PackPuppyItemHandler.java`
- [ ] `TreatBagItemHandler.java`
- [ ] `DoggyToolsItemHandler.java`

### Screen/GUI (28 files)
- [x] All screens migrated (commit c848bd73)

### Networking (73+ files)
- [ ] Delete `PacketHandler.java`
- [ ] Delete `DTNNetworkHandler.java`
- [ ] Create `DoggyTalentsNetworking.java`
- [ ] Convert 73+ packet classes to payload records

### Data Components
- [ ] Create `DoggyDataComponents.java`
- [ ] Migrate whistle item data
- [ ] Migrate accessory item data
- [ ] Migrate artifact item data

---

## Known Issues & Blockers

### 1. @OnlyIn Annotation Warnings (ACTIVE)

NeoForge 26.1.2 no longer strips members annotated with `@OnlyIn` at runtime. The following usages log ERROR-level warnings on startup:

- `doggytalents.DoggyTalentsNext#clientSetup` (method)
- `doggytalents.client.block.model.DogBedModel` (class)
- `doggytalents.client.block.model.DogBedItemOverride` (class)
- `doggytalents.client.entity.model.animation.DogKeyframeAnimations` (class)
- `doggytalents.common.block.DogBedBlock#addBedTooltip` (method)
- `doggytalents.common.entity.Dog#getInterestedAngle` (method)
- `doggytalents.common.entity.Dog#getShakeAngle` (method)
- `doggytalents.common.entity.Dog#getShadingWhileWet` (method)
- `doggytalents.common.item.TreatBagItem#appendHoverText` (method)

**Fix**: Remove `@OnlyIn(Dist.CLIENT)` from each location. Client-only methods on common classes should instead use `DistExecutor` or be moved to a `@EventBusSubscriber(value = Dist.CLIENT)` class. Client-only classes in `doggytalents.client.*` do not need the annotation at all since they are already in a client-only package.

### 2. API Coverage Gaps

The following NeoForge 26.1.2 APIs are mentioned in the migration plan but not yet confirmed:
- `MutableQuad` usage for BakedQuad manipulation
- `SubmitNodeCollector` for render state submission

**Mitigation**: Consult NeoForge docs when stable release is available.

---

## Timeline Estimate


| Phase | Complexity | Status |
|-------|------------|--------|
| Phase 1: Build System | Low | ✅ DONE |
| Phase 2: Inventory | High | Working (ItemStackHandler still valid) |
| Phase 3: GUI Rendering | Medium-High | ✅ DONE |
| Phase 4: Networking | High | Compatibility wrapper in place — cleanup pending |
| Phase 5: Data Components | Medium | Pending |
| Phase 6: Attribute Modifiers | None | ✅ DONE |
| Phase 7: Codec Updates | Low | Pending |
| Phase 8: Testing | Ongoing | Pending |

---

## Next Steps

1. **Remove `@OnlyIn` annotations** — highest-priority cleanup; causes ERROR log spam on every startup.
2. **Phase 4 Networking** — remove `PacketHandler`/`DTNNetworkHandler` wrappers; convert packets to `CustomPacketPayload` records.
3. **Phase 5 Data Components** — replace NBT usage in whistle, accessory, and artifact items.
4. **Phase 7 Codec Updates** — replace any remaining `ExtraCodecs` calls.


---

## Support Resources

- **NeoForge Documentation**: https://docs.neoforged.net/
- **NeoForge Discord**: https://discord.neoforged.net/
- **Migration Guide**: https://neoforged.net/news/26.1release/
- **Transfer Rework Guide**: https://neoforged.net/news/21.9-transfer-rework/

---

**Document Version**: 1.2
**Last Updated**: 2026-04-25
**Status**: Mod loading on NeoForge 26.1.2.11-beta; @OnlyIn warnings pending cleanup
