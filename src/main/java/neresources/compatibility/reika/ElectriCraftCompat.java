package neresources.compatibility.reika;

import Reika.ElectriCraft.Registry.ElectriOres;
import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.RegisterOreMessage;
import neresources.compatibility.CompatBase;
import net.minecraft.item.ItemStack;

public class ElectriCraftCompat extends CompatBase
{

    @Override
    protected void init()
    {
        for (ElectriOres electriOre : ElectriOres.oreList)
        {
            if (!electriOre.isOreEnabled()) continue;
            ItemStack ore = new ItemStack(electriOre.getBlock(), 1, electriOre.getBlockMetadata());
            int minY = electriOre.minY;
            int maxY = electriOre.maxY;
            int numVeins = electriOre.perChunk;
            int veinSize = electriOre.veinSize;
            registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
        }
    }
}
