package neresources.drop;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DropItem
{
    public int minDrop, maxDrop;
    public ItemStack item;
    public float chance;

    public DropItem (ItemStack item, int minDrop, int maxDrop)
    {
        this(item, minDrop, maxDrop, 1F);
    }

    public DropItem (ItemStack item, int minDrop, int maxDrop, float chance)
    {
        this.item = item;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.chance = chance;
    }

    public DropItem(Item item, int minDrop, int maxDrop)
    {
        this(new ItemStack(item),minDrop,maxDrop,1F);
    }

    public DropItem(Item item, int itemDamage, int minDrop, int maxDrop)
    {
        this(new ItemStack(item, 1, itemDamage),minDrop,maxDrop,1F);
    }

    public DropItem(Item item, int minDrop, int maxDrop, float chance)
    {
        this(new ItemStack(item), minDrop, maxDrop, chance);
    }

    public DropItem(Item item, int itemDamage, int minDrop, int maxDrop, float chance)
    {
        this(new ItemStack(item, 1, itemDamage), minDrop, maxDrop, chance);
    }

    public String toString()
    {
        if (minDrop==maxDrop) return minDrop + getDropChance();
        return minDrop + "-" + maxDrop + getDropChance();
    }

    private String getDropChance()
    {
        return chance == 1F ? "" : " (" + String.valueOf((int)(chance * 100)) + "%)";
    }
}
