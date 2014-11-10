package neiresources.compatibility;

import cpw.mods.fml.common.Loader;
import neiresources.registry.*;

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

    public void registerMob(MobRegistryEntry entry)
    {
        MobRegistry.getInstance().registerMob(entry);
    }

    public void registerOre(String string, OreEntry entry)
    {
        OreRegistry.getInstance().registerOre(string,entry);
    }
}
