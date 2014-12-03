package neresources.registry;

import net.minecraft.item.ItemStack;

public class PlantDrop
{
    private ItemStack seed;
    private int itemWeight;
    private int minDrop, maxDrop;
    private float chance;
    private DropKind dropKind;

    public enum DropKind
    {
        chance, weight, minMax
    }

    public PlantDrop(ItemStack seed, int itemWeight)
    {
        this.seed = seed;
        this.itemWeight = itemWeight;
        this.dropKind = DropKind.weight;
    }

    public PlantDrop(ItemStack seed, float chance)
    {
        this.seed = seed;
        this.chance = chance;
        this.dropKind = DropKind.chance;
    }

    public PlantDrop(ItemStack seed, int minDrop, int maxDrop)
    {
        this.seed = seed;
        this.minDrop = minDrop;
        this.maxDrop = maxDrop;
        this.dropKind = DropKind.minMax;
    }

    public ItemStack getSeed()
    {
        return seed;
    }

    public int getWeight()
    {
        return itemWeight;
    }

    public int getMinDrop()
    {
        return minDrop;
    }

    public int getMaxDrop()
    {
        return maxDrop;
    }

    public float getChance()
    {
        return chance;
    }

    public DropKind getDropKind()
    {
        return dropKind;
    }
}
