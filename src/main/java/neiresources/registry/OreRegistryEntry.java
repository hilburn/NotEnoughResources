package neiresources.registry;

import scala.Array;

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

    }

    private void calcChances()
    {
        chances = new double[256];
        for (int i = 0; i < 256; i++)
        {
            double chance = 0;
            for (OreEntry oreEntry : oreEntryList)
                chance += oreEntry.getChance(i);
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
