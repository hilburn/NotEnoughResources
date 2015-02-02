package neresources.registry;

import neresources.entries.EnchantmentEntry;
import neresources.utils.ByteArrayHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class EnchantmentRegistry
{
    private static Set<EnchantmentEntry> enchantments = new HashSet<EnchantmentEntry>();
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
            if (enchantment != null) enchantments.add(new EnchantmentEntry(enchantment));
    }

    public Set<EnchantmentEntry> getEnchantments(ItemStack itemStack)
    {
        Set<EnchantmentEntry> set = new HashSet<EnchantmentEntry>();
        for (EnchantmentEntry enchantment : enchantments)
        {
            if (itemStack.getItem() == Items.book && enchantment.getEnchantment().isAllowedOnBooks())
                set.add(enchantment);
            else if (enchantment.getEnchantment().canApply(itemStack)) set.add(enchantment);
        }
        return set;
    }

    public Set<EnchantmentEntry> getEnchantments()
    {
        return enchantments;
    }
    
    public static byte[] toBytes()
    {
        return ByteArrayHelper.toByteArray(enchantments);
    }
    
    public static void fromBytes(byte[] bytes)
    {
        enchantments = ByteArrayHelper.fromBytesArray(bytes);
    }
    
    private void excludeFormRegistry(Enchantment enchantment)
    {
        for (EnchantmentEntry entry : enchantments)
            if (entry.getEnchantment().effectId == enchantment.effectId) enchantments.remove(entry);
    }

    private void excludeFormRegistry(String sEnchantment)
    {
        for (Enchantment enchantment : Enchantment.enchantmentsList)
            if (enchantment != null && enchantment.getName().toLowerCase().contains(sEnchantment.toLowerCase())) excludeFormRegistry(enchantment);
    }

    public void removeAll(String[] excludedEnchants)
    {
        for (String enchant : excludedEnchants)
            excludeFormRegistry(enchant);
    }
}
