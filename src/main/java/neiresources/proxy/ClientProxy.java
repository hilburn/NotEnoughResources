package neiresources.proxy;

import cpw.mods.fml.client.FMLClientHandler;
import neiresources.compatibility.Compatibility;
import net.minecraft.world.World;


public class ClientProxy extends CommonProxy
{

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public void initCompatibility()
	{
		Compatibility.init();
	}

}
