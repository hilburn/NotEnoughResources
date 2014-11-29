package neresources.api.messages;

import cpw.mods.fml.common.event.FMLInterModComms;
import neresources.api.messages.utils.MessageKeys;

public class SendMessage
{
    public static void sendMessage(RegisterOreMessage entry)
    {
        FMLInterModComms.sendMessage(MessageKeys.notEnoughResources,MessageKeys.registerOre,entry.getMessage());
    }

    public static void sendMessage(ModifyOreMessage entry)
    {
        FMLInterModComms.sendMessage(MessageKeys.notEnoughResources,MessageKeys.modifyOre,entry.getMessage());
    }

    public static void sendMessage(RegisterMobMessage entry)
    {
        FMLInterModComms.sendMessage(MessageKeys.notEnoughResources,MessageKeys.registerMob,entry.getMessage());
    }

    public static void sendMessage(ModifyMobMessage entry)
    {
        FMLInterModComms.sendMessage(MessageKeys.notEnoughResources,MessageKeys.modifyMob,entry.getMessage());
    }
}
