package net.ciaphas.testingmod.datagen;

import net.ciaphas.testingmod.TestingMod;
import net.ciaphas.testingmod.block.ModBlocks;
import net.ciaphas.testingmod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> SAPPHIRE_SMELTABLES = List.of(ModItems.RAW_SAPPHIRE.get(),
            ModBlocks.SAPPHIRE_ORE.get(),ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModBlocks.NETHER_SAPPHIRE_ORE.get(),
            ModBlocks.END_SAPPHIRE_ORE.get());
    private static final List<ItemLike> ADAMANTIUM_SMELTABLES = List.of(ModItems.RAW_ADAMANTIUM.get(),
            ModBlocks.ADAMANTIUM_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        oreSmelting(consumer, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");
        oreSmelting(consumer, ADAMANTIUM_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 200, "sapphire");
        oreBlasting(consumer, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");
        oreBlasting(consumer, ADAMANTIUM_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 0.25f, 100, "sapphire");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SAPPHIRE_BLOCK.get())
                .pattern("SSS") //Recipe pattern - Shaped Crafting
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SAPPHIRE.get())
                .unlockedBy(getHasName(ModItems.SAPPHIRE.get()), has(ModItems.SAPPHIRE.get()))//Describes prerequisites to unlock recipe in recipe book
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ADAMANTIUM_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.ADAMANTIUM.get())
                .unlockedBy(getHasName(ModItems.ADAMANTIUM.get()), has(ModItems.ADAMANTIUM.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RAW_ADAMANTIUM_BLOCK.get())
                .pattern("RRR")
                .pattern("RRR")
                .pattern("RRR")
                .define('R', ModItems.RAW_ADAMANTIUM.get())
                .unlockedBy(getHasName(ModItems.RAW_ADAMANTIUM.get()), has(ModItems.RAW_ADAMANTIUM.get()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SAPPHIRE.get(), 9) //Shapeless crafting, calls for only required material and output amount
                .requires(ModBlocks.SAPPHIRE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.SAPPHIRE_BLOCK.get()), has(ModBlocks.SAPPHIRE_BLOCK.get()))//Describes prerequisites to unlock recipe in recipe book
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ADAMANTIUM.get(), 9)
                .requires(ModBlocks.ADAMANTIUM_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.ADAMANTIUM_BLOCK.get()), has(ModBlocks.ADAMANTIUM_BLOCK.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_ADAMANTIUM.get(), 9)
                .requires(ModBlocks.RAW_ADAMANTIUM_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RAW_ADAMANTIUM_BLOCK.get()), has(ModBlocks.RAW_ADAMANTIUM_BLOCK.get()))
                .save(consumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(new ItemLike[]{itemlike}), pCategory, pResult,
                    pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, TestingMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
