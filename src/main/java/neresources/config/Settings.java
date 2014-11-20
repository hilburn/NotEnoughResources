package neresources.config;

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

    public static void load()
    {
        NEIDungeonHandler.loadSettings();
        NEIMobHandler.loadSettings();
        NEIOreHandler.loadSettings();
        NEIEnchantmentHandler.loadSettings();
    }
}
