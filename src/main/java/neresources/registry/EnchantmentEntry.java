package neresources.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.StatCollector;

public class EnchantmentEntry
{
    private Enchantment enchantment;

    public EnchantmentEntry(Enchantment enchantment)
    {
        this.enchantment = enchantment;
    }

    public String getTranslatedWithLevels()
    {
        String s = this.enchantment.getTranslatedName(1);
        if (this.enchantment.getMinLevel() != this.enchantment.getMaxLevel())
            s += "-" + StatCollector.translateToLocal("enchantment.level." + this.enchantment.getMaxLevel());
        return s;
    }

    public Enchantment getEnchantment()
    {
        return enchantment;
    }
}
