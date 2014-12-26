package neresources.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import neresources.registry.DungeonRegistry;
import neresources.registry.EnchantmentRegistry;
import neresources.registry.MobRegistry;

public class ClientSyncMessage implements IMessage, IMessageHandler<ClientSyncRequestMessage, ClientSyncMessage>
{
    public byte[] dungeonReg;
    public byte[] dungeonCat;
    public byte[] enchantments;
    public byte[] mobs;
    
    public ClientSyncMessage()
    {
        this.dungeonReg = DungeonRegistry.getInstance().regToBytes();
        this.dungeonCat = DungeonRegistry.catToBytes();
        this.enchantments = EnchantmentRegistry.toBytes();
        this.mobs = MobRegistry.getInstance().toBytes();
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        int size = buf.readInt();
        this.dungeonReg = buf.readBytes(size).array();
        size = buf.readInt();
        this.dungeonCat = buf.readBytes(size).array();
        size = buf.readInt();
        this.enchantments = buf.readBytes(size).array();
        size = buf.readInt();
        this.mobs = buf.readBytes(size).array();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(this.dungeonReg.length);
        buf.writeBytes(this.dungeonReg);
        buf.writeInt(this.dungeonCat.length);
        buf.writeBytes(this.dungeonCat);
        buf.writeInt(this.enchantments.length);
        buf.writeBytes(this.enchantments);
        buf.writeInt(this.mobs.length);
        buf.writeBytes(this.mobs);
    }

    @Override
    public ClientSyncMessage onMessage(ClientSyncRequestMessage message, MessageContext ctx)
    {
        return new ClientSyncMessage();
    }
}
