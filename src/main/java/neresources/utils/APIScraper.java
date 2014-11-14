package neresources.utils;

import neresources.api.NEResourcesAPI;
import neresources.api.entry.IBaseEntry;

import java.util.*;

public class APIScraper
{
    public static <T extends IBaseEntry> Map<String, T> getEntries(Class<T> type)
    {
        Map<String, T> map = new LinkedHashMap<String, T>();
        for (Map.Entry<String, IBaseEntry> entry : NEResourcesAPI.getRegistryAPI().entrySet())
        {
            if (entry.getValue().getClass() == type)
            {
                map.put(entry.getKey(), type.cast(entry.getValue()));
            }
        }
        return map;
    }

    public static <T extends IBaseEntry> Collection<T> getCollection(Class<T> type)
    {
        Collection<T> collection = new ArrayList<T>();
        for (IBaseEntry entry : NEResourcesAPI.getRegistryAPI().values())
        {
            if (entry.getClass() == type)
            {
                collection.add(type.cast(entry));
            }
        }
        return collection;
    }
}
