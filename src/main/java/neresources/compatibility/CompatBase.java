package neresources.compatibility;

import neresources.registry.MobEntry;
import neresources.registry.MobRegistry;
import neresources.registry.OreEntry;
import neresources.registry.OreRegistry;
import neresources.utils.LogHelper;
import neresources.utils.ModList;

public class CompatBase
{
    public boolean load(ModList mod)
    {
        if (mod.isLoaded()) {
            LogHelper.info("Loading compatibility for "+mod.toString());
            init();
            return true;
        }
        else
        {
            LogHelper.info(mod.toString()+" not loaded - skipping");
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
