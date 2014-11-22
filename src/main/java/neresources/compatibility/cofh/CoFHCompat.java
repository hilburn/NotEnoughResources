package neresources.compatibility.cofh;

import cofh.api.world.IFeatureGenerator;
import cofh.core.world.WorldHandler;
import cofh.lib.util.WeightedRandomBlock;
import cofh.lib.world.*;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
    private static Class featureGenLargeVein;
    private static Class featureGenTopBlock;
    private static Class featureGenUnderFluid;

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
                featureGenLargeVein = FeatureGenLargeVein.class;
                featureGenTopBlock = FeatureGenTopBlock.class;
                featureGenUnderFluid = FeatureGenUnderfluid.class;
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
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(featureGenUniform, "worldGen", feature);
                CoFHWorldGen oreGen = new CoFHWorldGen();
                if (worldGen instanceof WorldGenMinableCluster) oreGen = new CoFHWorldGen((WorldGenMinableCluster) worldGen);
                else if (worldGen instanceof WorldGenSparseMinableCluster) oreGen = new CoFHWorldGen((WorldGenSparseMinableCluster) worldGen);
                else if (worldGen instanceof WorldGenMinableLargeVein) oreGen = new CoFHWorldGen((WorldGenMinableLargeVein) worldGen);


                if (oreGen.ores!=null)
                    registerOreEntries(oreGen.ores,getChancesForUniform(minY,maxY,oreGen.veinSize,count));
            } else if (feature.getClass() == featureGenNormal)
            {
                int maxVar = ReflectionHelper.getInt(featureGenNormal, "maxVar", feature);
                int meanY = ReflectionHelper.getInt(featureGenNormal, "meanY", feature);
                int count = ReflectionHelper.getInt(featureGenNormal, "count", feature);
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(featureGenNormal, "worldGen", feature);
                CoFHWorldGen oreGen = new CoFHWorldGen();
                if (worldGen instanceof WorldGenMinableCluster)
                {
                    oreGen = new CoFHWorldGen((WorldGenMinableCluster) worldGen);
                }
                if (oreGen.ores!=null)
                    registerOreEntries(oreGen.ores,getChancesForNormal(meanY, maxVar, oreGen.veinSize, count));
            } else if (feature.getClass() == featureGenSurface)
            {

            } else if (feature.getClass() == featureGenLargeVein)
            {

            } else if (feature.getClass() == featureGenTopBlock)
            {

            } else if (feature.getClass() == featureGenUnderFluid)
            {
                boolean water = ReflectionHelper.getBoolean(featureGenUnderFluid, "water", feature);
                if (!water) continue; //TODO: Not sure how to handle non water stuff
                int count = ReflectionHelper.getInt(featureGenUnderFluid, "count", feature);
                WorldGenerator worldGen = (WorldGenerator) ReflectionHelper.getObject(featureGenUnderFluid, "worldGen", feature);
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
        return getChancesForUniform(meanY - maxVar, meanY + maxVar, veinSize, numVeins);
    }

    private void registerOreEntries(List<WeightedRandomBlock> ores, double[] baseChance)
    {
        double totalWeight = 0;
        for (WeightedRandomBlock ore:ores)
            totalWeight+=ore.itemWeight;
        for (WeightedRandomBlock ore:ores)
        {
            if(ore.block == Blocks.gravel||ore.block == Blocks.dirt) return;
            registerOre(new OreEntry(new ItemStack(ore.block,1,ore.metadata),new DistributionCustom(DistributionHelpers.multiplyArray(baseChance,ore.itemWeight/totalWeight))));
        }
    }


    private class CoFHWorldGen
    {
        int veinSize;
        ArrayList<WeightedRandomBlock> ores;
        WeightedRandomBlock[] genBlock;

        public CoFHWorldGen() {
        }

        public CoFHWorldGen(WorldGenMinableCluster worldGen)
        {
            ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenMinableCluster.class, "cluster", worldGen);
            veinSize = ReflectionHelper.getInt(WorldGenMinableCluster.class, "genClusterSize", worldGen);
            genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenMinableCluster.class, "genBlock", worldGen);
        }

        public CoFHWorldGen(WorldGenMinableLargeVein worldGen)
        {
            ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenMinableLargeVein.class, "cluster", worldGen);
            veinSize = ReflectionHelper.getInt(WorldGenMinableLargeVein.class, "genVeinSize", worldGen);
            genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenMinableLargeVein.class, "genBlock", worldGen);
        }

        public CoFHWorldGen(WorldGenSparseMinableCluster worldGen)
        {
            ores = (ArrayList<WeightedRandomBlock>) ReflectionHelper.getObject(WorldGenSparseMinableCluster.class, "cluster", worldGen);
            veinSize = ReflectionHelper.getInt(WorldGenSparseMinableCluster.class, "genClusterSize", worldGen);
            genBlock = (WeightedRandomBlock[]) ReflectionHelper.getObject(WorldGenSparseMinableCluster.class, "genBlock", worldGen);
        }

        public CoFHWorldGen(WorldGenGeode worldGen)
        {

        }

        public CoFHWorldGen(WorldGenDecoration worldGen)
        {

        }
    }
}
