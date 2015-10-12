package neresources.compatibility.reika;

import Reika.ReactorCraft.Registry.ReactorOres;
import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.ModifyOreMessage;
import neresources.api.messages.RegisterOreMessage;
import neresources.api.utils.Priority;
import neresources.compatibility.CompatBase;
import neresources.registry.MessageRegistry;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ReactorCraftCompat extends CompatBase
{
    @Override
    protected void init()
    {
        for (ReactorOres reactorOre : ReactorOres.oreList)
        {
            boolean generates = reactorOre.shouldGen || !reactorOre.hasEquivalents();
            int metadata = reactorOre.getBlockMetadata();
            ItemStack ore = new ItemStack(reactorOre.getBlock(), 1, metadata);
            List<ItemStack> drops = reactorOre.getOreDrop(metadata);
            for (ItemStack drop : drops)
            {
                if (!drop.isItemEqual(ore))
                    MessageRegistry.addMessage(new ModifyOreMessage(ore, Priority.FIRST, drop));
            }
            int minY = reactorOre.minY;
            int maxY = reactorOre.maxY;
            int numVeins = reactorOre.perChunk;
            int veinSize = reactorOre.veinSize;
            if (!generates) continue;
            registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
        }
    }
}
