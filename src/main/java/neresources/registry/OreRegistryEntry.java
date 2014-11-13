package neresources.registry;

import neresources.config.Settings;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreRegistryEntry
{
    private List<OreEntry> oreEntryList = new ArrayList<OreEntry>();
    private ItemStack ore;
    private double[] chances;
    private int minY;
    private int maxY;
    private int bestY;

    OreRegistryEntry(OreEntry oreEntry)
    {
        ore = oreEntry.getOre();
        oreEntryList.add(oreEntry);
        calcChances();
    }

    public void add(OreEntry oreEntry)
    {
        oreEntryList.add(oreEntry);
        calcChances();
    }

    public void remove(String modName)
    {
        OreEntry remove=null;
        for (OreEntry entry:oreEntryList)
        {
            if (entry.getModName().equals(modName))
                remove = entry;
        }
        if (remove!=null) oreEntryList.remove(remove);
        calcChances();
    }

    private void calcChances()
    {
        chances = new double[256];
        minY=300;
        maxY=0;
        bestY = 0;
        for (int i = 0; i < 256; i++)
        {
            double chance = 0;
            for (OreEntry oreEntry : oreEntryList)
                chance += oreEntry.getChance(i);
            if (chance>0)
            {
                if (minY==300)
                    minY=i;
                maxY=i;
                if (chance>chances[bestY]) bestY = i;
            }
            chances[i] = chance;
        }
        if (oreEntryList.size()==1 && !(oreEntryList.get(0).getBestY()<0)) bestY = oreEntryList.get(0).getBestY();
    }

    public double[] getChances()
    {
        return getChances(Settings.EXTRA_RANGE);
    }

    public double[] getChances(int extraRange)
    {
        return Arrays.copyOfRange(chances,Math.max(minY-extraRange,0),Math.min(maxY+extraRange,255));
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

    public ItemStack getOre()
    {
        return ore;
    }
}
