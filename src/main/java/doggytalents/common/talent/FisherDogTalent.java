package doggytalents.common.talent;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import doggytalents.TalentsOptions;
import doggytalents.DoggyTalents;
import doggytalents.api.enu.WetSource;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.TalentOption;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import doggytalents.common.util.TagUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class FisherDogTalent extends TalentInstance {

    private int cookCooldown = 10;
    private boolean renderHat = true;

    public FisherDogTalent(Talent talentIn, int levelIn) {
        super(talentIn, levelIn);
    }

    @Override
    public void tick(AbstractDog dogIn) {
        if (dogIn.level().isClientSide)
            return;
        if (cookCooldown > 0)
            --cookCooldown;
    }

    @Override
    public void onShakingDry(AbstractDog dogIn, WetSource source) {
        if (dogIn.level().isClientSide) { // On client do nothing
            return;
        }

        if (!source.isWaterBlock())
            return;
        
        int r_fish = dogIn.getRandom().nextInt(15);
        if (r_fish >= this.level() * 2)
            return;
        
        var fishItem = getRandomFishingLoot(dogIn);
        var fishStack = getFishedStack(dogIn, fishItem);

        dogIn.spawnAtLocation(fishStack);
    }

    private ItemStack getRandomFishingLoot(AbstractDog dog) {
        if (!(dog.level() instanceof ServerLevel sLevel))
            return ItemStack.EMPTY;
        var loot_table = BuiltInLootTables.FISHING_FISH;
        var r = dog.getRandom();
        if (this.level() >= 5 && r.nextFloat() < 0.0125) {
            loot_table = BuiltInLootTables.FISHING_TREASURE;
        }
        var loot_param = new LootParams.Builder(sLevel)
            .create(LootContextParamSets.EMPTY);
        var loot_list = sLevel.getServer().reloadableRegistries()
            .getLootTable(loot_table)
            .getRandomItems(loot_param);
        if (loot_list.isEmpty())
            return ItemStack.EMPTY;
        var ret = loot_list.get(r.nextInt(loot_list.size()));
        return ret.copy(); 
    }

    private ItemStack getFishedStack(AbstractDog dog, ItemStack raw_stack) {
        raw_stack = mayCookFish(dog, raw_stack);
        return raw_stack;
    }

    private ItemStack mayCookFish(AbstractDog dog, ItemStack fish_raw) {
        if (cookCooldown > 0)
            return fish_raw;
        var hellhound_optional = dog.getTalent(DoggyTalents.HELL_HOUND);
        if (!hellhound_optional.isPresent())
            return fish_raw;
        if (!(hellhound_optional.get() instanceof HellHoundTalent hellhound))
            return fish_raw;
        if (!hellhound.canGenerateHeat())
            return fish_raw;

        if (hellhound.level() < hellhound.getTalent().getMaxLevel()) {
            float r = dog.getRandom().nextFloat();
            if (r > 0.75f)
                return fish_raw;
        }

        cookCooldown = 10;

        return tryCookFish(dog, fish_raw);
    }

    private ItemStack tryCookFish(AbstractDog dog, ItemStack fish_raw) {
        var recipeMan = dog.level().getRecipeManager();
        var recipeOptional = recipeMan.getRecipeFor(RecipeType.SMELTING, 
            new SingleRecipeInput(fish_raw.copy()), dog.level());
        if (!recipeOptional.isPresent())
            return fish_raw;
        var recipe = recipeOptional.get();
        var resultStack = recipe.value().getResultItem(dog.level().registryAccess());
        if (resultStack == null || resultStack.isEmpty())
            return fish_raw;
        return resultStack.copy();
    }

    @Override
    public void readFromNBT(AbstractDog dogIn, CompoundTag compound) {
        super.readFromNBT(dogIn, compound);

        this.renderHat = compound.getBoolean("renderHat");
    }

    @Override
    public void writeToNBT(AbstractDog dogIn, CompoundTag compound) {
        super.writeToNBT(dogIn, compound);

        compound.putBoolean("renderHat", renderHat);
    }

    @Override
    public Object getTalentOption(TalentOption<?> entry) {
        if (entry == TalentsOptions.FISHER_DOG_RENDER.get()) {
            return this.renderHat;
        }
        return null;
    }

    @Override
    public void setTalentOption(TalentOption<?> entry, Object data) {
        if (entry == TalentsOptions.FISHER_DOG_RENDER.get()) {
            this.renderHat = (Boolean) data;
        }
    }

    @Override
    public Collection<TalentOption<?>> getAllTalentOptions() {
        return List.of(TalentsOptions.FISHER_DOG_RENDER.get());
    }

    public boolean canRenderHat() { return this.level() >= this.getTalent().getMaxLevel(); }
    public boolean renderHat() { return this.renderHat; }
    public void setRenderHat(boolean hat) { this.renderHat = hat; }    

}
