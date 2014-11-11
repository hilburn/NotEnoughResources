package neiresources;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import neiresources.proxy.CommonProxy;
import neiresources.reference.Reference;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VERSION_FULL, dependencies = "after:NotEnoughItems;after:CoFHCore")
public class NEIResources
{

    @Instance(value = Reference.ID)
    public static NEIResources INSTANCE;

    @SidedProxy(clientSide = "neiresources.proxy.ClientProxy", serverSide = "neiresources.proxy.CommonProxy")
    public static CommonProxy PROXY;

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
