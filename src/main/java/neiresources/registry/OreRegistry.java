package neiresources.registry;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class OreRegistry
{
    private Map<String, OreRegistryEntry> registry = new LinkedHashMap<String, OreRegistryEntry>();

    public boolean registerOre(String oreName, OreEntry oreEntry)
    {
        OreRegistryEntry put = registry.get(oreName);
        if (put == null) put = new OreRegistryEntry(oreEntry);
        else put.add(oreEntry);
        registry.put(oreName, put);
        return true;
    }

    public double[] getChances(String oreName)
    {
        return getChances(oreName,OreRegistryEntry.EXTRA_RANGE);
    }

    public double[] getChances(String oreName, int extraRange)
    {
        OreRegistryEntry entry = registry.get(oreName);
        if (entry!=null)
            return entry.getChances(extraRange);
        return null;
    }

}
