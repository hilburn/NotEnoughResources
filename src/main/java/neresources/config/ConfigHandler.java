package neresources.config;

import cpw.mods.fml.client.config.IConfigElement;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import neresources.reference.Reference;
import neresources.utils.TranslationHelper;
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
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.ID))
        {
            loadConfig();
        }
    }

    private static void loadConfig()
    {
        Settings.ITEMS_PER_COLUMN = config.getInt(TranslationHelper.translateToLocal("ner.config.itemsPerColumn.title"), Configuration.CATEGORY_GENERAL, 4, 1, 4, TranslationHelper.translateToLocal("ner.config.itemsPerColumn.description"));
        Settings.ITEMS_PER_ROW = config.getInt(TranslationHelper.translateToLocal("ner.config.itemsPerRow.title"), Configuration.CATEGORY_GENERAL, 4, 1, 4, TranslationHelper.translateToLocal("ner.config.itemsPerRow.description"));

        Settings.CYCLE_TIME = config.getFloat(TranslationHelper.translateToLocal("ner.config.cycleTime.title"), Configuration.CATEGORY_GENERAL, 1.5F, 0.5F, 3.0F, TranslationHelper.translateToLocal("ner.config.cycleTime.description"));

        Settings.EXTRA_RANGE = config.getInt(TranslationHelper.translateToLocal("ner.config.extraRange.title"), Configuration.CATEGORY_GENERAL, 3, 0, 25, TranslationHelper.translateToLocal("ner.config.extraRange.description"));

        Settings.useDimNames = config.getBoolean(TranslationHelper.translateToLocal("ner.config.dimNames.title"), Configuration.CATEGORY_GENERAL, true, TranslationHelper.translateToLocal("ner.config.dimNames.description"));

        if (config.hasChanged())
        {
            config.save();
        }
        Settings.reload();
    }

    @SuppressWarnings("unchecked")
    public static List<IConfigElement> getConfigElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
        return list;
    }
}
