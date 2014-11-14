package neresources.compatibility;

import cpw.mods.fml.common.Loader;
import neresources.api.IOreEntry;
import neresources.registry.MobEntry;
import neresources.registry.MobRegistry;
import neresources.registry.OreEntry;
import neresources.registry.OreRegistry;

public class CompatBase
{

    public CompatBase(String name)
    {
        if (Loader.isModLoaded(name))
            init();
    }

    public void init()
    {
    }

    public void registerMob(MobEntry entry)
    {
        MobRegistry.getInstance().registerMob(entry);
    }

    public void registerOre(OreEntry entry)
    {
        OreRegistry.getInstance().registerOre(entry);
    }
}
