package neresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neresources.api.entry.IDungeonEntry;
import neresources.config.Settings;
import neresources.reference.Resources;
import neresources.registry.DungeonRegistry;
import neresources.utils.DungeonHelper;
import neresources.utils.Font;
import neresources.utils.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
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
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(5, 5, 40, 40), NEIConfig.DUNGEON, new Object()));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(NEIConfig.DUNGEON))
        {
            for (IDungeonEntry entry : DungeonRegistry.getInstance().getDungeons())
                arecipes.add(new CachedDungeonChest(entry));
            lastRecipe = -1;
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (IDungeonEntry entry : DungeonRegistry.getInstance().getDungeons(result))
            arecipes.add(new CachedDungeonChest(entry));
        lastRecipe = -1;
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
        font.print(DungeonRegistry.getInstance().getNumStacks(cachedChest.chest), 60, 20);
        if(cachedChest.lastSet > 0)font.print("Page " + (cachedChest.set+1) + " of " + (cachedChest.lastSet+1), 60, 36);

        int x = X_FIRST_ITEM + 18;
        int y = Y_FIRST_ITEM + (10 - Settings.ITEMS_PER_COLUMN);
        for (int i = ITEMS_PER_PAGE * cachedChest.set; i < ITEMS_PER_PAGE * cachedChest.set + ITEMS_PER_PAGE; i++)
        {
            if (i >= cachedChest.getContents().length) break;
            double chance = DungeonHelper.getChance(cachedChest.chest, cachedChest.getContents()[i]) * 100;
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
        public IDungeonEntry chest;
        public int set, lastSet;
        private long cycleAt;

        public CachedDungeonChest(IDungeonEntry chest)
        {
            this.chest = chest;
            set = 0;
            cycleAt = -1;
            lastSet = (this.getContents().length / (ITEMS_PER_PAGE + 1));
        }

        public WeightedRandomChestContent[] getContents()
        {
            return DungeonHelper.getContents(chest);
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(this.getContents()[set * ITEMS_PER_PAGE].theItemId, X_FIRST_ITEM, Y_FIRST_ITEM);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            int x = X_FIRST_ITEM;
            int y = Y_FIRST_ITEM;
            for (int i = ITEMS_PER_PAGE * set; i < ITEMS_PER_PAGE * set + ITEMS_PER_PAGE; i++)
            {
                if (i >= this.getContents().length) break;
                list.add(new PositionedStack(this.getContents()[i].theItemId, x, y));
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
            if (cycleAt == -1 || recipe != lastRecipe)
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
