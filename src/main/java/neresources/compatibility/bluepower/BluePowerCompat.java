package neresources.compatibility.bluepower;

import com.bluepowermod.init.BPBlocks;
import com.bluepowermod.init.Config;

import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.RegisterOreMessage;
import neresources.compatibility.CompatBase;
import net.minecraft.item.ItemStack;

public class BluePowerCompat extends CompatBase
{

    @Override
    protected void init()
    {
        registerOres();
    }

    private void registerOres()
    {
        if (Config.generateAmethyst)
        {
            int veinCount = Config.veinCountAmethyst;
            int veinSize = Config.veinSizeAmethyst;
            int minY = Config.minAmethystY;
            int maxY = Config.maxAmethystY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.amethyst_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
        if (Config.generateRuby)
        {
            int veinCount = Config.veinCountRuby;
            int veinSize = Config.veinSizeRuby;
            int minY = Config.minRubyY;
            int maxY = Config.maxRubyY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.ruby_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
        if (Config.generateSapphire)
        {
            int veinCount = Config.veinCountSapphire;
            int veinSize = Config.veinSizeSapphire;
            int minY = Config.minSapphireY;
            int maxY = Config.maxSapphireY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.sapphire_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
        if (Config.generateSilver)
        {
            int veinCount = Config.veinCountSilver;
            int veinSize = Config.veinSizeSilver;
            int minY = Config.minSilverY;
            int maxY = Config.maxSilverY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.silver_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
        if (Config.generateTeslatite)
        {
            int veinCount = Config.veinCountTeslatite;
            int veinSize = Config.veinSizeTeslatite;
            int minY = Config.minTeslatiteY;
            int maxY = Config.maxTeslatiteY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.teslatite_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
        if (Config.generateZinc)
        {
            int veinCount = Config.veinCountZinc;
            int veinSize = Config.veinSizeZinc;
            int minY = Config.minZincY;
            int maxY = Config.maxZincY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.zinc_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
        if (Config.generateCopper)
        {
            int veinCount = Config.veinCountCopper;
            int veinSize = Config.veinSizeCopper;
            int minY = Config.minCopperY;
            int maxY = Config.maxCopperY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.copper_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
        if (Config.generateTungsten)
        {
            int veinCount = Config.veinCountTungsten;
            int veinSize = Config.veinSizeTungsten;
            int minY = Config.minTungstenY;
            int maxY = Config.maxTungstenY;
            registerOre(new RegisterOreMessage(new ItemStack(BPBlocks.tungsten_ore), new DistributionSquare(veinCount, veinSize, minY, maxY)));
        }
    }
}
