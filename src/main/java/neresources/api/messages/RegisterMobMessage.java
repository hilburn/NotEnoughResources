package neresources.api.messages;

import neresources.api.messages.utils.MessageHelper;
import neresources.api.messages.utils.MessageKeys;
import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import net.minecraft.nbt.NBTTagCompound;

public class RegisterMobMessage extends Message
{
    private String className;
    private LightLevel lightLevel;
    private DropItem[] drops;

    public RegisterMobMessage(Class clazz)
    {
        this(clazz.getName());
    }

    public RegisterMobMessage(String className)
    {
        this(className, LightLevel.any);
    }

    public RegisterMobMessage(Class clazz, LightLevel level)
    {
        this(clazz.getName(), level);
    }

    public RegisterMobMessage(String className, LightLevel level)
    {
        this(className, level, new DropItem[0]);
    }

    public RegisterMobMessage(Class clazz, LightLevel level, DropItem[] drops)
    {
        this(clazz.getName(), level, drops);
    }

    public RegisterMobMessage(String className, LightLevel level, DropItem[] drops)
    {
        this.className = className;
        this.lightLevel = level;
        this.drops = drops;
    }

    public RegisterMobMessage(NBTTagCompound tagCompound)
    {
        this.className = tagCompound.getString(MessageKeys.className);
        this.lightLevel = LightLevel.decodeLightLevel(tagCompound.getString(MessageKeys.lightLevel));
        this.drops = MessageHelper.getDropItems(tagCompound,MessageKeys.addDrops);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString(MessageKeys.className,className);
        tagCompound.setString(MessageKeys.lightLevel,lightLevel.encode());
        tagCompound.setTag(MessageKeys.addDrops, MessageHelper.getDropItemList(drops));
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return !className.equals("");
    }
}
