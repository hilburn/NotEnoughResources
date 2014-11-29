package neresources.api.messages;

import net.minecraft.nbt.NBTTagCompound;

public abstract class Message
{
    public NBTTagCompound getMessage()
    {
        return writeToNBT(new NBTTagCompound());
    }

    public abstract NBTTagCompound writeToNBT(NBTTagCompound tagCompound);

    public abstract boolean isValid();
}
