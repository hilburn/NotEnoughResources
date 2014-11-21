package neresources.registry;

import neresources.api.utils.DistributionHelpers;
import neresources.config.Settings;
import neresources.utils.ColorHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OreMatchEntry
{
    List<OreEntry> oreEntryList = new ArrayList<OreEntry>();
    private double[] chances;
    private int minY;
    private int maxY;
    private int bestY;
    private int colour;

    public OreMatchEntry(OreEntry entry)
    {
        this.add(entry);
    }

    public void add(OreEntry entry)
    {
        oreEntryList.add(entry);
        calcChances();
        if (colour== ColorHelper.BLACK)colour = entry.getColour();
    }

    private void calcChances()
    {
        chances = new double[256];
        minY = 256;
        maxY = 0;
        for (OreEntry entry: oreEntryList)
        {
            int i = 0;
            for (double chance : entry.getDistribution().getDistribution())
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
            bestY = entry.getDistribution().getBestHeight();
        }
        if (minY == 256) minY = 0;
        if (maxY == 0) maxY = 255;
        if (oreEntryList.size()>1) bestY = DistributionHelpers.calculateMeanLevel(chances,40,0,1000);
    }

    public double[] getChances()
    {
        return getChances(Settings.EXTRA_RANGE);
    }

    public double[] getChances(int extraRange)
    {
        return Arrays.copyOfRange(chances, Math.max(minY - extraRange, 0), Math.min(maxY + extraRange + 2, 255));
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
        ItemStack[] result = new ItemStack[oreEntryList.size()];
        for (int i = 0; i < oreEntryList.size(); i++)
            result[i] = oreEntryList.get(i).getOre();
        return result;
    }

    public boolean isSilkTouchNeeded(ItemStack stack)
    {
        OreEntry value = getIOreEntry(stack);
        if (value!=null)
        {
            return value.silkTouch(stack);
        }
        return false;
    }

    public OreEntry getIOreEntry(ItemStack itemStack)
    {
        if (itemStack != null)
        {
            for (OreEntry entry: oreEntryList)
            {
                if (itemStack.isItemEqual(entry.getOre())) return entry;
            }
        }
        return null;
    }

    public OreEntry getIOreEntry(int id)
    {
        if (id<0 || id>=oreEntryList.size()) return null;
        return oreEntryList.get(id);
    }

    public int getColour()
    {
        return colour;
    }

    public void addDrop(ItemStack nonOre) {
    }
}
