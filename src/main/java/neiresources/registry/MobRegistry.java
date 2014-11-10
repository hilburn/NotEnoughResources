package neiresources.registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MobRegistry
{
    private Map<String, MobRegistryEntry> registry = new LinkedHashMap<String, MobRegistryEntry>();

    private static MobRegistry instance = null;

    public static MobRegistry getInstance()
    {
        if (instance==null)
            return instance=new MobRegistry();
        return instance;
    }

    public boolean registerMob(MobRegistryEntry entry)
    {
        return registerMob(entry.getName(),entry);
    }

    public boolean registerMob(String key, MobRegistryEntry entry)
    {
        if (!registry.containsKey(key)) {
            registry.put(key, entry);
            return true;
        }
        return false;
    }

    public MobRegistryEntry getMobEntry(String key)
    {
        return registry.get(key);
    }
}
