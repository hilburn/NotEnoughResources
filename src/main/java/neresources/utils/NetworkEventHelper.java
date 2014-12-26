package neresources.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import neresources.network.MessageHandler;
import neresources.network.message.ClientSyncRequestMessage;;


public class NetworkEventHelper
{
    @SubscribeEvent
    public void onConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        MessageHandler.INSTANCE.sendToServer(new ClientSyncRequestMessage());
    }
}
