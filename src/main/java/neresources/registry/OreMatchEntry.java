package neresources.registry;

import neresources.api.distributions.DistributionBase;
import neresources.api.utils.DistributionHelpers;
import neresources.api.utils.KeyGen;
import neresources.config.Settings;
import neresources.utils.ColorHelper;
import net.minecraft.item.ItemStack;

import java.util.*;


public class OreMatchEntry
{
    Map<String, Boolean> silkTouchMap = new LinkedHashMap<String, Boolean>();
    Map<ItemStack, DistributionBase> ores = new LinkedHashMap<ItemStack, DistributionBase>();
    private double[] chances;
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
        silkTouchMap.put(KeyGen.getKey(entry.getOre()), entry.needSilkTouch());
        ores.put(entry.getOre(), entry.getDistribution());
        calcChances();
        if (colour == ColorHelper.BLACK) colour = entry.getColour();
    }

    private void calcChances()
    {
        chances = new double[256];
        minY = 256;
        maxY = 0;
        for (DistributionBase distribution: ores.values())
        {
            int i = 0;
            for (double chance : distribution.getDistribution())
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
            bestY = distribution.getBestHeight();
        }
        if (minY == 256) minY = 0;
        if (maxY == 0) maxY = 255;
        if (ores.size()>1) bestY = DistributionHelpers.calculateMeanLevel(chances,40,0,1000);
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

    public boolean isSilkTouchNeeded(ItemStack itemStack)
    {
        Boolean silkTouch = this.silkTouchMap.get(KeyGen.getKey(itemStack));
        return silkTouch == null ? false : silkTouch;
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
            if (drop.isItemEqual(removeDrop)) drops.remove(drop);
    }

    public List<ItemStack> getDrops()
    {
        return drops;
    }

    public List<ItemStack> getOresAndDrops()
    {
        List<ItemStack> list = new LinkedList<ItemStack>(ores.keySet());
        list.addAll(drops);
        return list;
    }
}
