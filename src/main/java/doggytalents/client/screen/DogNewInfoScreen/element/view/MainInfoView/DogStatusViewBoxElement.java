package doggytalents.client.screen.DogNewInfoScreen.element.view.MainInfoView;

import java.util.List;
import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.client.entity.render.DogScreenOverlays;
import doggytalents.api.feature.DogSize;
import doggytalents.api.feature.DogLevel.Type;
import doggytalents.client.screen.PetSelectScreen;
import doggytalents.client.screen.ScreenUtil;
import doggytalents.client.screen.framework.ToolTipOverlayManager;
import doggytalents.client.screen.framework.element.AbstractElement;
import doggytalents.client.screen.framework.widget.FlatButton;
import doggytalents.common.entity.Dog;
import doggytalents.common.lib.Resources;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;

public class DogStatusViewBoxElement extends AbstractElement {

    private Dog dog;
    private Font font;
    private FlatButton petButton;

    public DogStatusViewBoxElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        var mc = this.getScreen().getMinecraft();
        this.font = mc.font;
    }

    @Override
    public AbstractElement init() {
        petButton = new FlatButton(0, 0, 30, 50, Component.empty(), (b) -> {
            PetSelectScreen.open();
        }) {
            @Override
            public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pTicks) {
                // TODO Auto-generated method stub
                // super.renderWidget(graphics, mouseX, mouseY, pTicks);
                if (this.isHovered) {
                    ToolTipOverlayManager.get().setComponents(
                        List.of(Component.translatable("doggui.home.pet_hint", 
                            dog.getGenderPronoun())));
                }
            }
        };
        petButton.setX(this.getRealX() + this.getSizeX()/2 - petButton.getWidth()/2);
        petButton.setY(this.getRealY() + this.getSizeY()/2 - petButton.getHeight()/2 + 10);
        this.addChildren(petButton);
        return this;
    }

    @Override
    public void renderElement(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        drawDogLevelKanji(graphics, mouseX, mouseY, partialTicks);

        int e_mX = this.getRealX() + this.getSizeX()/2; 
        int e_mY = this.getRealY() + this.getSizeY()/2; 

        renderVariantIcon(graphics, dog, this.getRealX() + this.getSizeX() - 16 - 3, this.getRealY() - 4);

        renderDogInside(graphics, dog, e_mX, e_mY + 32, 50, e_mX - mouseX, e_mY - mouseY);

        this.renderHealthBar(graphics, dog, e_mX - 41, this.getRealY() + this.getSizeY() - 10);

        var points = this.dog.getSpendablePoints();

        renderHungerStatusStr(graphics, this.dog, this.getRealX(), this.getRealY());
    }

    private void drawDogLevelKanji(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        //RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        //RenderSystem.setShaderTexture(0, getKanjiDogLevel(this.dog));
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 128;
        graphics.blit(getKanjiDogLevel(this.dog), this.getRealX() + this.getSizeX()/2 - imgeSize/2, 
            this.getRealY() + this.getSizeY()/2 - imgeSize/2, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        RenderSystem.disableBlend();
    }

    public static ResourceLocation getKanjiDogLevel(Dog dog) {
        var dogLevel = dog.getDogLevel();
        if (dogLevel.isFullKami())
            return Resources.KANJI_KAMI;
        int level_normal = dogLevel.getLevel(Type.NORMAL);
        var ret = Resources.KANJI_NORMAL;
        if (level_normal >= 20) 
            ret = Resources.KANJI_SUPER;
        if (level_normal >= 40)
            ret = Resources.KANJI_MASTER;
        if (level_normal >= 60)
            ret = Resources.KANJI_KAMI;
        return ret;
    }

    public static void renderDogInside(GuiGraphics graphics, Dog dog, 
        int dog_mX, int dog_mY, int size, int lookX, int lookY) {
        var currentDogSize = dog.getDogSize();
        boolean dogTooBig = currentDogSize.getId() > DogSize.MODERATO.getId();
        if (dogTooBig) {
            dog.setDogSize(DogSize.MODERATO);
        }

        var currentDogName = dog.getCustomName();
        String currentDogNameStr = "";
        boolean nameTooLong = false;
        if (currentDogName != null) {
            currentDogNameStr = currentDogName.getString();
            nameTooLong = currentDogNameStr.length() > 14;
        }
        if (nameTooLong) {
            var tempName = currentDogNameStr.substring(0, 13) + " ..";
            dog.setDogCustomName(Component.literal(tempName));
        }

        InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, dog_mX, dog_mY, size, 
            lookX, lookY, dog);

        if (nameTooLong) {
            dog.setDogCustomName(currentDogName);
        }
        if (dogTooBig) {
            dog.setDogSize(currentDogSize);
        }
    }

    public void renderHealthBar(GuiGraphics graphics, Dog dog, int x, int y) {
        
        int pX = x;
        int pY = y;

        String healthStrPrefix = " x " 
                + StatFormatter.DECIMAL_FORMAT.format(dog.getHealth());

        Component health_c0 = null;
        if (dog.hasWolfArmor()) {
            int wolf_armor_ext_hp = dog.wolfArmor().getMaxDamage() -
                 dog.wolfArmor().getDamageValue();
            var healthStrPosfix = "+" + wolf_armor_ext_hp;
            health_c0 = Component.literal(healthStrPrefix)
                .append(
                    Component.literal(healthStrPosfix)
                        .withStyle(Style.EMPTY.withColor(0xffd1926d))
                );
        } else {
            var healthStrPosfix = "/" + ((int)dog.getMaxHealth());
            String healthStr = healthStrPrefix + healthStrPosfix;
            health_c0 = Component.literal(healthStr);
        }
        
        pX += (80 - (8 + font.width(health_c0)))/2; 
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16, 0 ,9, 9);
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16 + 36, 0 ,9, 9);
        pX += 9;
        pY += 1;
        graphics.drawString(font, health_c0, pX, pY, 0xffffffff);
        return;
        // Random random = new Random();
        // random.setSeed((long) (dog.tickCount * 312871));
        // int dogHealth = Mth.ceil(dog.getHealth());
        // int aX = x;
        // int aY = y;
        // float dogMaxHealth = (float) dog.getMaxHealth();
        // int dogAbsorbAmount = Mth.ceil(dog.getAbsorptionAmount());
        // float totalHealth = (dogMaxHealth + (float) dogAbsorbAmount);
        // if (totalHealth > 40f) {
            
        //     int pX = aX;
        //     int pY = aY;
            
        //     String healthStr = " x " 
        //         + StatFormatter.DECIMAL_FORMAT.format(dog.getHealth()) + "/" 
        //         + StatFormatter.DECIMAL_FORMAT.format(dogMaxHealth);
        //     pX += (80 - (8 + font.width(healthStr)))/2; 
        //     graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16, 0 ,9, 9);
        //     graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, pX, pY, 16 + 36, 0 ,9, 9);
        //     pX += 9;
        //     pY += 1;
        //     graphics.drawString(font, healthStr, pX, pY, 0xffffffff);
        //     return;
        // }
        // if (totalHealth > 20f) {
        //     aY += 9;
        // }
        // int fullHealthLines = Mth.ceil(totalHealth / 20f);
        // int j2 = Math.max(10 - (fullHealthLines - 2), 3);
        // int pDogAbsorbAmount = dogAbsorbAmount;
        // int k3 = -1;
        // if (dog.hasEffect(MobEffects.REGENERATION)) {
        //     k3 = dog.tickCount % Mth.ceil(dogMaxHealth + 5.0F);
        // }
        // // this.minecraft.getProfiler().push("health");
        // // not gonna display effect now becuz there is an client entity effect sync
        // // problem

        // for (int pHeart = Mth.ceil(totalHealth / 2.0F) - 1; pHeart >= 0; --pHeart) {
        //     int guiIconIndexX = 16;
        //     int guiIconIndexY = 0;
        //     if (dog.hasEffect(MobEffects.POISON)) {
        //         guiIconIndexX += 36;
        //     } else if (dog.hasEffect(MobEffects.WITHER)) {
        //         guiIconIndexX += 72;
        //     }

        //     int k4 = Mth.ceil((float) (pHeart + 1) / 10.0F) - 1;
        //     int heartX = aX + pHeart % 10 * 8;
        //     int heartY = aY - k4 * j2;
        //     if (dogHealth <= 4) {
        //         heartY += random.nextInt(2);
        //     }

        //     if (pDogAbsorbAmount <= 0 && pHeart == k3) {
        //         heartY -= 2;
        //     }

            

        //     graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, heartX, heartY, 16, 0, 9, 9);

        //     if (pDogAbsorbAmount > 0) {
        //         //Render Yellow Heart.
        //         if (pDogAbsorbAmount == dogAbsorbAmount && dogAbsorbAmount % 2 == 1) {
        //             graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, heartX, heartY, guiIconIndexX + 153, 9 * guiIconIndexY, 9, 9);
        //             --pDogAbsorbAmount;
        //         } else {
        //             graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, heartX, heartY, guiIconIndexX + 144, 9 * guiIconIndexY, 9, 9);
        //             pDogAbsorbAmount -= 2;
        //         }
        //     } else {
        //         //Render last red heart
        //         if (pHeart * 2 + 1 < dogHealth) {
        //             graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, heartX, heartY, guiIconIndexX + 36, 9 * guiIconIndexY, 9, 9);
        //         }

        //         if (pHeart * 2 + 1 == dogHealth) {
        //             graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, heartX, heartY, guiIconIndexX + 45, 9 * guiIconIndexY, 9, 9);
        //         }
        //     }

        // }

        // this.minecraft.getProfiler().pop();
    }

    private void renderHungerStatusStr(GuiGraphics graphics, Dog dog, int x, int y) {
        graphics.blit(DogScreenOverlays.GUI_ICONS_LOCATION, x, y, 16 + 36, 27, 9, 9);
        int hunger = (int) dog.getDogHunger();
        graphics.drawString(font, "" + hunger, x + 10, y + 1, 0xffffffff);
    }

    private void renderVariantIcon(GuiGraphics graphics, Dog dog, int x, int y) {
        var variant = dog.dogVariant();
        var iconLoc = variant.icon().orElse(null);
        if (iconLoc == null)
            return;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int imgeSize = 16;
        graphics.blit(iconLoc, x, y, 0, 0, 0, imgeSize, imgeSize, imgeSize, imgeSize);
        RenderSystem.disableBlend();
    }
    
}
