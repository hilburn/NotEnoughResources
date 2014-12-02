package neresources.compatibility.forestry;

import forestry.core.config.Config;
import forestry.core.config.ForestryBlock;
import forestry.core.config.ForestryItem;
import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.ModifyOreMessage;
import neresources.api.utils.Priority;
import neresources.compatibility.CompatBase;
import neresources.registry.MessageRegistry;
import neresources.entries.OreEntry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ForestryCompat extends CompatBase
{
    Block oreBlock;

    @Override
    protected void init()
    {
        registerOres();
    }

    private void registerOres()
    {
        oreBlock = ForestryBlock.resources.block();
        ItemStack apatite = ForestryItem.apatite.getItemStack();
        MessageRegistry.addMessage(new ModifyOreMessage(new ItemStack(oreBlock, 1, 0), Priority.FIRST, apatite));
        if (Config.generateApatiteOre) genApatite();
        if (Config.generateCopperOre) genCopper();
        if (Config.generateTinOre) genTin();
        if (Config.generateBogEarth) ; //TODO: Bog Earth
        if (Config.generateBeehives) ; //TODO: Beehives
    }

    private void genTin()
    {
        ItemStack ore = new ItemStack(oreBlock, 1, 2);
        float numVeins = 18F;
        int minY = 16;
        int maxY = 92;
        int veinSize = 6;
        float chance = (numVeins * veinSize) / ((maxY - minY + 1) * 256);
        registerOre(new OreEntry(ore, new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
    }

    private void genCopper()
    {
        ItemStack ore = new ItemStack(oreBlock, 1, 1);
        float numVeins = 20F;
        int minY = 32;
        int maxY = 108;
        int veinSize = 6;
        float chance = (numVeins * veinSize) / ((maxY - minY + 1) * 256);
        registerOre(new OreEntry(ore, new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
    }

    private void genApatite()
    {
        ItemStack ore = new ItemStack(oreBlock, 1, 0);
        float numVeins = 0.8F;
        int minY = 56;
        int maxY = 240;
        int veinSize = 36;
        float chance = (numVeins * veinSize) / ((maxY - minY + 1) * 256);
        registerOre(new OreEntry(ore, new DistributionSquare(Math.max(0, minY - veinSize / 2), minY, maxY, Math.min(maxY + veinSize / 2, 255), chance)));
    }
}
