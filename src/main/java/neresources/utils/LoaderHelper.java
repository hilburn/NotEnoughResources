package neresources.utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class LoaderHelper
{
    public static boolean isModVersion(String modId, String version)
    {
        if(Loader.isModLoaded("modId"))
        {
            for (ModContainer mod : Loader.instance().getActiveModList())
            {
                if (mod.getModId().equals(modId) && mod.getDisplayVersion().equals(version))
                {
                    return true;
                }
            }
        }
        return false;
    }
}
