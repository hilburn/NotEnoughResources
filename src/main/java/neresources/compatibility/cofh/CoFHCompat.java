package neresources.compatibility.cofh;

import cofh.api.world.IFeatureGenerator;
import cofh.core.world.WorldHandler;
import cofh.lib.util.WeightedRandomBlock;
import cofh.lib.world.WorldGenMinableCluster;
import cofh.lib.world.feature.FeatureGenNormal;
import cofh.lib.world.feature.FeatureGenSurface;
import cofh.lib.world.feature.FeatureGenUniform;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import neresources.compatibility.CompatBase;
import neresources.utils.ModList;
import neresources.utils.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.List;

public class CoFHCompat extends CompatBase
{

    private static List<IFeatureGenerator> features;
    public static boolean cofhReplace = false;

    public static CoFHCompat instance = null;

    public static CoFHCompat newInstance()
    {
        if (instance != null)
            return instance;
        else
            return instance = new CoFHCompat();
    }

    public CoFHCompat()
    {
        super(ModList.cofhcore.toString());
    }

    @Override
    public void init()
    {
        cofhReplace = WorldHandler.genReplaceVanilla;
        features = (ArrayList<IFeatureGenerator>) ReflectionHelper.getObject(WorldHandler.class, "features", null);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) registerOres();
    }

    private void registerOres()
    {
        for (IFeatureGenerator feature : features)
        {
            if (feature instanceof FeatureGenUniform)
            {
                FeatureGenUniform val = (FeatureGenUniform) feature;
                int maxY = ReflectionHelper.getInt(FeatureGenUniform.class, "maxY", val);
                int minY = ReflectionHelper.getInt(FeatureGenUniform.class, "minY", val);
                int count = ReflectionHelper.getInt(FeatureGenUniform.class, "count", val);
                int veinSize = 0;
                ArrayList<WeightedRandomBlock> ores = null;
                WeightedRandomBlock[] genBlock = null;
                Block ore;
                int metadata = 0;
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(val.getClass(), "worldGen", val);
                if (worldGen instanceof WorldGenMinableCluster)
                {
                    WorldGenMinableCluster cluster = ((WorldGenMinableCluster) worldGen);
                    ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenMinableCluster.class, "cluster", cluster);
                    veinSize = ReflectionHelper.getInt(WorldGenMinableCluster.class, "genClusterSize", cluster);
                    genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenMinableCluster.class, "genBlock", cluster);
                }
                System.out.println(ores.get(0).block.getUnlocalizedName() + ":" + ores.get(0).metadata + " - " + minY + " to " + maxY + ": Veins = " + count + ", Vein Size = " + veinSize);
            } else if (feature instanceof FeatureGenNormal)
            {

            } else if (feature instanceof FeatureGenSurface)
            {

            }
        }
    }
}
