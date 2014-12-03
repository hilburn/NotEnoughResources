package neresources.registry;

import net.minecraft.item.ItemStack;

public class PlantDrop
{
    private ItemStack seed;
    private int itemWeight;

    public PlantDrop(ItemStack seed, int itemWeight)
    {
        this.seed = seed;
        this.itemWeight = itemWeight;
    }

    public ItemStack getDrop()
    {
        return seed;
    }

    public int getWeight()
    {
        return itemWeight;
    }
}
