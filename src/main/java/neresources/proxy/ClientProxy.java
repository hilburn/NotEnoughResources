package neresources.proxy;

import neresources.utils.NetworkEventHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;


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
