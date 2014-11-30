package neresources.api.messages;

import cpw.mods.fml.common.event.FMLInterModComms;
import neresources.api.messages.utils.MessageKeys;

public class SendMessage
{
    public static void sendMessage(RegisterDungeonMessage message)
    {
        sendMessage(message, MessageKeys.registerDungeon);
    }
    public static void sendMessage(RegisterOreMessage message)
    {
        sendMessage(message, MessageKeys.registerOre);
    }

    public static void sendMessage(ModifyOreMessage message)
    {
        sendMessage(message, MessageKeys.modifyOre);
    }

    public static void sendMessage(RegisterMobMessage message)
    {
        sendMessage(message, MessageKeys.registerMob);
    }

    public static void sendMessage(ModifyMobMessage message)
    {
        sendMessage(message, MessageKeys.modifyMob);
    }

    public static void sendMessage(RemoveMobMessage message)
    {
        sendMessage(message, MessageKeys.removeMob);
    }

    public static void sendMessage(Message message, String key)
    {
        if (message.isValid()) FMLInterModComms.sendMessage(MessageKeys.notEnoughResources, key, message.getMessage());
    }
}
