package neresources.utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import neresources.NEResources;
import neresources.config.Settings;
import neresources.network.MessageHandler;
import neresources.network.message.ClientSyncRequestMessage;

;


public class NetworkEventHelper
{
    @SubscribeEvent
    public void onConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event)
    {
        if (!Settings.initedCompat)
            NEResources.PROXY.initCompatibility();
        if (!event.isLocal)
            MessageHandler.INSTANCE.sendToServer(new ClientSyncRequestMessage());

    }
}
