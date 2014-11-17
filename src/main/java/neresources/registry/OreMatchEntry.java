package neresources.registry;

import neresources.api.entry.IOreEntry;
import neresources.api.utils.DistributionHelpers;
import neresources.config.Settings;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class OreMatchEntry
{
    Map<ItemStack, IOreEntry> oreEntryMap = new LinkedHashMap<ItemStack, IOreEntry>();
    private double[] chances;
    private int minY;
    private int maxY;
    private int bestY;
    private int colour = 0;

    public OreMatchEntry(Map<ItemStack, IOreEntry> entries)
    {
        oreEntryMap = entries;
        calcChances();
        for (Map.Entry<ItemStack, IOreEntry> entry: entries.entrySet()) {
            int entryColour = entry.getValue().getColour(entry.getKey());
            if (colour == 0 && entryColour != 0) colour = entryColour;
        }
    }

    public OreMatchEntry(ItemStack stack, IOreEntry entry)
    {
        oreEntryMap.put(stack,entry);
        calcChances();
        colour = entry.getColour(stack);
    }

    public void add(ItemStack stack, IOreEntry entry)
    {
        oreEntryMap.put(stack,entry);
        calcChances();
        if (colour==0) colour = entry.getColour(stack);
    }

    private void calcChances()
    {
        chances = new double[256];
        minY = 256;
        maxY = 0;
        for (Map.Entry<ItemStack, IOreEntry> entry: oreEntryMap.entrySet())
        {
            int i = 0;
            for (double chance : entry.getValue().getDistribution(entry.getKey()).getDistribution())
            {
                if (++i == chances.length) break;
                chances[i] += chance;
                if (chances[i] > 0)
                {
                    if (minY>i)
                        minY = i;
                    if (i>maxY)
                        maxY = i;
                }
            }
            bestY = entry.getValue().getDistribution(entry.getKey()).getBestHeight();
        }
        if (minY == 256) minY = 0;
        if (maxY == 0) maxY = 255;
        if (oreEntryMap.size()>1) bestY = DistributionHelpers.calculateMeanLevel(chances,40,0,1000);
    }

    public double[] getChances()
    {
        return getChances(Settings.EXTRA_RANGE);
    }

    public double[] getChances(int extraRange)
    {
        return Arrays.copyOfRange(chances, Math.max(minY - extraRange, 0), Math.min(maxY + extraRange +2, 255));
    }

    public int getBestY()
    {
        return bestY;
    }

    public int getMinY()
    {
        return minY;
    }

    public int getMaxY()
    {
        return maxY;
    }

    public ItemStack[] getOres()
    {
        return oreEntryMap.keySet().toArray(new ItemStack[oreEntryMap.keySet().size()]);
    }

    public boolean isSilkTouchNeeded(ItemStack stack)
    {
        IOreEntry value = getIOreEntry(stack);
        if (value!=null)
        {
            return value.silkTouch(stack);
        }
        return false;
    }

    public IOreEntry getIOreEntry(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            for (ItemStack keyStack : oreEntryMap.keySet())
            {
                if (itemStack.isItemEqual(keyStack)) return oreEntryMap.get(keyStack);
            }
        }
        return null;
    }

    public IOreEntry getIOreEntry(int id)
    {
        return oreEntryMap.get(getOres()[id]);
    }

    public int getColour()
    {
        return colour;
    }
}
