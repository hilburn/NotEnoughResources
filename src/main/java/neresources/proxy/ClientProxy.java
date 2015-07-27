package neresources.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import neresources.utils.NetworkEventHelper;
import net.minecraft.world.World;


public class ClientProxy extends CommonProxy
{

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public void registerEvents()
    {
        super.registerEvents();
        FMLCommonHandler.instance().bus().register(new NetworkEventHelper());
    }
}
