package neresources.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class EnchantmentRegistry
{
    private static Set<Enchantment> enchantments = new HashSet<Enchantment>();
    private static EnchantmentRegistry instance = null;

    public static EnchantmentRegistry getInstance()
    {
        if (instance == null)
            return instance = new EnchantmentRegistry();
        return instance;
    }

    public EnchantmentRegistry()
    {
        for (Enchantment enchantment : Enchantment.enchantmentsList)
            if (enchantment != null) enchantments.add(enchantment);
    }

    public Set<Enchantment> getEnchantments(ItemStack itemStack)
    {
        Set<Enchantment> set = new HashSet<Enchantment>();
        for (Enchantment enchantment : enchantments)
            if (enchantment.canApply(itemStack)) set.add(enchantment);
        return set;
    }

    public Set<Enchantment> getEnchantments()
    {
        return enchantments;
    }
}
