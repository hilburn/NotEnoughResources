package neresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.*;
import codechicken.nei.Button;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neresources.config.Settings;
import neresources.reference.Resources;
import neresources.registry.DungeonEntry;
import neresources.registry.DungeonRegistry;
import neresources.utils.Font;
import neresources.utils.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NEIDungeonHandler extends TemplateRecipeHandler
{

    private static final int X_FIRST_ITEM = -2;
    private static final int Y_FIRST_ITEM = 48;

    private static int ITEMS_PER_PAGE;
    private static int SPACING_X;
    private static int SPACING_Y;
    private static int CYCLE_TIME;

    public static void loadSettings()
    {
        ITEMS_PER_PAGE = Settings.ITEMS_PER_COLUMN * Settings.ITEMS_PER_ROW;
        SPACING_X = 176 / Settings.ITEMS_PER_ROW;
        SPACING_Y = 80 / Settings.ITEMS_PER_COLUMN;
        CYCLE_TIME = (int) (20 * Settings.CYCLE_TIME);
    }

    private static int lidStart = -1;
    private static int lastRecipe = -1;
    private static boolean done;

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
    public void loadTransferRects()
    {
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(5, 5, 40, 40), NEIConfig.DUNGEON, null));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(NEIConfig.DUNGEON))
        {
            for (DungeonEntry entry : DungeonRegistry.getInstance().getDungeons())
                arecipes.add(new CachedDungeonChest(entry));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (DungeonEntry entry : DungeonRegistry.getInstance().getDungeons(result))
            arecipes.add(new CachedDungeonChest(entry));
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 130);

        RenderHelper.renderChest(15, 20, -40, 20, getLidAngle(recipe));
    }

    private float getLidAngle(int recipe)
    {
        if (recipe != lastRecipe)
        {
            done = false;
            lastRecipe = -1;
            lidStart = -1;
        }

        if (lidStart == -1) lidStart = cycleticks;

        float angle = (cycleticks - lidStart) % 80;
        if (angle > 50 || done)
        {
            done = true;
            angle = 50;
        }

        return angle;
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedDungeonChest cachedChest = (CachedDungeonChest) arecipes.get(recipe);

        Font font = new Font(false);
        font.print(cachedChest.chest.getName(), 60, 7);
        font.print(cachedChest.chest.getNumStacks(), 60, 22);
        if(cachedChest.lastSet > 0)font.print("Page " + (cachedChest.set+1) + " of " + (cachedChest.lastSet+1), 80, 36);

        int x = X_FIRST_ITEM + 18;
        int y = Y_FIRST_ITEM + (10 - Settings.ITEMS_PER_COLUMN);
        for (int i = ITEMS_PER_PAGE * cachedChest.set; i < ITEMS_PER_PAGE * cachedChest.set + ITEMS_PER_PAGE; i++)
        {
            if (i >= cachedChest.chest.getContents().length) break;
            double chance = cachedChest.chest.getChance(cachedChest.chest.getContents()[i]) * 100;
            String format = chance < 100 ? "%2.1f" : "%2.0f";
            String toPrint = String.format(format, chance).replace(',', '.') + "%";
            font.print(toPrint, x, y);
            y += SPACING_Y;
            if (y >= Y_FIRST_ITEM + SPACING_Y * Settings.ITEMS_PER_COLUMN)
            {
                y = Y_FIRST_ITEM + (10 - Settings.ITEMS_PER_COLUMN);
                x += SPACING_X;
            }
        }

        cachedChest.cycleOutputs(cycleticks, recipe);
    }

    public class CachedDungeonChest extends TemplateRecipeHandler.CachedRecipe
    {
        public DungeonEntry chest;
        public int set, lastSet;
        private long cycleAt;

        public CachedDungeonChest(DungeonEntry chest)
        {
            this.chest = chest;
            set = 0;
            cycleAt = -1;
            lastSet = (chest.getContents().length / (ITEMS_PER_PAGE + 1));
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(this.chest.getContents()[set * ITEMS_PER_PAGE].theItemId, X_FIRST_ITEM, Y_FIRST_ITEM);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            int x = X_FIRST_ITEM;
            int y = Y_FIRST_ITEM;
            for (int i = ITEMS_PER_PAGE * set; i < ITEMS_PER_PAGE * set + ITEMS_PER_PAGE; i++)
            {
                if (i >= this.chest.getContents().length) break;
                list.add(new PositionedStack(this.chest.getContents()[i].theItemId, x, y));
                y += SPACING_Y;
                if (y >= Y_FIRST_ITEM + SPACING_Y * Settings.ITEMS_PER_COLUMN)
                {
                    y = Y_FIRST_ITEM;
                    x += SPACING_X;
                }
            }
            if (list.size() > 0) list.remove(0);
            return list;
        }

        public void cycleOutputs(long tick, int recipe)
        {
            if (cycleAt == -1 || recipe != lastRecipe || !Settings.DO_CYLCE)
            {
                lastRecipe = recipe;
                cycleAt = tick + CYCLE_TIME;
                return;
            }

            if (tick >= cycleAt)
            {
                cycle();
                cycleAt += CYCLE_TIME;
            }
        }

        public void cycle()
        {
            if (++set > lastSet) set = 0;
        }

        public void cycleBack()
        {
            if (--set < 0) set = lastSet;
        }
    }
}
