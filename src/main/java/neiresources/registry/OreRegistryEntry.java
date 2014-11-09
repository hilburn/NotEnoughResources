package neiresources.registry;

import java.util.ArrayList;
import java.util.List;

public class OreRegistryEntry {

    private List<OreEntry> oreEntryList = new ArrayList<OreEntry>();

    OreRegistryEntry(OreEntry oreEntry)
    {
        oreEntryList.add(oreEntry);
    }

    public void add(OreEntry oreEntry)
    {
        oreEntryList.add(oreEntry);
    }


    public double[] getChances()
    {
        double[] result = new double[256];
        for (int i=0;i<256;i++)
        {
            double chance = 0;
            for (OreEntry oreEntry:oreEntryList)
                chance+=oreEntry.getChance(i);
            result[i]=chance;
        }
        return result;
    }
}
