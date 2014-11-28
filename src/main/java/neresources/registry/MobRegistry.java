package neresources.registry;

import neresources.api.entry.IMobEntry;
import neresources.api.entry.IModifyMob;
import neresources.api.utils.DropItem;
import neresources.utils.ClassScraper;
import neresources.utils.MobHelper;
import net.minecraft.entity.monster.EntitySkeleton;
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

    public void removeMobDrops(IModifyMob entry)
    {
        int wither = entry.witherSkeleton()?1:0;
        for (IMobEntry regEntry:registry)
        {

            Set classes = new LinkedHashSet();
            if (entry.isExactMatch())
                classes.add(regEntry.getEntity().getClass());
            else classes = ClassScraper.getGeneralizations(regEntry.getEntity().getClass());
            for (Object clazz : classes)
            {
                if (clazz == entry.applyToClass())
                {
                    if (clazz == EntitySkeleton.class)
                        if (((EntitySkeleton)regEntry.getEntity()).getSkeletonType() != wither) break;
                    for (ItemStack item : entry.removeItems())
                        ((MobEntry) regEntry).removeDrop(item);
                }
            }
        }
    }

    public void addMobDrops(IModifyMob entry)
    {
        int wither = entry.witherSkeleton()?1:0;
        for (IMobEntry regEntry:registry)
        {

            Set classes = new LinkedHashSet();
            if (entry.isExactMatch())
                classes.add(regEntry.getEntity().getClass());
            else classes = ClassScraper.getGeneralizations(regEntry.getEntity().getClass());
            for (Object clazz : classes)
            {
                if (clazz == entry.applyToClass())
                {
                    if (clazz == EntitySkeleton.class)
                        if (((EntitySkeleton)regEntry.getEntity()).getSkeletonType() != wither) break;
                    for (DropItem item : entry.addItems())
                        ((MobEntry) regEntry).addDrop(item);
                }
            }
        }
    }
}
