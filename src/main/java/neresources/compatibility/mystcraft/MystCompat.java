package neresources.compatibility.mystcraft;

import com.xcompwiz.mystcraft.world.WorldProviderMyst;
import cpw.mods.fml.common.Optional;
import neresources.compatibility.CompatBase;
import neresources.utils.ModList;
import net.minecraftforge.common.DimensionManager;

public class MystCompat extends CompatBase
{
	@Optional.Method(modid = ModList.Names.MYSTCRAFT)
	public static boolean isMystDim(int dim)
	{
		try
		{
			return DimensionManager.getProvider(dim) instanceof WorldProviderMyst;
		}
		catch (NullPointerException n)
		{
			return false;
		}
	}
}
