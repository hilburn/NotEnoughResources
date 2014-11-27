package neresources.compatibility;

import neresources.registry.MobEntry;
import neresources.registry.MobRegistry;
import neresources.registry.OreEntry;
import neresources.registry.OreRegistry;
import neresources.utils.ModList;

public class CompatBase
{
    public boolean load(ModList mod)
    {
        if (mod.isLoaded()) {
            init();
            return true;
        }
        return false;
    }

    protected void init()
    {
    }

    public void registerMob(MobEntry entry)
    {
        MobRegistry.getInstance().registerMob(entry);
    }

    public void registerOre(OreEntry entry)
    {
        OreRegistry.getInstance().register(entry);
    }
}
