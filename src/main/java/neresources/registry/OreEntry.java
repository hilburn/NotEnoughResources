package neresources.registry;

import neresources.api.distributions.DistributionBase;
import neresources.utils.ColorHelper;
import net.minecraft.item.ItemStack;

public class OreEntry
{
    private ItemStack ore;
    private boolean needSilkTouch;
    private DistributionBase distribution;
    private int colour;

    public OreEntry(ItemStack ore, DistributionBase distribution)
    {
        this(ore, distribution, ColorHelper.BLACK, false);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, boolean needSilkTouch)
    {
        this(ore, distribution, ColorHelper.BLACK, needSilkTouch);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, int colour)
    {
        this(ore, distribution, colour, false);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, int colour, boolean needSilkTouch)
    {
        this.ore = ore;
        this.needSilkTouch = needSilkTouch;
        this.distribution = distribution;
        this.colour = colour;
    }

    public ItemStack getOre()
    {
        return this.ore;
    }

    public DistributionBase getDistribution() {
        return distribution;
    }

    public boolean needSilkTouch()
    {
        return this.needSilkTouch;
    }

    public int getColour() {
        return colour;
    }
}
