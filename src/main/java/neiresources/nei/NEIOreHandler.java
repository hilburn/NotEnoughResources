package neiresources.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.reference.Resources;
import neiresources.registry.OreRegistryEntry;
import neiresources.utils.Font;
import neiresources.utils.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class NEIOreHandler extends TemplateRecipeHandler
{
    private static final int X_OFFSPRING = 59;
    private static final int Y_OFFSPRING = 52;
    private static final int X_AXIS_SIZE = 105;
    private static final int Y_AXIS_SIZE = 40;

    private static final int X_ITEM = 8;
    private static final int Y_ITEM = 6;

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
        double[] array = new double[]{10, 20, 10, 20, 10, 20, 10, 20, 10, 10, 20, 10, 20, 10, 20, 10};
        double max = 0;
        for (double d : array)
            if (d > max) max = d;
        int xPrev = X_OFFSPRING;
        int yPrev = Y_OFFSPRING - (int)((array[0]/max)*Y_AXIS_SIZE);
        int space = X_AXIS_SIZE / array.length;
        int precision = array.length/2 < 1 ? 1 : array.length/2;
        for(int i = 1; i < array.length; i++)
        {
            int x = xPrev + space;
            int y = Y_OFFSPRING - (int)((array[i]/max)*Y_AXIS_SIZE);
            RenderHelper.drawLine(xPrev, yPrev, x, y, precision);
            xPrev = x;
            yPrev = y;
        }

        Font font = new Font(true);
        font.print("0%", X_OFFSPRING -10, Y_OFFSPRING -7);
        font.print("0.03%", X_OFFSPRING -20, Y_OFFSPRING - Y_AXIS_SIZE);
        font.print("16", X_OFFSPRING, Y_OFFSPRING +2);
        font.print("40", X_OFFSPRING + X_AXIS_SIZE -20, Y_OFFSPRING +2);
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
            return new PositionedStack(new ItemStack(Item.getItemFromBlock(Blocks.coal_ore)), X_ITEM, Y_ITEM);
        }

    }
}