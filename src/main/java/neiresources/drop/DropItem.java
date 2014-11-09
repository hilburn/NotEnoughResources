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
}
