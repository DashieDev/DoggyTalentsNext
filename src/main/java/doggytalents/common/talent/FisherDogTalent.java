package doggytalents.common.talent;

import java.util.stream.Collectors;

import doggytalents.DoggyTalents;
import doggytalents.api.enu.WetSource;
import doggytalents.api.inferface.AbstractDog;
import doggytalents.api.registry.Talent;
import doggytalents.api.registry.TalentInstance;
import doggytalents.common.entity.Dog;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;

public class FisherDogTalent extends TalentInstance {

    private int cookCooldown = 10;

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
        if (dogIn.level.isClientSide) { // On client do nothing
            return;
        }

        if (!source.isWaterBlock())
            return;
        
        int r_fish = dogIn.getRandom().nextInt(15);
        if (r_fish >= this.level() * 2)
            return;
        
        var fishItem = getRandomFish(dogIn);
        var fishStack = getFishedStack(dogIn, fishItem);

        dogIn.spawnAtLocation(fishStack);
    }

    private Item getRandomFish(AbstractDog dog) {
        var tags = ForgeRegistries.ITEMS.tags();
        var fishes = tags.getTag(ItemTags.FISHES)
            .stream().collect(Collectors.toList());
        if (fishes.isEmpty())
            return Items.COD;
        int r = dog.getRandom().nextInt(fishes.size());
        return fishes.get(r);
    }

    private ItemStack getFishedStack(AbstractDog dog, Item raw_item) {
        var raw_stack = new ItemStack(raw_item);
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
            new SimpleContainer(fish_raw.copy()), dog.level());
        if (!recipeOptional.isPresent())
            return fish_raw;
        var recipe = recipeOptional.get();
        var resultStack = recipe.getResultItem(dog.level().registryAccess());
        if (resultStack == null || resultStack.isEmpty())
            return fish_raw;
        return resultStack.copy();
    }
}
