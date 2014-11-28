package neresources.registry;

import neresources.api.entry.IMobEntry;
import neresources.utils.MobHelper;
import net.minecraft.item.ItemStack;

import java.util.*;

public class MobRegistry
{
    private Set<IMobEntry> registry = new LinkedHashSet<IMobEntry>();

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
        if (!registry.contains(entry))
        {
            registry.add(entry);
            return true;
        }
        return false;
    }

    public IMobEntry getMobEntry(String key)
    {
        return null;//registry.get(key);
    }

    public List<IMobEntry> getMobsThatDropItem(ItemStack item)
    {
        List<IMobEntry> list = new ArrayList<IMobEntry>();
        for (IMobEntry entry : registry)
            if (MobHelper.dropsItem(entry, item)) list.add(entry);
        return list;
    }

    public List<IMobEntry> getMobs()
    {
        return new ArrayList<IMobEntry>(registry);
    }
}
