package neiresources.registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class OreRegistry
{
    private Map<String, OreRegistryEntry> registry = new LinkedHashMap<String, OreRegistryEntry>();

    public boolean registerOre(String oreName, OreEntry oreEntry) {

        OreRegistryEntry put = registry.get(oreName);
        if (put == null) put = new OreRegistryEntry(oreEntry);
        else put.add(oreEntry);
        registry.put(oreName, put);
        return true;
    }
}
