package neresources.registry;

import neresources.api.messages.ModifyOreMessage;
import neresources.api.messages.RegisterOreMessage;
import neresources.api.utils.Priority;
import neresources.utils.MapKeys;
import net.minecraft.item.ItemStack;

import java.util.*;

public class NewOreRegistry
{
    private static Map<Integer, OreMatchEntry> matchEntryMap = new LinkedHashMap<Integer, OreMatchEntry>();
    private static Map<String, Set<Integer>> dropMap = new LinkedHashMap<String, Set<Integer>>();

    public static void registerOre(RegisterOreMessage message)
    {
        String key = MapKeys.getKey(message.getOre());
        if (key==null) return;

        if (dropMap.containsKey(key))
        {
            Set<Integer> hashCodes = dropMap.get(key);
            boolean matched = false;
            for (int hashCode:hashCodes)
            {
                if (matchEntryMap.get(hashCode).add(message))
                {
                    matched = true;
                    break;
                }
            }
            if (!matched)
            {
                int newHash = addNewOre(key,message);
                for (int hashCode:hashCodes)
                {
                    if (hashCode!=newHash)
                        matchEntryMap.get(newHash).add(matchEntryMap.get(hashCode));
                }
            }
        }
        else
        {
            addNewOre(key, message);
        }
        ItemStack[] drops = message.getDrops();
        if (drops == null || drops.length == 0) return;
        MessageRegistry.addMessage(new ModifyOreMessage(message.getOre(), Priority.FIRST,drops));
    }

    private static int addNewOre(String key, RegisterOreMessage message)
    {
        OreMatchEntry newMatch = new OreMatchEntry(message);
        int hashCode = newMatch.hashCode();
        Set<Integer> hashSet = dropMap.containsKey(key)? dropMap.get(key) : new LinkedHashSet<Integer>();
        hashSet.add(hashCode);
        dropMap.put(key,hashSet);
        matchEntryMap.put(hashCode,newMatch);
        return hashCode;
    }

    public List<OreMatchEntry> getOres()
    {
        List<OreMatchEntry> result = new ArrayList<OreMatchEntry>();
        Set<Integer> addedCodes = new TreeSet<Integer>();
        for (Set<Integer> hashCodes : dropMap.values())
        {
            for (int hashCode : hashCodes)
            {
                if (!addedCodes.contains(hashCode))
                {
                    addedCodes.add(hashCode);
                    result.add(matchEntryMap.get(hashCode));
                }
            }
        }
        return result;
    }

    public List<OreMatchEntry> getRegistryMatches(ItemStack itemStack)
    {
        String key = MapKeys.getKey(itemStack);
        List<OreMatchEntry> result = new ArrayList<OreMatchEntry>();
        if (dropMap.containsKey(key))
        {
            for (Integer hashCode : dropMap.get(key))
            {
                result.add(matchEntryMap.get(hashCode));
            }
        }
        return result;
    }
}
