package neiresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.config.Settings;
import neiresources.reference.Resources;
import neiresources.registry.MobEntry;
import neiresources.registry.MobRegistry;
import neiresources.utils.Font;
import neiresources.utils.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NEIMobHandler extends TemplateRecipeHandler
{
    private static final String MOB_ID = "neiResources.mob";
    private static final int X_FIRST_ITEM = 90;
    private static final int Y_FIRST_ITEM = 42;

    private static int SPACING_Y = 90 / Settings.ITEMS_PER_COLUMN;
    private static int CYCLE_TIME = (int)(20 * Settings.CYCLE_TIME);

    public static void loadSettings()
    {
        SPACING_Y = 80 / Settings.ITEMS_PER_COLUMN;
        CYCLE_TIME = (int)(20 * Settings.CYCLE_TIME);
    }

    private static int lastRecipe = -1;

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
    public void loadTransferRects()
    {
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(62, 72, 28, 18), MOB_ID, null));
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if (outputId.equals(MOB_ID))
        {
            for (MobEntry entry : MobRegistry.getInstance().getMobs())
                arecipes.add(new CachedMob(entry));
        }
        else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {
        for (int oreDict: OreDictionary.getOreIDs(result))
            System.out.println(OreDictionary.getOreName(oreDict));
        for (MobEntry entry : MobRegistry.getInstance().getMobsThatDropItem(result))
            arecipes.add(new CachedMob(entry));
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 130);

        EntityLivingBase entityLivingBase = ((CachedMob)arecipes.get(recipe)).getMob();
        float scale = getScale(entityLivingBase);
        int offsetX = entityLivingBase.width < entityLivingBase.height ?(int)(72-scale) : 72;
        if (scale == 70.0F) offsetX = (int)(72-scale/2);
        RenderHelper.renderEntity(30, 165 - offsetX, scale , 20, -20, entityLivingBase);
    }

    private float getScale(EntityLivingBase entityLivingBase)
    {
        float width = entityLivingBase.width;
        float height = entityLivingBase.height;
        if (width < height)
        {
            if (height < 1) return 70.0F;
            else if (height < 2) return 32.0F;
            else if (height < 3) return 26.0F;
            else return 20.0F;
        }
        else
        {
            if (width < 1) return 38.0F;
            else if (width < 2) return 27.0F;
            else if (width < 3) return 13.0F;
            else return 9.0F;
        }
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedMob cachedMob = (CachedMob)arecipes.get(recipe);

        Font font = new Font(false);
        font.print(cachedMob.mob.getName(), 2, 2);
        font.print("Spawn Biome: " + cachedMob.mob.getBiomes().get(0), 2, 12);
        font.print(cachedMob.mob.getLightLevel(), 2, 22);
        font.print("Experience Dropped: "+ cachedMob.mob.getExperience(), 2, 32);

        int y = Y_FIRST_ITEM +4;
        for (int i = cachedMob.set * Settings.ITEMS_PER_COLUMN; i < cachedMob.set * Settings.ITEMS_PER_COLUMN + Settings.ITEMS_PER_COLUMN; i++)
        {
            if (i >= cachedMob.mob.getDrops().size()) break;
            font.print(cachedMob.mob.getDrops().get(i).toString(), X_FIRST_ITEM +18, y);
            y += SPACING_Y;
        }

        cachedMob.cycleOutputs(cycleticks, recipe);
    }



    public class CachedMob extends TemplateRecipeHandler.CachedRecipe
    {
        public MobEntry mob;
        public int set;
        private int lastSet;
        private long cycleAt;

        public CachedMob(MobEntry mob)
        {
            this.mob = mob;
            this.set = 0;
            this.lastSet = (mob.getDrops().size() / (Settings.ITEMS_PER_COLUMN+1));
            cycleAt = -1;
        }

        public EntityLivingBase getMob()
        {
            return this.mob.getEntity();
        }

        @Override
        public PositionedStack getResult()
        {
            if (mob.getDrops().isEmpty()) return null;
            return new PositionedStack(mob.getDrops().get(set*Settings.ITEMS_PER_COLUMN).item, X_FIRST_ITEM, Y_FIRST_ITEM);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            int y = Y_FIRST_ITEM;
            for (int i = set * Settings.ITEMS_PER_COLUMN; i < set * Settings.ITEMS_PER_COLUMN + Settings.ITEMS_PER_COLUMN; i++)
            {
                if (i >= mob.getDrops().size()) break;
                list.add(new PositionedStack(mob.getDrops().get(i).item, X_FIRST_ITEM, y));
                y += SPACING_Y;
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
                if (++set > lastSet) set = 0;
                cycleAt += CYCLE_TIME;
            }
        }

    }
}
