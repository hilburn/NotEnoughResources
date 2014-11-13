package neresources.compatibility;

import neresources.utils.ModList;

public class Compatibility {

    public static void init()
    {
        for (ModList mod: ModList.values())
        {
            mod.initialise();
        }
    }
}
