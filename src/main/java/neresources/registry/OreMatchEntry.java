package neresources.registry;

import neresources.api.utils.DistributionHelpers;
import neresources.config.Settings;
import neresources.utils.ColorHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.item.ItemStack;

import java.util.*;


public class OreMatchEntry
{
    List<OreEntry> oreEntryList = new ArrayList<OreEntry>();
    private float[] chances;
    private int minY;
    private int maxY;
    private int bestY;
    private int colour;
    List<ItemStack> drops = new ArrayList<ItemStack>();

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
        chances = new float[256];
        minY = 256;
        maxY = 0;
        for (OreEntry entry: oreEntryList)
        {
            int i = 0;
            for (float chance : entry.getDistribution().getDistribution())
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

    public float[] getChances()
    {
        return getChances(Settings.EXTRA_RANGE);
    }

    public float[] getChances(int extraRange)
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

    public List<ItemStack> getOres()
    {
        List<ItemStack> list = new LinkedList<ItemStack>();
        for (OreEntry oreEntry : oreEntryList)
            Collections.addAll(list, oreEntry.getOreMatches());
        return list;
    }

    public boolean isSilkTouchNeeded(ItemStack itemStack)
    {
        return itemStack != null && drops.size() > 0 && (Block.getBlockFromItem(itemStack.getItem()) instanceof BlockOre || Block.getBlockFromItem(itemStack.getItem()) instanceof BlockRedstoneOre);
    }

    public int getColour()
    {
        return colour;
    }

    public void addDrop(ItemStack nonOre) {
        drops.add(nonOre);
    }

    public void removeDrop(ItemStack removeDrop)
    {
        for (ItemStack drop:drops)
        {
            if (drop.isItemEqual(removeDrop)) drops.remove(drop);
        }
    }

    public List<ItemStack> getDrops()
    {
        return drops;
    }

    public List<ItemStack> getOresAndDrops()
    {
        List<ItemStack> list = new LinkedList<ItemStack>(getOres());
        list.addAll(drops);
        return list;
    }
}
