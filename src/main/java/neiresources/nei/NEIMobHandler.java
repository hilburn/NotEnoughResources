package neiresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.drop.DropItem;
import neiresources.reference.Resources;
import neiresources.registry.MobRegistry;
import neiresources.registry.MobRegistryEntry;
import neiresources.utils.Font;
import neiresources.utils.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
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
        for (MobRegistryEntry entry : MobRegistry.getInstance().getMobsThatDropItem(result.getItem()))
            arecipes.add(new CachedMob(entry));
    }

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 130);

        EntityLivingBase entityLivingBase = ((CachedMob)arecipes.get(recipe)).getMob();
        float scale = 1;
        if (entityLivingBase.width < entityLivingBase.height) scale = 70/entityLivingBase.height;
        else scale = 25/entityLivingBase.width;
        RenderHelper.renderEntity(30, 90 + (int)(entityLivingBase.height*scale/2), scale , 20, -20, entityLivingBase);
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedMob cachedMob = (CachedMob)arecipes.get(recipe);

        Font font = new Font(false);
        font.print(cachedMob.mob.getName(), 2, 2);
        font.print("Spawn Biome: " + cachedMob.mob.getBiomes().get(0), 2, 12);
        font.print("Spawn light level: " + cachedMob.getLightLevel(), 2, 22);
        font.print("more Info", 2, 32);

        int y = 46;
        for (DropItem dropItem : cachedMob.mob.getDrops())
        {
            font.print(dropItem.minDrop + "-" + dropItem.maxDrop + getDropChance(dropItem), 110, y);
            y += 18;
        }
    }

    private String getDropChance(DropItem dropItem)
    {
        return dropItem.chance == 1F ? "" : " (" + String.valueOf((int)(dropItem.chance * 100)) + "%)";
    }

    public class CachedMob extends TemplateRecipeHandler.CachedRecipe
    {
        public MobRegistryEntry mob;

        public CachedMob(MobRegistryEntry mob)
        {
            this.mob = mob;
        }

        public EntityLivingBase getMob()
        {
            return this.mob.getEntity();
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(new ItemStack(mob.getDrops().get(0).item), 90, 40);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            int y = 40;
            for (DropItem dropItem : mob.getDrops())
            {
                list.add(new PositionedStack(new ItemStack(dropItem.item), 90, y));
                y += 20;
            }
            list.remove(0);
            return list;
        }

        public String getLightLevel()
        {
            return mob.getLightLevel() == -1 ? "Any" : String.valueOf(mob.getLightLevel());
        }
    }
}
