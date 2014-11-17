package neresources.compatibility;

import neresources.api.NEResourcesAPI;
import neresources.api.entry.IDungeonEntry;
import neresources.api.entry.IMobEntry;
import neresources.api.entry.IOreEntry;
import neresources.registry.DungeonRegistry;
import neresources.registry.GrassSeedRegistry;
import neresources.registry.MobRegistry;
import neresources.registry.OreRegistry;
import neresources.utils.ModList;

public class Compatibility
{

    public static void init()
    {
        for (ModList mod : ModList.values())
        {
            mod.initialise();
        }

        for (Object entry: NEResourcesAPI.getRegistryAPI().values())
        {
            if (entry instanceof IMobEntry) MobRegistry.getInstance().registerMob((IMobEntry)entry);
            else if (entry instanceof IDungeonEntry) DungeonRegistry.getInstance().registerDungeonEntry((IDungeonEntry) entry);
            else if (entry instanceof IOreEntry) OreRegistry.getInstance().registerOre(((IOreEntry)entry));
        }
    }
}
