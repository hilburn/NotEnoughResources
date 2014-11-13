package neiresources.compatibility;

import cpw.mods.fml.common.Loader;
import neiresources.registry.MobEntry;
import neiresources.registry.MobRegistry;
import neiresources.registry.OreEntry;
import neiresources.registry.OreRegistry;

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

    public void registerOre(String string, OreEntry entry)
    {
        OreRegistry.getInstance().registerOre(string,entry);
    }

    public void registerOre(OreEntry entry)
    {
        OreRegistry.getInstance().registerOre(entry);
    }
}
