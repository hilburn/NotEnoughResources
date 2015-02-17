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

    public static class Storage
    {
        private final String key;
        private final NBTTagCompound message;

        public Storage(String key, Message message)
        {
            this.key = key;
            this.message = message.writeToNBT(new NBTTagCompound());
        }

        public Storage(String key, NBTTagCompound message)
        {
            this.key = key;
            this.message = message;
        }

        public NBTTagCompound getMessage()
        {
            return message;
        }

        public String getKey()
        {
            return key;
        }
    }
}
