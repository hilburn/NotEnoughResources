package neresources.api.messages;

import neresources.api.messages.utils.MessageHelper;
import neresources.api.messages.utils.MessageKeys;
import net.minecraft.nbt.NBTTagCompound;

public class RemoveMobMessage extends RemoveMessage
{
    private String className;
    private boolean strict;
    private boolean witherSkeleton;

    public RemoveMobMessage(Object clazz)
    {
        this(clazz, false);
    }

    public RemoveMobMessage(Object clazz, boolean strict)
    {
        this(clazz,strict,false);
    }

    public RemoveMobMessage(Object clazz, boolean strict, boolean witherSkeleton)
    {
        this.className = MessageHelper.getClass(clazz);
        this.strict = strict;
        this.witherSkeleton = witherSkeleton;
    }

    public RemoveMobMessage(NBTTagCompound tagCompound)
    {
        this.className = tagCompound.getString(MessageKeys.className);
        this.strict = tagCompound.getBoolean(MessageKeys.strict);
        this.witherSkeleton = tagCompound.getBoolean(MessageKeys.wither);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString(MessageKeys.className, this.className);
        tagCompound.setBoolean(MessageKeys.strict, this.strict);
        tagCompound.setBoolean(MessageKeys.wither, this.witherSkeleton);
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return !this.className.equals("");
    }
}
