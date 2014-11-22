package neresources.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neresources.reference.Resources;
import neresources.registry.GrassSeedRegistry;
import neresources.utils.Font;
import neresources.utils.TranslationHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Map;

public class NEISeedHandler extends TemplateRecipeHandler
{
    private static final int Y = 16;

    @Override
    public String getGuiTexture()
    {
        return Resources.Gui.SEED_NEI.toString();
    }

    @Override
    public String getRecipeName()
    {
        return TranslationHelper.translateToLocal("ner.seed.title");
    }

    @Override
    public void loadTransferRects()
    {
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(60, 15, 28, 18), NEIConfig.GRASS, new Object()));
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients)
    {
        if (ingredients[0] instanceof ItemStack)
        {
            ItemStack ingredient = (ItemStack) ingredients[0];
            if (ingredient.isItemEqual(new ItemStack(Blocks.tallgrass, 1, 1)))
            {
                for (Map.Entry<ItemStack, Float> entry : GrassSeedRegistry.getInstance().getAllDrops().entrySet())
                    arecipes.add(new CachedSeed(entry.getKey(), entry.getValue()));
            }
        }
        else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(NEIConfig.GRASS))
        {
            for (Map.Entry<ItemStack, Float> entry : GrassSeedRegistry.getInstance().getAllDrops().entrySet())
                arecipes.add(new CachedSeed(entry.getKey(), entry.getValue()));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        if (GrassSeedRegistry.getInstance().contains(result))
            arecipes.add(new CachedSeed(result, GrassSeedRegistry.getInstance().getChance(result)));
    }

    @Override
    public void drawExtras(int recipe)
    {
        Font font = new Font(false);
        font.print(String.format("%2.2f", ((CachedSeed)arecipes.get(recipe)).chance *100).replace(",",".") + "%", 56, Y+20);
    }

    public class CachedSeed extends TemplateRecipeHandler.CachedRecipe
    {
        public float chance;
        private ItemStack seed;

        public CachedSeed(ItemStack seed, float chance)
        {
            this.seed = seed;
            this.chance = chance;
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(new ItemStack(Blocks.tallgrass, 1, 1), 34, Y);
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return new PositionedStack(seed, 93, Y);
        }
    }
}
