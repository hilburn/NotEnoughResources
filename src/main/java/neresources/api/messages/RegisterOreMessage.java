package neresources.api.messages;

import neresources.api.distributions.DistributionBase;
import neresources.api.messages.utils.MessageHelper;
import neresources.api.messages.utils.MessageKeys;
import neresources.api.utils.ColorHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RegisterOreMessage extends Message
{
    private ItemStack ore;
    private ItemStack[] drops;
    private boolean needSilkTouch;
    private DistributionBase distribution;
    private int colour;

    public RegisterOreMessage(ItemStack ore, DistributionBase distribution, ItemStack... drops)
    {
        this(ore, distribution, ColorHelper.BLACK, false, drops);
    }

    public RegisterOreMessage(ItemStack ore, DistributionBase distribution, boolean needSilkTouch, ItemStack... drops)
    {
        this(ore, distribution, ColorHelper.BLACK, needSilkTouch, drops);
    }

    public RegisterOreMessage(ItemStack ore, DistributionBase distribution, int colour, ItemStack... drops)
    {
        this(ore, distribution, colour, false, drops);
    }

    public RegisterOreMessage(ItemStack ore, DistributionBase distribution, int colour, boolean needSilkTouch, ItemStack... drops)
    {
        this.ore = ore;
        this.drops = drops;
        this.needSilkTouch = needSilkTouch;
        this.distribution = distribution;
        this.colour = colour;
    }

    public RegisterOreMessage(NBTTagCompound tagCompound)
    {
        this.ore = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag(MessageKeys.ore));
        this.drops = MessageHelper.getItemStacks(tagCompound, MessageKeys.addDrops);
        this.needSilkTouch = tagCompound.getBoolean(MessageKeys.silkTouch);
        this.distribution = MessageHelper.getDistribution(tagCompound);
        this.colour = tagCompound.hasKey(MessageKeys.colour)?tagCompound.getInteger(MessageKeys.colour):ColorHelper.BLACK;
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

    public ItemStack[] getDrops()
    {
        return drops;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setTag(MessageKeys.ore, ore.writeToNBT(new NBTTagCompound()));
        tagCompound.setTag(MessageKeys.addDrops, MessageHelper.getItemStackList(drops));
        distribution.writeToNBT(tagCompound);
        tagCompound.setBoolean(MessageKeys.silkTouch, needSilkTouch);
        tagCompound.setInteger(MessageKeys.colour, colour);
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return ore!=null && distribution.getDistribution().length == 256;
    }
}
