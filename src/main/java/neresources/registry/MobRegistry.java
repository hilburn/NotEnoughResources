package neresources.registry;

import neresources.api.entry.IMobEntry;
import neresources.utils.APIScraper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MobRegistry
{
    private Map<String, IMobEntry> registry = new LinkedHashMap<String, IMobEntry>();

    private static MobRegistry instance = null;

    public static MobRegistry getInstance()
    {
        if (instance == null)
            return instance = new MobRegistry();
        return instance;
    }

    public boolean registerMob(IMobEntry entry)
    {
        return registerMob(entry.getMobName(), entry);
    }

    public boolean registerMob(String key, IMobEntry entry)
    {
        if (!registry.containsKey(key))
        {
            registry.put(key, entry);
            return true;
        }
        return false;
    }

    public IMobEntry getMobEntry(String key)
    {
        return registry.get(key);
    }

    public List<IMobEntry> getMobsThatDropItem(ItemStack item)
    {
        List<IMobEntry> list = new ArrayList<IMobEntry>();
        for (IMobEntry entry : registry.values())
            if (entry.dropsItem(item)) list.add(entry);
        return list;
    }

    public List<IMobEntry> getMobs()
    {
        List<IMobEntry> list = new ArrayList<IMobEntry>(registry.values());
        list.addAll(APIScraper.getCollection(IMobEntry.class));
        return list;
    }
}
