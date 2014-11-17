package neresources.config;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import neresources.reference.Reference;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler
{
    public static Configuration config;

    public static void init(File configFile)
    {
        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfig();
        }
        Settings.loadSettings();
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.ID))
        {
            loadConfig();
            Settings.loadSettings();
        }
    }

    private static void loadConfig()
    {
        Settings.ITEMS_PER_COLUMN = config.getInt("Items Per Column", Configuration.CATEGORY_GENERAL, 4, 1, 4, "Changes the amount of items per column in the NEI dungeon and mob views");
        Settings.ITEMS_PER_ROW = config.getInt("Items Per Row", Configuration.CATEGORY_GENERAL, 4, 1, 4, "Changes the amount of items per row in the NEI dungeon view");

        Settings.CYCLE_TIME = config.getFloat("Cycle Time", Configuration.CATEGORY_GENERAL, 1.5F, 0.5F, 3.0F, "Show duration before cycle in NEI views");

        Settings.EXTRA_RANGE = config.getInt("Extra range", Configuration.CATEGORY_GENERAL, 0, 0, 25, "Changes the extra yLevels displayed on the graph in the NEI ore View");

        if (config.hasChanged())
        {
            config.save();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        return list;
    }
}
