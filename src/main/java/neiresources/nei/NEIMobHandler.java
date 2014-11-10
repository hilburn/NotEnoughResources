package neiresources.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neiresources.reference.Resources;
import neiresources.registry.MobRegistryEntry;
import neiresources.utils.Font;
import neiresources.utils.RenderHelper;
import net.minecraft.entity.EntityLivingBase;
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

        EntityLivingBase entityLivingBase = ((CachedMob)arecipes.get(recipe)).getMob();
        float scale = 1;
        if (entityLivingBase.width < entityLivingBase.height) scale = 80/entityLivingBase.height;
        else scale = 25/entityLivingBase.width;
        RenderHelper.renderEntity(30, 85 + (int)(entityLivingBase.height*scale/2), scale , 20, -20, entityLivingBase);
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedMob cachedMob = (CachedMob) arecipes.get(recipe);

        RenderHelper.drawPoint(30, 85);

        Font font = new Font(false);
        font.print("Name of the mob", 2, 2);
        font.print("Spawn Biome: List or All", 2, 12);
        font.print("Spawn light level: some level", 2, 22);
        font.print("more Info", 2, 32);

        font.print("0-6 (20%)", 110, 46);
        font.print("0-6 (20%)", 110, 64);
        font.print("0-6 (20%)", 110, 82);
        font.print("0-6 (20%)", 110, 100);
        font.print("0-6 (20%)", 110, 118);


    }

    @Override
    public void onUpdate()
    {

    }

    public class CachedMob extends TemplateRecipeHandler.CachedRecipe
    {
        MobRegistryEntry mob;

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
            return new PositionedStack(new ItemStack(Items.blaze_powder), 90, 40);
        }

        @Override
        public List<PositionedStack> getOtherStacks()
        {
            List<PositionedStack> list = new ArrayList<PositionedStack>();
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 90, 58));
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 90, 76));
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 90, 94));
            list.add(new PositionedStack(new ItemStack(Items.blaze_powder), 90, 112));
            return list;
        }
    }
}
