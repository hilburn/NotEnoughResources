package neresources.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import neresources.api.utils.restrictions.BlockRestriction;
import neresources.api.utils.restrictions.DimensionRegistry;
import neresources.compatibility.mystcraft.MystCompat;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEventHelper
{
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (!DimensionRegistry.contains(event.world.provider.dimensionId))
        {
            DimensionRegistry.registerDimension(BlockRestriction.STONE, event.world.provider.dimensionId, ModList.mystcraft.isLoaded() && MystCompat.isMystDim(event.world.provider.dimensionId));
        }
    }
}
