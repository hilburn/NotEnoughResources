package neresources.api;


import neresources.api.utils.MapKeys;

import java.util.LinkedHashMap;
import java.util.Map;

public class NEResourcesAPI
{
    public static Map<String,Object> registryAPI = new LinkedHashMap<String, Object>();

    public static boolean registerDungeonEntry(IDungeonEntry entry)
    {
        if (entry.getChestGenHooks()==null) return false;
        if (entry.getName() == null) return false;
        if (registryAPI.containsKey(entry.getName())) return false;

        registryAPI.put(entry.getName(), entry);
        return true;

    }

    public static boolean registerMobEntry(IMobEntry entry)
    {
        if (entry.getEntity()==null) return false;
        if (registryAPI.containsKey(entry.getEntity().getCommandSenderName())) return false;

        registryAPI.put(entry.getEntity().getCommandSenderName(),entry);
        return true;
    }

    public static boolean registerOreEntry(IOreEntry entry)
    {
        if (entry.getOre()==null) return false;
        if (entry.getDistribution() == null || entry.getDistribution().getDistribution().length != 256) return false;
        if (registryAPI.containsKey(MapKeys.getKey(entry.getOre()))) return false;

        registryAPI.put(MapKeys.getKey(entry.getOre()),entry);
        return true;
    }

}
