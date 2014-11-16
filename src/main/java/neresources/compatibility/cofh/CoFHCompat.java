package neresources.compatibility.cofh;

import cofh.api.world.IFeatureGenerator;
import cofh.core.world.WorldHandler;
import cofh.lib.util.WeightedRandomBlock;
import cofh.lib.world.WorldGenMinableCluster;
import cofh.lib.world.feature.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import neresources.api.distributions.DistributionCustom;
import neresources.api.utils.DistributionHelpers;
import neresources.compatibility.CompatBase;
import neresources.registry.OreEntry;
import neresources.utils.LoaderHelper;
import neresources.utils.ModList;
import neresources.utils.ReflectionHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.ArrayList;
import java.util.List;

public class CoFHCompat extends CompatBase
{

    private static List<IFeatureGenerator> features;
    public static boolean cofhReplace = false;

    public static CoFHCompat instance = null;

    private static Class featureGenUniform;
    private static Class featureGenNormal;
    private static Class featureGenSurface;

    public static CoFHCompat newInstance()
    {
        if (instance != null)
            return instance;
        else
        {
            if(LoaderHelper.isModVersion(ModList.cofhcore.toString(), "1.7.10R3.0.0B6"))
            {
                featureGenUniform = FeatureOreGenUniform.class;
                featureGenNormal = FeatureOreGenNormal.class;
                featureGenSurface = FeatureOreGenSurface.class;
            }
            else
            {
                featureGenUniform = FeatureGenUniform.class;
                featureGenNormal = FeatureGenNormal.class;
                featureGenSurface = FeatureGenSurface.class;
            }
            return instance = new CoFHCompat();
        }
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
            if (feature.getClass() == featureGenUniform)
            {
                int maxY = ReflectionHelper.getInt(featureGenUniform, "maxY", feature);
                int minY = ReflectionHelper.getInt(featureGenUniform, "minY", feature);
                int count = ReflectionHelper.getInt(featureGenUniform, "count", feature);
                int veinSize = 0;
                ArrayList<WeightedRandomBlock> ores = null;
                WeightedRandomBlock[] genBlock = null;
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(feature.getClass(), "worldGen", feature);
                if (worldGen instanceof WorldGenMinableCluster)
                {
                    WorldGenMinableCluster cluster = ((WorldGenMinableCluster) worldGen);
                    ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenMinableCluster.class, "cluster", cluster);
                    veinSize = ReflectionHelper.getInt(WorldGenMinableCluster.class, "genClusterSize", cluster);
                    genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenMinableCluster.class, "genBlock", cluster);
                }
                if (ores!=null)
                    registerOreEntries(ores,getChancesForUniform(minY,maxY,veinSize,count));
            } else if (feature.getClass() == featureGenNormal)
            {
                int maxVar = ReflectionHelper.getInt(featureGenNormal, "maxVar", feature);
                int meanY = ReflectionHelper.getInt(featureGenNormal, "meanY", feature);
                int count = ReflectionHelper.getInt(featureGenNormal, "count", feature);
                ArrayList<WeightedRandomBlock> ores = null;
                int veinSize = 0;
                WeightedRandomBlock[] genBlock = null;
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(feature.getClass(), "worldGen", feature);
                if (worldGen instanceof WorldGenMinableCluster)
                {
                    WorldGenMinableCluster cluster = ((WorldGenMinableCluster) worldGen);
                    ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenMinableCluster.class, "cluster", cluster);
                    veinSize = ReflectionHelper.getInt(WorldGenMinableCluster.class, "genClusterSize", cluster);
                    genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenMinableCluster.class, "genBlock", cluster);
                }
                if (ores != null)
                    registerOreEntries(ores, getChancesForNormal(meanY, maxVar, veinSize, count));
            } else if (feature.getClass() == featureGenSurface)
            {

            }
        }
    }

    private double[] getChancesForUniform(int minY, int maxY, int veinSize, int numVeins)
    {
        int safeMinY = Math.max(minY, 0);
        int safeMaxY = Math.min(maxY, 255);
        double chance = (double)numVeins/(safeMaxY-safeMinY)*veinSize/256D;
        return DistributionHelpers.getRoundedSquareDistribution(Math.max(0,minY-veinSize/2),safeMinY,safeMaxY,Math.min(maxY+veinSize/2,255), chance);
    }

    private double[] getChancesForNormal(int meanY, int maxVar, int veinSize, int numVeins)
    {
        return getChancesForUniform(meanY-maxVar, meanY+maxVar, veinSize, numVeins);
    }

    private void registerOreEntries(List<WeightedRandomBlock> ores, double[] baseChance)
    {
        double totalWeight = 0;
        for (WeightedRandomBlock ore:ores)
            totalWeight+=ore.itemWeight;
        for (WeightedRandomBlock ore:ores)
        {
            registerOre(new OreEntry(new ItemStack(ore.block,1,ore.metadata),new DistributionCustom(DistributionHelpers.divideArray(baseChance,ore.itemWeight/totalWeight))));
        }
    }
}
