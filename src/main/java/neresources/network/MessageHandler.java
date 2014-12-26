package neresources.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import neresources.network.message.ClientSyncMessage;
import neresources.network.message.ClientSyncRequestMessage;
import neresources.reference.Reference;

public class MessageHandler implements IMessageHandler
{
    public static SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Reference.ID);
    public static int id = 0;
    
    public static void init()
    {
        INSTANCE.registerMessage(ClientSyncRequestMessage.class, ClientSyncMessage.class, id++, Side.SERVER);
        INSTANCE.registerMessage(ClientSyncMessage.class, ClientSyncRequestMessage.class, id++, Side.SERVER);
    }
    
    @Override
    public IMessage onMessage(IMessage message, MessageContext ctx)
    {
        return null;
    }
}
