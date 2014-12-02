package neresources.compatibility.metallurgy;

import com.teammetallurgy.metallurgy.world.WorldGenMetals;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import neresources.api.distributions.DistributionSquare;
import neresources.compatibility.CompatBase;
import neresources.entries.OreEntry;
import neresources.utils.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class MetallurgyCompat extends CompatBase
{

    @Override
    public void init()
    {
        Set<IWorldGenerator> generators = (Set<IWorldGenerator>) ReflectionHelper.getObject(GameRegistry.class, "worldGenerators", null);
        for (IWorldGenerator generator : generators)
        {
            if (generator instanceof WorldGenMetals)
            {
                WorldGenMetals genMetals = (WorldGenMetals) generator;
                Block genBlock = (Block) ReflectionHelper.getObject(WorldGenMetals.class, "genBlock", genMetals);
                int genMetaId = ReflectionHelper.getInt(WorldGenMetals.class, "genMetaId", genMetals);
                ItemStack ore = new ItemStack(genBlock, 1, genMetaId);
                int[] generation = (int[]) ReflectionHelper.getObject(WorldGenMetals.class, "generation", genMetals);
                String dimensions = ReflectionHelper.getString(WorldGenMetals.class, "dimensions", genMetals);
                float chance = (float) (generation[0] * generation[1]) / ((generation[3] - generation[2]) * 256) * (float) generation[4] / 100;
                OreEntry entry = new OreEntry(ore, new DistributionSquare(Math.max(generation[2] - generation[1] / 2, 0), generation[2], generation[3], Math.min(generation[3] + generation[1] / 2, 255), chance));
                registerOre(entry);
            }
        }
    }
}
