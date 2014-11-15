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
import neresources.api.distributions.DistributionCustom;
import neresources.api.entry.IOreEntry;
import neresources.api.utils.DistributionHelpers;
import neresources.compatibility.CompatBase;
import neresources.registry.OreEntry;
import neresources.utils.ModList;
import neresources.utils.ReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
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
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(val.getClass(), "worldGen", val);
                if (worldGen instanceof WorldGenMinableCluster)
                {
                    WorldGenMinableCluster cluster = ((WorldGenMinableCluster) worldGen);
                    ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenMinableCluster.class, "cluster", cluster);
                    veinSize = ReflectionHelper.getInt(WorldGenMinableCluster.class, "genClusterSize", cluster);
                    genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenMinableCluster.class, "genBlock", cluster);
                }
                if (ores!=null)
                    registerOreEntries(ores,getChancesForUniform(minY,maxY,veinSize,count));
            } else if (feature instanceof FeatureGenNormal)
            {
                FeatureGenNormal val = (FeatureGenNormal) feature;
                int maxVar = ReflectionHelper.getInt(FeatureGenNormal.class, "maxVar", val);
                int meanY = ReflectionHelper.getInt(FeatureGenNormal.class, "meanY", val);
                int count = ReflectionHelper.getInt(FeatureGenNormal.class, "count", val);
                ArrayList<WeightedRandomBlock> ores = null;
                int veinSize = 0;
                WeightedRandomBlock[] genBlock = null;
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(val.getClass(), "worldGen", val);
                if (worldGen instanceof WorldGenMinableCluster)
                {
                    WorldGenMinableCluster cluster = ((WorldGenMinableCluster) worldGen);
                    ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenMinableCluster.class, "cluster", cluster);
                    veinSize = ReflectionHelper.getInt(WorldGenMinableCluster.class, "genClusterSize", cluster);
                    genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenMinableCluster.class, "genBlock", cluster);
                }
                if (ores != null)
                    registerOreEntries(ores, getChancesForNormal(meanY, maxVar, veinSize, count));
                System.out.println(ores.get(0).block.getUnlocalizedName() + ":" + ores.get(0).metadata + " - " + meanY + " to " + maxVar + ": Veins = " + count + ", Vein Size = " + veinSize);
            } else if (feature instanceof FeatureGenSurface)
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
