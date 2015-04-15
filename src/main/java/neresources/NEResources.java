package neresources;

import neresources.config.ConfigHandler;
import neresources.config.Settings;
import neresources.network.MessageHandler;
import neresources.proxy.CommonProxy;
import neresources.reference.MetaData;
import neresources.reference.Reference;
import neresources.registry.EnchantmentRegistry;
import neresources.registry.MessageRegistry;
import neresources.utils.LogHelper;
import neresources.utils.ReflectionHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION_FULL, guiFactory = "neresources.gui.ModGuiFactory", dependencies = "after:NotEnoughItems")
public class NEResources
{

    @Mod.Instance(value = Reference.ID)
    public static NEResources INSTANCE;

    @Mod.Metadata(Reference.ID)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "neresources.proxy.ClientProxy", serverSide = "neresources.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
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

    @Mod.EventHandler
    public void imcCallback(FMLInterModComms.IMCEvent event)
    {
        for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
        {
            LogHelper.debug("Message Received - Sender: " + imcMessage.getSender() + " - Message Type: " + imcMessage.key);
            if (imcMessage.isNBTMessage())
                MessageRegistry.registerMessage(imcMessage.key, imcMessage.getNBTValue());
        }
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        Settings.gameLoaded = true;
    }

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
        PROXY.initCompatibility();
        EnchantmentRegistry.getInstance().removeAll(Settings.excludedEnchants);
    }
}
