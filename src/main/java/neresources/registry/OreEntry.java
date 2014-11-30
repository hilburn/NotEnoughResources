package neresources.registry;

import neresources.api.distributions.DistributionBase;
import neresources.api.messages.ModifyMessage;
import neresources.api.messages.ModifyOreMessage;
import neresources.api.messages.RegisterOreMessage;
import neresources.api.utils.ColorHelper;
import neresources.api.utils.Priority;
import neresources.utils.SilkTouchHelper;
import net.minecraft.item.ItemStack;

public class OreEntry
{
    private ItemStack ore;
    private boolean needSilkTouch;
    private DistributionBase distribution;
    private int colour;

    public OreEntry(ItemStack ore, DistributionBase distribution)
    {
        this(ore, distribution, ColorHelper.BLACK, SilkTouchHelper.isOreBlock(ore));
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, boolean needSilkTouch)
    {
        this(ore, distribution, ColorHelper.BLACK, needSilkTouch);
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, int colour)
    {
        this(ore, distribution, colour, SilkTouchHelper.isOreBlock(ore));
    }

    public OreEntry(ItemStack ore, DistributionBase distribution, int colour, boolean needSilkTouch)
    {
        this.ore = ore;
        this.needSilkTouch = needSilkTouch;
        this.distribution = distribution;
        this.colour = colour;
    }

    public OreEntry(RegisterOreMessage message)
    {
        this.ore = message.getOre();
        this.distribution = message.getDistribution();
        this.needSilkTouch = message.needSilkTouch();
        this.colour = message.getColour();
        if (message.getDrops().length>0)
            MessageRegistry.addMessage(new ModifyOreMessage(this.ore,true,Priority.FIRST,message.getDrops()));
    }

    public ItemStack getOre()
    {
        return this.ore;
    }

    public DistributionBase getDistribution()
    {
        return distribution;
    }

    public boolean needSilkTouch()
    {
        return this.needSilkTouch;
    }

    public int getColour()
    {
        return colour;
    }
}
