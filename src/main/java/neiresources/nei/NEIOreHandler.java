package neiresources.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.mob.Mob;
import neiresources.reference.Resources;
import neiresources.registry.OreRegistryEntry;
import neiresources.utils.RenderHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NEIOreHandler extends TemplateRecipeHandler
{
    @Override
    public String getGuiTexture()
    {
        return Resources.Gui.ORE_NEI.toString();
    }

    @Override
    public String getRecipeName()
    {
        return "Ore Gen";
    }

    @Override
    public int recipiesPerPage()
    {
        return 2;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        arecipes.add(new CachedOre(null));
        arecipes.add(new CachedOre(null));
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedOre cachedOre = (CachedOre)arecipes.get(recipe);
        double[] array = new double[]{10, 12, 18, 20, 20, 6};
        double max = 0;
        for (double d : array)
            if (d > max) max = d;
        int xPrev = 59;
        int yPrev = 52;
        for(int i = 0; i < array.length; i++)
        {
            int x = xPrev + 10;
            int y = 52 - (int)array[i];
            RenderHelper.drawLine(xPrev, yPrev, x, y);
            xPrev = x;
            yPrev = y;
        }
    }

    public class CachedOre extends TemplateRecipeHandler.CachedRecipe
    {
        private OreRegistryEntry oreRegistryEntry;

        public CachedOre(OreRegistryEntry oreRegistryEntry)
        {
            this.oreRegistryEntry = oreRegistryEntry;
        }

        public double[] getChances()
        {
            return oreRegistryEntry.getChances();
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(new ItemStack(Items.blaze_powder), 8, 6);
        }

    }
}