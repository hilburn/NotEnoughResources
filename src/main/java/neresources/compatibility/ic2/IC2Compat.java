package neresources.compatibility.ic2;

import cpw.mods.fml.common.Optional;
import ic2.core.Ic2Items;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import neresources.api.distributions.DistributionCustom;
import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.RegisterOreMessage;
import neresources.api.utils.DistributionHelpers;
import neresources.compatibility.CompatBase;
import neresources.utils.ModList;
import net.minecraft.item.ItemStack;

public class IC2Compat extends CompatBase
{
    @Override
    protected void init()
    {
        registerIC2Ores();
    }

    @Optional.Method(modid = ModList.Names.IC2)
    private void registerIC2Ores()
    {
        float density = ConfigUtil.getFloat(MainConfig.get(), "worldgen/oreDensityFactor");
        int baseScale = Math.round(64 * density);
        int numVeins, minY, maxY, veinSize;
        if(ConfigUtil.getBool(MainConfig.get(), "worldgen/copperOre") && Ic2Items.copperOre != null)
        {
            numVeins = 15 * baseScale / 64;
            minY = 10;
            int rand1 = 20;
            int rand2 = 40;
            veinSize = 10;
            float chance = (numVeins * veinSize) / ((rand1+rand2-1) * 256F);
            registerOre(new RegisterOreMessage(Ic2Items.copperOre, new DistributionCustom(DistributionHelpers.getTriangularDistribution(minY - veinSize / 2, rand1 + veinSize / 2, rand2 + veinSize / 2, chance))));
        }

        if(ConfigUtil.getBool(MainConfig.get(), "worldgen/tinOre") && Ic2Items.tinOre != null)
        {
            numVeins = 25 * baseScale / 64;
            minY = 0;
            maxY = 39;
            veinSize = 6;
            float chance = (numVeins * veinSize) / ((maxY - minY + 1) * 256F);
            registerOre(new RegisterOreMessage(Ic2Items.tinOre, new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
        }

        if(ConfigUtil.getBool(MainConfig.get(), "worldgen/uraniumOre") && Ic2Items.uraniumOre != null)
        {
            numVeins = 20 * baseScale / 64;
            minY = 0;
            maxY = 63;
            veinSize = 3;
            float chance = (numVeins * veinSize) / ((maxY - minY + 1) * 256F);
            registerOre(new RegisterOreMessage(Ic2Items.uraniumOre, new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
        }

        if(ConfigUtil.getBool(MainConfig.get(), "worldgen/leadOre") && Ic2Items.leadOre != null)
        {
            numVeins = 8 * baseScale / 64;
            minY = 0;
            maxY = 63;
            veinSize = 4;
            float chance = (numVeins * veinSize) / ((maxY - minY + 1) * 256F);
            registerOre(new RegisterOreMessage(Ic2Items.leadOre, new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
        }
    }
}
