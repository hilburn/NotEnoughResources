package neresources.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import neresources.registry.DungeonRegistry;
import neresources.registry.EnchantmentRegistry;
import neresources.registry.MobRegistry;

public class ClientSyncRequestMessage implements IMessage, IMessageHandler<ClientSyncMessage, IMessage>
{
    public ClientSyncRequestMessage()
    {
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
        DungeonRegistry.getInstance().regFromBytes(message.dungeonReg);
        DungeonRegistry.catFromBytes(message.dungeonCat);
        EnchantmentRegistry.fromBytes(message.enchantments);
        MobRegistry.getInstance().fromBytes(message.mobs);
        return null;
    }
}
