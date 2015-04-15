package neresources.utils;

import neresources.api.utils.restrictions.BlockRestriction;
import neresources.api.utils.restrictions.DimensionRegistry;
// import neresources.compatibility.mystcraft.MystCompat;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEventHelper
{
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (!DimensionRegistry.contains(event.world.provider.getDimensionId()))
        {
            DimensionRegistry.registerDimension(BlockRestriction.STONE, event.world.provider.getDimensionId(), ModList.mystcraft.isLoaded() /*&& MystCompat.isMystDim(event.world.provider.getDimensionId())*/);
        }
    }
}
