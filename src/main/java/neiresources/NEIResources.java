package neiresources;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import neiresources.config.ConfigHandler;
import neiresources.proxy.CommonProxy;
import neiresources.reference.Reference;
import neiresources.utils.LogHelper;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION_FULL, guiFactory = "neiresources.gui.ModGuiFactory", dependencies = "after:NotEnoughItems;after:CoFHCore")
public class NEIResources
{

    @Instance(value = Reference.ID)
    public static NEIResources INSTANCE;

    @SidedProxy(clientSide = "neiresources.proxy.ClientProxy", serverSide = "neiresources.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        LogHelper.info("Loading configs..");
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
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
