package neresources.api.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DropItem
{
    public int minDrop, maxDrop;
    public ItemStack item;
    public float chance;

    /**
     * @param item    The dropped {@link net.minecraft.item.ItemStack} (chance for drop will be 100%)
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     */
    public DropItem(ItemStack item, int minDrop, int maxDrop)
    {
        this(item, minDrop, maxDrop, 1F);
    }

    /**
     * @param item    The dropped {@link net.minecraft.item.ItemStack}
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     * @param chance  the chance the {@param item} gets dropped
     */
    public DropItem(ItemStack item, int minDrop, int maxDrop, float chance)
    {
        this.item = item;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.chance = chance;
    }

    /**
     * @param item    The dropped {@link net.minecraft.item.Item} (chance for drop will be 100% and the itemDamage will be default)
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     */
    public DropItem(Item item, int minDrop, int maxDrop)
    {
        this(new ItemStack(item), minDrop, maxDrop, 1F);
    }

    /**
     * @param item       The dropped {@link net.minecraft.item.Item} (chance for drop will be 100%)
     * @param itemDamage the damage on the item
     * @param minDrop    the maximum amount dropped
     * @param maxDrop    the minimum amount dropped
     */
    public DropItem(Item item, int itemDamage, int minDrop, int maxDrop)
    {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, 1F);
    }

    /**
     * @param item    The dropped {@link net.minecraft.item.Item}
     * @param minDrop the maximum amount dropped
     * @param maxDrop the minimum amount dropped
     * @param chance  the chance the {@param item} gets dropped
     */
    public DropItem(Item item, int minDrop, int maxDrop, float chance)
    {
        this(new ItemStack(item), minDrop, maxDrop, chance);
    }

    /**
     * @param item       The dropped {@link net.minecraft.item.Item}
     * @param itemDamage the damage on the item
     * @param minDrop    the maximum amount dropped
     * @param maxDrop    the minimum amount dropped
     * @param chance     the chance the {@param item} gets dropped
     */
    public DropItem(Item item, int itemDamage, int minDrop, int maxDrop, float chance)
    {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, chance);
    }

    public String toString()
    {
        if (minDrop == maxDrop) return minDrop + getDropChance();
        return minDrop + "-" + maxDrop + getDropChance();
    }

    private String getDropChance()
    {
        return chance == 1F ? "" : " (" + String.valueOf((int) (chance * 100)) + "%)";
    }
}
