package neresources.registry;

import neresources.utils.WeightedRandomChestContentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Random;

public class DungeonEntry
{
    private String name;
    private ChestGenHooks chestGenHooks;
    private int totalWeight = 0;

    private Random rand = new Random();

    public DungeonEntry(String name, ChestGenHooks chestGenHooks)
    {
        setName(name);
        setContents(chestGenHooks);
    }

    public void setContents(ChestGenHooks chestGenHooks)
    {
        this.chestGenHooks = chestGenHooks;
        for (WeightedRandomChestContent chestItem : chestGenHooks.getItems(rand))
            totalWeight += chestItem.itemWeight;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public int getAverageStacks()
    {
        return (this.chestGenHooks.getMax() + this.chestGenHooks.getMin()) / 2;
    }

    public WeightedRandomChestContent[] getContents()
    {
        return WeightedRandomChestContentHelper.sort(chestGenHooks.getItems(rand), this);
    }

    public int getTotalWeight()
    {
        return totalWeight;
    }

    public String getNumStacks()
    {
        int max = this.chestGenHooks.getMax();
        int min = this.chestGenHooks.getMin();
        if (min == max) return max + " Stacks";
        return min + " - " + max + " Stacks";
    }

    public double getChance(WeightedRandomChestContent item)
    {
        return ((double) getAverageStacks() * item.itemWeight) / this.totalWeight;
    }

    public boolean hasItem(ItemStack item)
    {
        for (WeightedRandomChestContent chestContent : getContents())
            if (chestContent.theItemId.isItemEqual(item)) return true;
        return false;
    }

}
