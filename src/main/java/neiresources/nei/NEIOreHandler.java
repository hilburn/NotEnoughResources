package neiresources.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.mob.Mob;
import neiresources.reference.Resources;
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
        RenderHelper.drawLine(10, 10, 20, 20);
    }

    public class CachedOre extends TemplateRecipeHandler.CachedRecipe
    {
        public CachedOre(Mob mob)
        {

        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(new ItemStack(Items.blaze_powder), 8, 6);
        }

    }
}