package neiresources.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OreRegistryEntry
{
    public static int EXTRA_RANGE = 3;
    private List<OreEntry> oreEntryList = new ArrayList<OreEntry>();
    private double[] chances;
    private int minY;
    private int maxY;

    OreRegistryEntry(OreEntry oreEntry)
    {
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
    }

    private void calcChances()
    {
        chances = new double[256];
        minY=300;
        maxY=0;
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
            }
            chances[i] = chance;
        }
    }

    public double[] getChances()
    {
        return getChances(EXTRA_RANGE);
    }

    public double[] getChances(int extraRange)
    {
        return Arrays.copyOfRange(chances,Math.max(minY-extraRange,0),Math.min(maxY+extraRange,255));
    }
}
