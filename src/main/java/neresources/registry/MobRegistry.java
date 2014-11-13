package neresources.registry;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MobRegistry
{
    private Map<String, MobEntry> registry = new LinkedHashMap<String, MobEntry>();

    private static MobRegistry instance = null;

    public static MobRegistry getInstance()
    {
        if (instance == null)
            return instance = new MobRegistry();
        return instance;
    }

    public boolean registerMob(MobEntry entry)
    {
        return registerMob(entry.getMobName(), entry);
    }

    public boolean registerMob(String key, MobEntry entry)
    {
        if (!registry.containsKey(key))
        {
            registry.put(key, entry);
            return true;
        }
        return false;
    }

    public MobEntry getMobEntry(String key)
    {
        return registry.get(key);
    }

    public List<MobEntry> getMobsThatDropItem(ItemStack item)
    {
        List<MobEntry> list = new ArrayList<MobEntry>();
        for (MobEntry entry : registry.values())
            if (entry.dropsItem(item)) list.add(entry);
        return list;
    }

    public List<MobEntry> getMobs()
    {
        return new ArrayList<MobEntry>(registry.values());
    }
}
