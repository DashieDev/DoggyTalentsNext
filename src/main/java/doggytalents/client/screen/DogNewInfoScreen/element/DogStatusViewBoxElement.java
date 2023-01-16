package doggytalents.client.screen.DogNewInfoScreen.element;

import java.util.Random;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import doggytalents.common.entity.Dog;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;

public class DogStatusViewBoxElement extends AbstractElement {

    private Dog dog;
    private Font font;

    public DogStatusViewBoxElement(AbstractElement parent, Screen screen, Dog dog) {
        super(parent, screen);
        this.dog = dog;
        var mc = this.getScreen().getMinecraft();
        this.font = mc.font;
    }

    @Override
    public void renderElement(PoseStack stack, int mouseX, int mouseY, float partialTicks) {

        int e_mX = this.getRealX() + this.getSizeX()/2; 
        int e_mY = this.getRealY() + this.getSizeY()/2; 

        InventoryScreen.renderEntityInInventory(e_mX, e_mY + 32, 50, 
            e_mX - mouseX, e_mY - mouseY, this.dog);
        this.renderHealthBar(stack, dog, e_mX - 41, this.getRealY() + this.getSizeY() - 10);

        var points = this.dog.getSpendablePoints();
        this.font.draw(stack, "Pts: " + points, this.getRealX(), this.getRealY(), 0xffffffff);
    }

    public void renderHealthBar(PoseStack stack, Dog dog, int aX, int aY) {
        Random random = new Random();
        random.setSeed((long) (dog.tickCount * 312871));
        int i = Mth.ceil(dog.getHealth());
        int i1 = aX;
        int k1 = aY;
        float f = (float) dog.getMaxHealth();
        int l1 = Mth.ceil(dog.getAbsorptionAmount());
        int i2 = Mth.ceil((f + (float) l1) / 2.0F / 10.0F);
        int j2 = Math.max(10 - (i2 - 2), 3);
        int k2 = k1 - (i2 - 1) * j2 - 10;
        int l2 = k1 - 10;
        int i3 = l1;
        int k3 = -1;
        if (dog.hasEffect(MobEffects.REGENERATION)) {
            k3 = dog.tickCount % Mth.ceil(f + 5.0F);
        }
        RenderSystem.setShaderTexture(0, Screen.GUI_ICONS_LOCATION);
        // this.minecraft.getProfiler().push("health");
        // not gonna display effect now becuz there is an client entity effect sync
        // problem

        for (int l5 = Mth.ceil((f + (float) l1) / 2.0F) - 1; l5 >= 0; --l5) {
            int i6 = 16;
            if (dog.hasEffect(MobEffects.POISON)) {
                i6 += 36;
            } else if (dog.hasEffect(MobEffects.WITHER)) {
                i6 += 72;
            }

            int j4 = 0;

            int k4 = Mth.ceil((float) (l5 + 1) / 10.0F) - 1;
            int l4 = i1 + l5 % 10 * 8;
            int i5 = k1 - k4 * j2;
            if (i <= 4) {
                i5 += random.nextInt(2);
            }

            if (i3 <= 0 && l5 == k3) {
                i5 -= 2;
            }

            int j5 = 0;

            this.getScreen().blit(stack, l4, i5, 16, 0, 9, 9);

            if (i3 > 0) {
                if (i3 == l1 && l1 % 2 == 1) {
                    this.getScreen().blit(stack, l4, i5, i6 + 153, 9 * j5, 9, 9);
                    --i3;
                } else {
                    this.getScreen().blit(stack, l4, i5, i6 + 144, 9 * j5, 9, 9);
                    i3 -= 2;
                }
            } else {
                if (l5 * 2 + 1 < i) {
                    this.getScreen().blit(stack, l4, i5, i6 + 36, 9 * j5, 9, 9);
                }

                if (l5 * 2 + 1 == i) {
                    this.getScreen().blit(stack, l4, i5, i6 + 45, 9 * j5, 9, 9);
                }
            }

        }

        // this.minecraft.getProfiler().pop();
    }


    
}
