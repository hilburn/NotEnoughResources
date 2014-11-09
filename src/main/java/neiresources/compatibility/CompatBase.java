package neiresources.compatibility;

import cpw.mods.fml.common.Loader;

public class CompatBase
{

    public CompatBase(String name)
    {
        if (Loader.isModLoaded(name))
            init();
    }

    public void init()
    {
    }
}
