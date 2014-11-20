package neresources.utils;

import neresources.api.entry.IDungeonEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;

import java.util.Random;

public class DungeonHelper
{
    public static Random rand = new Random();

    public static int getTotalWeight(IDungeonEntry entry)
    {
        int totalWeight = 0;
        for (WeightedRandomChestContent chestItem : entry.getChestGenHooks().getItems(rand))
            totalWeight += chestItem.itemWeight;
        return totalWeight;
    }

    public static int getAverageStacks(IDungeonEntry entry)
    {
        return (entry.getChestGenHooks().getMax() + entry.getChestGenHooks().getMin()) / 2;
    }

    public static double getChance(IDungeonEntry entry, WeightedRandomChestContent content)
    {
        return ((double) getAverageStacks(entry) * content.itemWeight) / getTotalWeight(entry);
    }

    public static WeightedRandomChestContent[] getContents(IDungeonEntry entry)
    {
        return WeightedRandomChestContentHelper.sort(entry.getChestGenHooks().getItems(rand), entry);
    }

    public static boolean hasItem(IDungeonEntry entry, ItemStack item)
    {
        for (WeightedRandomChestContent chestContent : entry.getChestGenHooks().getItems(rand))
            if (chestContent.theItemId.isItemEqual(item)) return true;
        return false;
    }
}
