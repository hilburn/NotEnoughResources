package neiresources.registry;

import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
        return registerMob(entry.getMobName(),entry);
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

    public List<MobRegistryEntry> getMobsThatDropItem(Item item)
    {
        List<MobRegistryEntry> list = new ArrayList<MobRegistryEntry>();
        for (MobRegistryEntry entry : registry.values())
            if (entry.dropsItem(item)) list.add(entry);
        return list;
    }
}
