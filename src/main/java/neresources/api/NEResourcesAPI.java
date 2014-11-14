package neresources.api;

import neresources.api.entry.IBaseEntry;

import java.util.LinkedHashMap;
import java.util.Map;

public class NEResourcesAPI
{
    private static Map<String,IBaseEntry> registryAPI = new LinkedHashMap<String, IBaseEntry>();

    public static boolean registerEntry(IBaseEntry entry)
    {
        String key = entry.getKey();
        if (key == null || key == "") return false;
        if (registryAPI.containsKey(key)) return false;
        registryAPI.put(key,entry);
        return true;
    }

    public static Map<String,IBaseEntry> getRegistryAPI()
    {
        return registryAPI;
    }
}
