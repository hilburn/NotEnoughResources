package neresources.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import neresources.api.messages.Message;
import neresources.api.messages.SendMessage;
import neresources.utils.LogHelper;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ClientSyncMessage implements IMessage, IMessageHandler<ClientSyncRequestMessage, ClientSyncMessage>
{    
    private List<Message.Storage> storageList;
    
    public ClientSyncMessage()
    {
        this.storageList = new LinkedList<Message.Storage>();
    }
    
    public ClientSyncMessage(List<Message.Storage> storageList)
    {
        LogHelper.info("Sending Sync...");
        this.storageList = storageList;
    }
    
    public List<Message.Storage> getStorageList()
    {
        return storageList;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        int size = buf.readInt();
        for (int i = 0; i < size; i++)
        {
            if (buf.readBoolean())
            {
                int keySize = buf.readInt();
                String key = new String(buf.readBytes(keySize).array());
                int messageSize = buf.readInt();
                byte[] message = buf.readBytes(messageSize).array();
                try
                {
                    NBTTagCompound messageTag = CompressedStreamTools.func_152457_a(message, new NBTSizeTracker(message.length * 8));
                    storageList.add(new Message.Storage(key, messageTag));
                } catch (IOException e)
                {
                    LogHelper.warn("Failed to read message with key " + key);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(storageList.size());
        for (Message.Storage stored : storageList)
        {
            try
            {
                byte[] bytes = CompressedStreamTools.compress(stored.getMessageNBT());
                buf.writeBoolean(true);
                buf.writeInt(stored.getKey().getBytes().length);
                buf.writeBytes(stored.getKey().getBytes());
                buf.writeInt(bytes.length);
                buf.writeBytes(bytes);
            } catch (IOException e)
            {
                LogHelper.warn("Dropped message with key " + stored.getKey());
                buf.writeBoolean(false);
                e.printStackTrace();
            }
        }
    }

    @Override
    public ClientSyncMessage onMessage(ClientSyncRequestMessage message, MessageContext ctx)
    {
        LogHelper.info("Received Sync Request");
        return new ClientSyncMessage(SendMessage.getStorage());
    }
}
