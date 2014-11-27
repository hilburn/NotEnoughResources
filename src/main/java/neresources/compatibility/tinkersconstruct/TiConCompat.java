package neresources.compatibility.tinkersconstruct;

import neresources.api.distributions.DistributionCustom;
import neresources.api.distributions.DistributionSquare;
import neresources.api.utils.DistributionHelpers;
import neresources.compatibility.CompatBase;
import neresources.registry.OreEntry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import tconstruct.util.config.PHConstruct;
import tconstruct.world.TinkerWorld;

public class TiConCompat extends CompatBase{
    private Block slagBlock;

    @Override
    protected void init() {
        slagBlock = TinkerWorld.oreSlag;
        if (PHConstruct.generateCopper) generateCopper();
        if (PHConstruct.generateTin) generateTin();
        if (PHConstruct.generateAluminum) generateAluminium();
        if (PHConstruct.generateNetherOres) generateNetherOres();
    }

    private void generateAluminium()
    {
        int numVeins = PHConstruct.aluminumuDensity;
        int minY = PHConstruct.aluminumuMinY;
        int maxY = PHConstruct.aluminumuMaxY;
        int veinSize = 6;
        ItemStack ore = new ItemStack(slagBlock,1,5);
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

    private void generateTin()
    {
        int numVeins = PHConstruct.tinuDensity;
        int minY = PHConstruct.tinuMinY;
        int maxY = PHConstruct.tinuMaxY;
        int veinSize = 8;
        ItemStack ore = new ItemStack(slagBlock,1,4);
        float chance = ((float)numVeins*veinSize)/((maxY-minY+1)*256);
        registerOre(new OreEntry(ore,new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
    }

    private void generateCopper()
    {
        int numVeins = PHConstruct.copperuDensity;
        int minY = PHConstruct.copperuMinY;
        int maxY = PHConstruct.copperuMaxY;
        int veinSize = 8;
        ItemStack ore = new ItemStack(slagBlock,1,3);
        float chance = ((float)numVeins*veinSize)/((maxY-minY+1)*256);
        registerOre(new OreEntry(ore,new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
    }
}
