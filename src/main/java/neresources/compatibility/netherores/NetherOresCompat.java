package neresources.compatibility.netherores;

import neresources.api.distributions.DistributionSquare;
import neresources.compatibility.CompatBase;
import neresources.registry.OreEntry;
import neresources.utils.ModList;
import net.minecraft.item.ItemStack;
import powercrystals.netherores.NetherOresCore;
import powercrystals.netherores.ores.Ores;

public class NetherOresCompat extends CompatBase
{

    public static NetherOresCompat instance = null;

    public static NetherOresCompat newInstance()
    {
        if (instance != null)
            return instance;
        else
            return instance = new NetherOresCompat();
    }


    public NetherOresCompat()
    {
        super(ModList.netherores.toString());
    }

    @Override
    public void init()
    {
        for (Ores netherOre : Ores.values())
        {
            if (netherOre.getForced() || ((netherOre.isRegisteredSmelting() || netherOre.isRegisteredMacerator() ||
                    NetherOresCore.forceOreSpawn.getBoolean(false)) && !netherOre.getDisabled()))
            {
                ItemStack ore = netherOre.getItemStack(1);
                int minY = netherOre.getMinY();
                int maxY = netherOre.getMaxY();
                int veinSize = netherOre.getBlocksPerGroup();
                int numVeins = netherOre.getGroupsPerChunk();
                float chance = (float) (numVeins * veinSize) / ((maxY - minY) * 256);
                OreEntry entry = new OreEntry(ore, new DistributionSquare(Math.max(minY - veinSize / 2, 0), minY, maxY, Math.min(maxY + veinSize / 2, 127), chance));
                registerOre(entry);
            }
        }
    }
}
