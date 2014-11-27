package neresources.compatibility.tinkersconstruct;

import neresources.api.NEResourcesAPI;
import neresources.api.distributions.DistributionCustom;
import neresources.api.distributions.DistributionSquare;
import neresources.api.distributions.DistributionTriangular;
import neresources.api.utils.DistributionHelpers;
import neresources.compatibility.CompatBase;
import neresources.registry.AddOreDrop;
import neresources.registry.OreEntry;
import neresources.registry.OreRegistry;
import neresources.utils.MapKeys;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import tconstruct.util.config.PHConstruct;
import tconstruct.world.TinkerWorld;

public class TiConCompat extends CompatBase{
    private Block slagBlock;
    private Block oreBerry;
    private Block oreBerry2;
    ItemStack[] bushes;
    ItemStack[] gravel = new ItemStack[6];

    @Override
    protected void init() {
        slagBlock = TinkerWorld.oreSlag;
        oreBerry = TinkerWorld.oreBerry;
        oreBerry2 = TinkerWorld.oreBerrySecond;
        bushes = new ItemStack[]{new ItemStack(TinkerWorld.oreBerry,1,12),new ItemStack(TinkerWorld.oreBerry,1,13),new ItemStack(TinkerWorld.oreBerry,1,14),new ItemStack(TinkerWorld.oreBerry,1,15),new ItemStack(TinkerWorld.oreBerrySecond,1,12),new ItemStack(TinkerWorld.oreBerrySecond,1,13)};
        for (int i=0; i< bushes.length; i++)
            NEResourcesAPI.registerEntry(new AddOreDrop(bushes[i],new ItemStack(TinkerWorld.oreBerries,1,i)));
        for (int i=0;i<6;i++)
            gravel[i] = new ItemStack(TinkerWorld.oreGravel,1,i);
        if (PHConstruct.generateCopper) generateUnderground(new ItemStack(slagBlock, 1, 3), PHConstruct.copperuDensity, PHConstruct.copperuMinY, PHConstruct.copperuMaxY, 8);
        if (PHConstruct.generateTin) generateUnderground(new ItemStack(slagBlock, 1, 4), PHConstruct.tinuDensity, PHConstruct.tinuMinY, PHConstruct.tinuMaxY, 8);
        if (PHConstruct.generateAluminum) generateUnderground(new ItemStack(slagBlock,1,5),PHConstruct.aluminumuDensity,PHConstruct.aluminumuMinY,PHConstruct.aluminumuMaxY,6);
        if (PHConstruct.generateNetherOres) generateNetherOres();
        if (PHConstruct.generateIronBush) generateOreBush(bushes[0], PHConstruct.ironBushDensity, PHConstruct.ironBushDensity, getAverageSize(12), 32, 32);
        if (PHConstruct.generateGoldBush) generateOreBush(bushes[1],PHConstruct.goldBushDensity, PHConstruct.goldBushDensity, getAverageSize(6), 32, 32);
        if (PHConstruct.generateCopperBush) generateOreBush(bushes[2],PHConstruct.copperBushDensity, PHConstruct.copperBushDensity, getAverageSize(12), 32, 32);
        if (PHConstruct.generateTinBush) generateOreBush(bushes[3],PHConstruct.tinBushDensity, PHConstruct.tinBushDensity, getAverageSize(12), 32, 32);
        if (PHConstruct.generateAluminumBush) generateOreBush(bushes[4],PHConstruct.aluminumBushDensity, PHConstruct.aluminumBushDensity, getAverageSize(14), 32, 32);
        if (PHConstruct.generateEssenceBush) generateOreBush(bushes[5],PHConstruct.essenceBushRarity, PHConstruct.essenceBushRarity, getAverageSize(12), 32, 32);
        if (PHConstruct.generateIronSurface) generateSurface(gravel[0], PHConstruct.ironsRarity, 12);
        if (PHConstruct.generateGoldSurface) generateSurface(gravel[1],PHConstruct.goldsRarity,20);
        if (PHConstruct.generateCopperSurface) generateSurface(gravel[2],PHConstruct.cobaltsRarity,12);
        if (PHConstruct.generateTinSurface) generateSurface(gravel[3],PHConstruct.tinsRarity,12);
        if (PHConstruct.generateAluminumSurface) generateSurface(gravel[4],PHConstruct.aluminumsRarity,12);
        if (PHConstruct.generateCobaltSurface) generateSurface(gravel[5],PHConstruct.cobaltsRarity,30);
    }

    private float getAverageSize(int chance)
    {
        float result = 0;
        for (int i = 0; i<chance; i++)
        {
            if (i==11) result+=6.75F;
            if (i>=5) result+=3F;
            else result+=1.5F;
        }
        return result/chance;
    }

    private void generateSurface(ItemStack ore, int rarity, int veinSize)
    {
        float chanceToSpawn = 1F/rarity;
        float numOres = 0.7F*veinSize;
        double cubeLength = Math.pow(numOres,0.3333D);
        float[] distribution = DistributionHelpers.getOverworldSurfaceDistribution(round(cubeLength));
        distribution = DistributionHelpers.multiplyArray(distribution, chanceToSpawn);
        registerOre(new OreEntry(ore,new DistributionCustom(distribution)));
    }

    private int round(double val)
    {
        if (val%1>0.5) return (int)Math.ceil(val);
        return (int)Math.floor(val);
    }

    private void generateOreBush(ItemStack bush, int density, int rarity, float averageSize, int midY, int var)
    {
        float chanceToSpawn = (float)density/rarity;
        float numBushesPerChunk = averageSize*chanceToSpawn;
        float maxChance = numBushesPerChunk/(256F*(2*var+1));
        registerOre(new OreEntry(bush,new DistributionTriangular(midY,var,maxChance)));
        OreRegistry.getInstance().addOreLink(MapKeys.getKey(new ItemStack(bush.getItem(),1,bush.getItemDamage()-4)),MapKeys.getKey(bush));
    }

    private void generateUnderground(ItemStack ore, int numVeins, int minY, int maxY, int veinSize)
    {
        float chance = ((float)numVeins*veinSize)/((maxY-minY+1)*256);
        registerOre(new OreEntry(ore,new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
    }

    private void generateNetherOres()
    {
        int netherDensity = PHConstruct.netherDensity;
        ItemStack ardite = new ItemStack(slagBlock,1,1);
        ItemStack cobalt = new ItemStack(slagBlock,1,2);
        int veinSize = 3;
        int minY = 32;
        int maxY = 96;
        float chance = ((float)netherDensity*veinSize)/((maxY-minY+1)*256);
        float[] distribution = DistributionHelpers.getRoundedSquareDistribution(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 127), chance);
        chance = ((float)netherDensity*veinSize)/(128*256);
        DistributionHelpers.addDistribution(distribution,DistributionHelpers.getSquareDistribution(0,127,chance));
        registerOre(new OreEntry(ardite,new DistributionCustom(distribution)));
        registerOre(new OreEntry(cobalt,new DistributionCustom(distribution)));
    }
}