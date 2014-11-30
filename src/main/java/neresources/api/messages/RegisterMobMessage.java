package neresources.api.messages;

import neresources.api.messages.utils.MessageHelper;
import neresources.api.messages.utils.MessageKeys;
import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import neresources.utils.ReflectionHelper;
import net.minecraft.nbt.NBTTagCompound;

public class RegisterMobMessage extends Message
{
    private Class mobClass;
    private LightLevel lightLevel;
    private DropItem[] drops;

    public RegisterMobMessage(Class clazz)
    {
        this(clazz, LightLevel.any);
    }

    public RegisterMobMessage(Class clazz, LightLevel level)
    {
        this(clazz, level, new DropItem[0]);
    }

    public RegisterMobMessage(Class clazz, LightLevel level, DropItem[] drops)
    {
        this.mobClass = clazz;
        this.lightLevel = level;
        this.drops = drops;
    }

    public RegisterMobMessage(NBTTagCompound tagCompound)
    {
        this.mobClass = ReflectionHelper.findClass(tagCompound.getString(MessageKeys.className));
        this.lightLevel = LightLevel.decodeLightLevel(tagCompound.getString(MessageKeys.lightLevel));
        this.drops = MessageHelper.getDropItems(tagCompound, MessageKeys.addDrops);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString(MessageKeys.className, mobClass.getName());
        tagCompound.setString(MessageKeys.lightLevel, lightLevel.encode());
        tagCompound.setTag(MessageKeys.addDrops, MessageHelper.getDropItemList(drops));
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return mobClass != null;
    }

    public Class getMobClass()
    {
        return mobClass;
    }

    public LightLevel getLightLevel()
    {
        return lightLevel;
    }

    public DropItem[] getDrops()
    {
        return drops;
    }
}
