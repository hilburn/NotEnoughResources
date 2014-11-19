package neresources.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import neresources.reference.Resources;
import neresources.registry.EnchantmentRegistry;
import neresources.utils.Font;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class NEIEnchantmentHandler extends TemplateRecipeHandler
{
    @Override
    public String getGuiTexture()
    {
        return Resources.Gui.ENCHANTMENT_NEI.toString();
    }

    @Override
    public String getRecipeName()
    {
        return "Enchantments";
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {
        if (EnchantmentRegistry.getInstance().getEnchantments(ingredient).size() > 0)
            arecipes.add(new CachedEnchantment(ingredient));
    }

    @Override
    public void drawExtras(int recipe)
    {
        CachedEnchantment cachedEnchantment = (CachedEnchantment)arecipes.get(recipe);
        Font font = new Font(false);
        int y = 5;
        for (Enchantment enchantment : cachedEnchantment.enchantments)
        {
            font.print(enchantment.getName(), 30, y);
            y += 10;
        }
    }

    public class CachedEnchantment extends TemplateRecipeHandler.CachedRecipe
    {

        private ItemStack itemStack;
        public Set<Enchantment> enchantments;

        public CachedEnchantment(ItemStack itemStack)
        {
            this.itemStack = itemStack;
            this.enchantments = EnchantmentRegistry.getInstance().getEnchantments(itemStack);
        }

        @Override
        public PositionedStack getResult()
        {
            return new PositionedStack(this.itemStack, 8, 6);
        }
    }
}
