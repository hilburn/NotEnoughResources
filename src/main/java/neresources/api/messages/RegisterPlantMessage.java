package neresources.api.messages;

import net.minecraft.nbt.NBTTagCompound;

public class RegisterPlantMessage extends Message
{
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        return tagCompound;
    }

    @Override
    public boolean isValid()
    {
        return false;
    }
}
