package neiresources.drop;

import net.minecraft.item.Item;

public class DropItem
{
    public int minDrop, maxDrop;
    public Item item;
    public float chance;

    public DropItem(Item item, int minDrop, int maxDrop)
    {
        this.item = item;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.chance = 1F;
    }

    public DropItem(Item item, int minDrop, int maxDrop, float chance)
    {
        this(item, minDrop, maxDrop);
        this.chance = chance;
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
