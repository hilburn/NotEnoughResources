package neresources.compatibility.thaumcraft;

import neresources.api.NEResourcesAPI;
import neresources.api.distributions.DistributionCustom;
import neresources.api.distributions.DistributionSquare;
import neresources.api.utils.DistributionHelpers;
import neresources.compatibility.CompatBase;
import neresources.registry.AddOreDrop;
import neresources.registry.OreEntry;
import net.minecraft.item.ItemStack;
import thaumcraft.common.config.Config;
import thaumcraft.common.config.ConfigBlocks;
import thaumcraft.common.config.ConfigItems;

public class ThaumcraftCompat extends CompatBase {
    @Override
    protected void init() {
        ItemStack amber = new ItemStack(ConfigBlocks.blockCustomOre,1,7);
        ItemStack amberDrop = new ItemStack(ConfigItems.itemResource,1,6);
        NEResourcesAPI.registerEntry(new AddOreDrop(amber,amberDrop));
        for (int i=0;i<6;i++)
        {
            ItemStack infusedStone = new ItemStack(ConfigBlocks.blockCustomOre,1,i+1);
            ItemStack infusedShard = new ItemStack(ConfigItems.itemShard, 2, i);
            NEResourcesAPI.registerEntry(new AddOreDrop(infusedStone,infusedShard));
        }
        if (Config.genCinnibar) genCinnibar();
        if (Config.genAmber) genAmber();
        if (Config.genInfusedStone) genInfused();
    }

    private void genInfused() {
        int minY=5;
        int maxY=59;
        int veinSize = 6;
        float numVeins = 8F;
        float chance = numVeins/((maxY-minY+1)*265);
        for (int i=0;i<6;i++) {
            ItemStack infusedStone = new ItemStack(ConfigBlocks.blockCustomOre, 1, i + 1);
            registerOre(new OreEntry(infusedStone,new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
        }
    }

    private void genAmber() {
        int minY = 0;
        int maxY = 64;
        float maxYRange = 25;
        float numVeins = 20F;
        float chance = numVeins/((maxY-maxYRange/2-minY+1)*256);
        float[] distribution = DistributionHelpers.getSquareDistribution(minY, maxY-(int)maxYRange, chance);
        DistributionHelpers.addDistribution(distribution,DistributionHelpers.getRampDistribution(maxY,(int)(maxY-maxYRange),chance),maxY-(int)maxYRange);
        ItemStack amber = new ItemStack(ConfigBlocks.blockCustomOre,1,7);
        registerOre(new OreEntry(amber,new DistributionCustom(distribution)));
    }

    private void genCinnibar() {
        int minY=0;
        int maxY = 64/5;
        float numVeins = 18F;
        float chance = numVeins/(maxY*256);
        ItemStack ore = new ItemStack(ConfigBlocks.blockCustomOre,1,0);
        registerOre(new OreEntry(ore,new DistributionSquare(minY, maxY, chance)));
    }
}
