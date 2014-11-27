package neresources.config;

import cpw.mods.fml.relauncher.Side;
import neresources.nei.NEIDungeonHandler;
import neresources.nei.NEIEnchantmentHandler;
import neresources.nei.NEIMobHandler;
import neresources.nei.NEIOreHandler;

public final class Settings
{
    public static int ITEMS_PER_ROW;
    public static int ITEMS_PER_COLUMN;
    public static float CYCLE_TIME;

    public static int EXTRA_RANGE;
    public static Side side;

    public static void reload()
    {
        if (side == Side.CLIENT)
        {
            NEIDungeonHandler.reloadSettings();
            NEIMobHandler.reloadSettings();
            NEIOreHandler.reloadSettings();
            NEIEnchantmentHandler.reloadSettings();
        }
    }
}
