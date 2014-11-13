package neiresources.config;

import neiresources.nei.NEIDungeonHandler;
import neiresources.nei.NEIMobHandler;

public final class Settings
{
    public static int ITEMS_PER_ROW;
    public static int ITEMS_PER_COLUMN;
    public static boolean DO_CYLCE;
    public static float CYCLE_TIME;

    public static int EXTRA_RANGE;

    public static void loadSettings()
    {
        NEIDungeonHandler.loadSettings();
        NEIMobHandler.loadSettings();
    }
}
