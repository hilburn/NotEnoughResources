package neresources.compatibility.reika;

import Reika.ReactorCraft.Registry.ReactorOres;
import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.ModifyOreMessage;
import neresources.api.utils.Priority;
import neresources.compatibility.CompatBase;
import neresources.registry.MessageRegistry;
import neresources.registry.OreEntry;
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
            float chance = (float) (numVeins * veinSize) / ((maxY - minY + 1) * 256);
            if (!generates) continue;
            registerOre(new OreEntry(ore, new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
        }
    }
}
