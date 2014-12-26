package neresources;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import neresources.config.ConfigHandler;
import neresources.config.Settings;
import neresources.network.MessageHandler;
import neresources.proxy.CommonProxy;
import neresources.reference.MetaData;
import neresources.reference.Reference;
import neresources.registry.MessageRegistry;
import neresources.utils.LogHelper;
import neresources.utils.NetworkEventHelper;
import neresources.utils.ReflectionHelper;
import neresources.utils.WorldEventHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION_FULL, guiFactory = "neresources.gui.ModGuiFactory", dependencies = "after:NotEnoughItems;after:CoFHCore")
public class NEResources
{

    @Instance(value = Reference.ID)
    public static NEResources INSTANCE;

    @Mod.Metadata(Reference.ID)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "neresources.proxy.ClientProxy", serverSide = "neresources.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Loading configs..");
        Settings.side = event.getSide();
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());

        LogHelper.info("Updating ModMetaData...");
        metadata = MetaData.init(metadata);

        ReflectionHelper.isObf = ReflectionHelper.doesFieldExist(WeightedRandom.Item.class, "field_76292_a");
        LogHelper.debug("Minecraft is " + (ReflectionHelper.isObf ? "obf" : "deObf"));

        LogHelper.info("Registering Network Messages...");
        MessageHandler.init();
        
        LogHelper.info("Registering Events...");
        PROXY.registerEvents();
    }

    @EventHandler
    public void imcCallback(FMLInterModComms.IMCEvent event)
    {
        for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
        {
            LogHelper.debug("Message Received - Sender: " + imcMessage.getSender() + " - Message Type: " + imcMessage.key);
            if (imcMessage.isNBTMessage())
                MessageRegistry.registerMessage(imcMessage.key, imcMessage.getNBTValue());
        }
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        PROXY.initCompatibility();
    }
}
