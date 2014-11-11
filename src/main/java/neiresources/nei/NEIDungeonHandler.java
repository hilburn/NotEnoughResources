package neiresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.reference.Resources;
import neiresources.registry.DungeonRegistry;
import neiresources.registry.DungeonRegistryEntry;
import neiresources.utils.Font;
import neiresources.utils.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class NEIDungeonHandler extends TemplateRecipeHandler
{
    private static final int X_FIRST_ITEM = -2;
    private static final int Y_FIRST_ITEM = 48;
    private static final int ITEMS_PER_COLUMN = 5;
    private static final int SPACING_Y = 16;

    @Override
    public String getGuiTexture()
    {
        return Resources.Gui.DUNGEON_NEI.toString();
    }

    @Override
    public String getRecipeName()
    {
        return "Dungeon Chest";
    }

    @Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (DungeonRegistryEntry entry : DungeonRegistry.getInstance().getDungeons(result))
            arecipes.add(new CachedDungeonChest(entry));
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 130);

        //RenderHelper.renderChest(10, 40, 60, -60, 0);
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedDungeonChest cachedChest = (CachedDungeonChest)arecipes.get(recipe);

        Font font = new Font(false);
        font.print(cachedChest.chest.getName(), 60, 10);
        font.print(cachedChest.chest.getNumStacks(), 60, 25);

        int x = X_FIRST_ITEM +18;
        int y = Y_FIRST_ITEM +5;
        for (WeightedRandomChestContent chestContent : cachedChest.chest.getContents())
        {
            double chance = cachedChest.chest.getChance(chestContent)*100;
            String format = chance < 100 ? "%2.1f" : "%2.0f";
            font.print(String.format(format, chance) + "%", x, y);
            y += SPACING_Y;
            if (y >= Y_FIRST_ITEM + SPACING_Y *ITEMS_PER_COLUMN)
            {
                y = Y_FIRST_ITEM +5;
                x += 44;
            }
        }
    }

    public class CachedDungeonChest extends TemplateRecipeHandler.CachedRecipe
    {

        public DungeonRegistryEntry chest;

        public CachedDungeonChest(DungeonRegistryEntry chest)
        {
            this.chest = chest;
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(this.chest.getContents()[0].theItemId, X_FIRST_ITEM, Y_FIRST_ITEM);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            int x = X_FIRST_ITEM;
            int y = Y_FIRST_ITEM;
            for (WeightedRandomChestContent chestContent : this.chest.getContents())
            {
                list.add(new PositionedStack(chestContent.theItemId, x, y));
                y += SPACING_Y;
                if (y >= Y_FIRST_ITEM + SPACING_Y *ITEMS_PER_COLUMN)
                {
                    y = Y_FIRST_ITEM;
                    x += 44;
                }
            }
            list.remove(0);
            return list;
        }
    }
}
