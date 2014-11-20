package neresources.api;

import cpw.mods.fml.common.Loader;
import neresources.api.entry.IBaseEntry;

import java.util.LinkedHashMap;
import java.util.Map;

public class NEResourcesAPI
{
    private static Map<String,IBaseEntry> registryAPI = new LinkedHashMap<String, IBaseEntry>();

    /**
     * Adds {@link neresources.api.entry.IBaseEntry} to the registry
     * @param entry the to add entry
     * @return true if succeeded
     */
    public static boolean registerEntry(IBaseEntry entry)
    {
        String key = Loader.instance().activeModContainer().getModId()+":"+ entry.getKey();
        if (key == null || key == "") return false;
        if (registryAPI.containsKey(key)) return false;
        registryAPI.put(key,entry);
        return true;
    }

    /**
     * @return get the registry
     */
    public static Map<String,IBaseEntry> getRegistryAPI()
    {
        return registryAPI;
    }
}
