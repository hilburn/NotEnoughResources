package neresources.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neresources.config.Settings;
import neresources.reference.Resources;
import neresources.registry.PlantDrop;
import neresources.registry.PlantEntry;
import neresources.registry.PlantRegistry;
import neresources.utils.Font;
import neresources.utils.TranslationHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Map;

public class NEISeedHandler extends TemplateRecipeHandler
{
    private static final int Y = 16;

    private static int CYCLE_TIME = (int) (20 * Settings.CYCLE_TIME);

    public static void reloadSettings()
    {
        CYCLE_TIME = (int) (20 * Settings.CYCLE_TIME);
    }

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
            if (PlantRegistry.getInstance().contains(ingredient))
                arecipes.add(new CachedSeed(PlantRegistry.getInstance().getEntry(ingredient)));
        } else super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(NEIConfig.GRASS))
        {
            for (PlantEntry entry : PlantRegistry.getInstance().getAllPlants())
                arecipes.add(new CachedSeed(entry));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        if (PlantRegistry.getInstance().containsDrop(result))
            for (PlantEntry entry : PlantRegistry.getInstance().getEntriesForDrop(result))
                arecipes.add(new CachedSeed(entry));
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedSeed cachedSeed = (CachedSeed) arecipes.get(recipe);
        Font font = new Font(false);
        font.print(String.format("%2.2f", cachedSeed.getChance() * 100).replace(",", ".") + "%", 56, Y + 20);

        cachedSeed.cycleOutput(cycleticks);
    }

    public class CachedSeed extends TemplateRecipeHandler.CachedRecipe
    {
        private PlantEntry entry;
        private int i, lastI;
        private long cycleAt;

        public CachedSeed(PlantEntry entry)
        {
            this.entry = entry;
            this.i = 0;
            this.lastI = entry.getDrops().size();
            this.cycleAt = -1;
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(entry.getPlant(), 34, Y);
        }

        @Override
        public PositionedStack getOtherStack()
        {
            return new PositionedStack(entry.getDrops().get(i).getDrop(), 94, Y);
        }

        public float getChance()
        {
            return (float)entry.getDrops().get(i).getWeight() / entry.getTotalWeight();
        }

        public void cycleOutput(long tick)
        {
            if (cycleAt == -1) cycleAt = tick + CYCLE_TIME;

            if (cycleAt <= tick)
            {
                if (++i >= lastI) i = 0;
                cycleAt += CYCLE_TIME;
            }
        }
    }
}
