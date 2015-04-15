package neresources.utils;

import neresources.NEResources;
import neresources.config.Settings;
import neresources.network.MessageHandler;
import neresources.network.message.ClientSyncRequestMessage;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;;


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
