package neresources;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import neresources.config.ConfigHandler;
import neresources.config.Settings;
import neresources.proxy.CommonProxy;
import neresources.reference.MetaData;
import neresources.reference.Reference;
import neresources.utils.IMCHandler;
import neresources.utils.LogHelper;
import neresources.utils.ReflectionHelper;
import net.minecraft.util.WeightedRandom;

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
    }

    @EventHandler
    public void imcCallback(FMLInterModComms.IMCEvent event)
    {
        for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
        {
            if (imcMessage.isNBTMessage())
                IMCHandler.registerIMCMessage(imcMessage.key, imcMessage.getNBTValue());
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {

    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event)
    {
        PROXY.initCompatibility();
    }
}
