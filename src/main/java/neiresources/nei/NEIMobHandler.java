package neiresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.mob.Mob;
import neiresources.reference.Resources;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class NEIMobHandler extends TemplateRecipeHandler
{
    @Override
    public String getGuiTexture()
    {
        return Resources.Gui.MOB_NEI.toString();
    }

    @Override
    public String getRecipeName()
    {
        return "Mob drop";
    }

    @Override
    public int recipiesPerPage()
    {
       return 1;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        arecipes.add(new CachedMob(null));
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 130);
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedMob cachedMob = (CachedMob) arecipes.get(recipe);
        GuiDraw.drawString("Name of the mob", 2, 2, 8, false);
        GuiDraw.drawString("Spawn Biome: List or All", 2, 12, 8, false);
        GuiDraw.drawString("Spawn light level: some level", 2, 22, 8, false);
        GuiDraw.drawString("more Info", 2, 32, 8, false);

        GuiDraw.drawString("0-6 (20%)", 90, 46, 8, false);
        GuiDraw.drawString("0-6 (20%)", 90, 64, 8, false);
        GuiDraw.drawString("0-6 (20%)", 90, 82, 8, false);
        GuiDraw.drawString("0-6 (20%)", 90, 100, 8, false);
        GuiDraw.drawString("0-6 (20%)", 90, 118, 8, false);
    }

    public class CachedMob extends TemplateRecipeHandler.CachedRecipe
    {
        public CachedMob(Mob mob)
        {

        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(new ItemStack(Items.blaze_powder), 70, 40);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 70, 58));
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 70, 76));
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 70, 94));
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 70, 112));
            return list;
        }
    }
}
