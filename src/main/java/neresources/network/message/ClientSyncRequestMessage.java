package neresources.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import neresources.api.messages.SendMessage;
import neresources.config.Settings;
import neresources.registry.*;
import neresources.utils.LogHelper;

public class ClientSyncRequestMessage implements IMessage, IMessageHandler<ClientSyncMessage, IMessage>
{    
    public ClientSyncRequestMessage()
    {
        LogHelper.info("Requesting Sync...");
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    }

    @Override
    public IMessage onMessage(ClientSyncMessage message, MessageContext ctx)
    {
        LogHelper.info("Receiving Sync");
        DungeonRegistry.getInstance().clear();
        EnchantmentRegistry.getInstance().clear();
        MessageRegistry.clear();
        MobRegistry.getInstance().clear();
        OreRegistry.clear();
        PlantRegistry.getInstance().clear();
        SendMessage.readFromStorage(message.getStorageList());
        MessageRegistry.processMessages();
        Settings.reload();
        LogHelper.info("Synced");
        return null;
    }
}
