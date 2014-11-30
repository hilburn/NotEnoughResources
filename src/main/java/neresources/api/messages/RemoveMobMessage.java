package neresources.api.messages;

import neresources.api.messages.utils.MessageKeys;
import neresources.utils.ReflectionHelper;
import net.minecraft.nbt.NBTTagCompound;

public class RemoveMobMessage extends RemoveMessage
{
    private Class filterClass;
    private boolean strict;
    private boolean witherSkeleton;

    public RemoveMobMessage(Class clazz)
    {
        this(clazz, false);
    }

    public RemoveMobMessage(Class clazz, boolean strict)
    {
        this(clazz, strict, false);
    }

    public RemoveMobMessage(Class clazz, boolean strict, boolean witherSkeleton)
    {
        this.filterClass = clazz;
        this.strict = strict;
        this.witherSkeleton = witherSkeleton;
    }

    public RemoveMobMessage(NBTTagCompound tagCompound)
    {
        this.filterClass = ReflectionHelper.findClass(tagCompound.getString(MessageKeys.className));
        this.strict = tagCompound.getBoolean(MessageKeys.strict);
        this.witherSkeleton = tagCompound.getBoolean(MessageKeys.wither);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString(MessageKeys.className, this.filterClass.getName());
        tagCompound.setBoolean(MessageKeys.strict, this.strict);
        tagCompound.setBoolean(MessageKeys.wither, this.witherSkeleton);
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return !this.filterClass.equals("");
    }

    public Class getFilterClass()
    {
        return filterClass;
    }

    public boolean isStrict()
    {
        return strict;
    }

    public boolean isWither()
    {
        return witherSkeleton;
    }
}
