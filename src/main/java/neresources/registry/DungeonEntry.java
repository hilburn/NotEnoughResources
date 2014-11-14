package neresources.registry;

import neresources.api.IDungeonEntry;
import neresources.utils.WeightedRandomChestContentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import java.util.Random;

public class DungeonEntry implements IDungeonEntry
{
    private String name;
    private ChestGenHooks chestGenHooks;
    private int totalWeight = 0;

    public DungeonEntry(String name, ChestGenHooks chestGenHooks)
    {
        setName(name);
        setContents(chestGenHooks);
    }

    public void setContents(ChestGenHooks chestGenHooks)
    {
        this.chestGenHooks = chestGenHooks;
        for (WeightedRandomChestContent chestItem : chestGenHooks.getItems(DungeonRegistry.rand))
            totalWeight += chestItem.itemWeight;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public ChestGenHooks getChestGenHooks()
    {
        return this.chestGenHooks;
    }

    public int getAverageStacks()
    {
        return (this.chestGenHooks.getMax() + this.chestGenHooks.getMin()) / 2;
    }

    public int getTotalWeight()
    {
        return totalWeight;
    }

    public double getChance(WeightedRandomChestContent content)
    {
        return ((double) getAverageStacks() * content.itemWeight) / this.totalWeight;
    }

    public boolean hasItem(ItemStack item)
    {
        for (WeightedRandomChestContent chestContent : getChestGenHooks().getItems(DungeonRegistry.rand))
            if (chestContent.theItemId.isItemEqual(item)) return true;
        return false;
    }

    @Override
    public String getKey()
    {
        return getName();
    }
}
