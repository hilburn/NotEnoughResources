package neresources.compatibility;

import neresources.api.messages.RegisterOreMessage;
import neresources.entries.MobEntry;
import neresources.entries.PlantEntry;
import neresources.registry.MobRegistry;
import neresources.registry.OreRegistry;
import neresources.registry.PlantRegistry;
import neresources.utils.LogHelper;
import neresources.utils.ModList;
import neresources.utils.ReflectionHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class CompatBase
{
    protected static World world = DimensionManager.getWorld(0);
    public static boolean load(ModList mod)
    {
        if (mod.isLoaded())
        {
            LogHelper.info("Loading compatibility for " + mod.toString());
            CompatBase compat = (CompatBase)ReflectionHelper.initialize(mod.compatClass());
            compat.init();
            return true;
        } else
        {
            LogHelper.info(mod.toString() + " not loaded - skipping");
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

    public void registerOre(RegisterOreMessage message)
    {
        OreRegistry.registerOre(message);
    }

    public void registerPlant(PlantEntry entry)
    {
        PlantRegistry.getInstance().registerPlant(entry);
    }
}
