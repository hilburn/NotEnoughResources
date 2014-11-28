package neresources.api.utils;

import neresources.api.utils.conditionals.Conditional;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DropItem
{
    public int minDrop, maxDrop;
    public ItemStack item;
    public float chance;
    public List<String> conditionals = new ArrayList<String>();


    /**
     * @param item    The dropped {@link net.minecraft.item.ItemStack} (chance for drop will be 100%)
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     */
    public DropItem(ItemStack item, int minDrop, int maxDrop, Conditional... conditionals)
    {
        this(item, minDrop, maxDrop, 1F, conditionals);
    }

    /**
     * @param item    The dropped {@link net.minecraft.item.ItemStack}
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     * @param chance  the chance the {@param item} gets dropped
     */
    public DropItem(ItemStack item, int minDrop, int maxDrop, float chance, Conditional... conditionals)
    {
        this.item = item;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.chance = chance;
        for (Conditional conditional:conditionals)
            this.conditionals.add(conditional.toString());
    }

    /**
     * @param item    The dropped {@link net.minecraft.item.Item} (chance for drop will be 100% and the itemDamage will be default)
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     */
    public DropItem(Item item, int minDrop, int maxDrop, Conditional... conditionals)
    {
        this(new ItemStack(item), minDrop, maxDrop, 1F, conditionals);
    }

    /**
     * @param item       The dropped {@link net.minecraft.item.Item} (chance for drop will be 100%)
     * @param itemDamage the damage on the item
     * @param minDrop    the maximum amount dropped
     * @param maxDrop    the minimum amount dropped
     */
    public DropItem(Item item, int itemDamage, int minDrop, int maxDrop, Conditional... conditionals)
    {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, 1F, conditionals);
    }

    /**
     * @param item    The dropped {@link net.minecraft.item.Item}
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     * @param chance  the chance the {@param item} gets dropped
     */
    public DropItem(Item item, int minDrop, int maxDrop, float chance, Conditional... conditionals)
    {
        this(new ItemStack(item), minDrop, maxDrop, chance, conditionals);
    }

    /**
     * @param item       The dropped {@link net.minecraft.item.Item}
     * @param itemDamage the damage on the item
     * @param minDrop    the maximum amount dropped
     * @param maxDrop    the minimum amount dropped
     * @param chance     the chance the {@param item} gets dropped
     */
    public DropItem(Item item, int itemDamage, int minDrop, int maxDrop, float chance, Conditional... conditionals)
    {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, chance, conditionals);
    }

    public String toString()
    {
        if (minDrop == maxDrop) return minDrop + getDropChance();
        return minDrop + "-" + maxDrop + getDropChance();
    }

    private String getDropChance()
    {
        return chance < 1F ?" (" + formatChance() + "%)":"";
    }

    private String formatChance()
    {
        float chance = this.chance * 100;
        if (chance<10) return String.format("%.1f",chance);
        return String.format("%2d",(int)chance);
    }

    private List<String> getTooltipText()
    {
        return conditionals;
    }
}
