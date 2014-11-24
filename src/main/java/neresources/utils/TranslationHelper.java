package neresources.utils;

import net.minecraft.util.StatCollector;

public class TranslationHelper
{
    public static String translateToLocal(String key)
    {
        return StatCollector.translateToLocal(key);
    }

    public static String getLocalPageInfo(int page, int lastPage)
    {
        return TranslationHelper.translateToLocal("ner.page") + " " + (page + 1) + " " + TranslationHelper.translateToLocal("ner.of") + " " + (lastPage + 1);
    }
}
