package neresources.compatibility.thaumcraft;

import neresources.compatibility.CompatBase;

public class ThaumcraftCompat extends CompatBase {
    private static final ThaumcraftCompat instance = new ThaumcraftCompat();

    public static ThaumcraftCompat instance()
    {
        return instance;
    }
}
