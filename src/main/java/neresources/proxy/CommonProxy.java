package neresources.proxy;

import neresources.compatibility.Compatibility;
import neresources.utils.WorldEventHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;


public class CommonProxy
{

    public World getClientWorld()
    {
        return null;
    }

    public void initCompatibility()
    {
        Compatibility.init();
    }

    public void registerEvents()
    {
        MinecraftForge.EVENT_BUS.register(new WorldEventHelper());
    }
}
