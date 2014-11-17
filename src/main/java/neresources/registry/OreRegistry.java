package neresources.registry;

import neresources.api.entry.IOreEntry;
import neresources.utils.MapKeys;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OreRegistry
{
    private Map<String, IOreEntry> registry = new LinkedHashMap<String, IOreEntry>();
    private Map<String, OreMatchEntry> matchEntryMap = new LinkedHashMap<String, OreMatchEntry>();
    private List<String> ores = new ArrayList<String>();

    private static OreRegistry instance = null;

    public static OreRegistry getInstance()
    {
        if (instance == null)
            return instance = new OreRegistry();
        return instance;
    }

    public boolean registerOre(IOreEntry entry)
    {
        if (registry.containsKey(entry.getKey())) return false;
        registry.put(entry.getKey(),entry);
        return registerOreMatches(entry);
    }

    private boolean registerOreMatches(IOreEntry entry) {
        ItemStack[] drops = entry.getOreMatches();
        for (int i = 0;i<drops.length;i++) {
            //String[] keys = MapKeys.getKeys(drop);
            ItemStack drop = drops[i];
            String key = MapKeys.getKey(drop);
            if (i==0) ores.add(key);
            //for (String key:keys) {
            if (key==null) return false;
            if (matchEntryMap.containsKey(key)) {
                matchEntryMap.get(key).add(drop, entry);
            } else {
                matchEntryMap.put(key, new OreMatchEntry(drop, entry));
            }
            //}
        }
        return true;
    }


    public OreMatchEntry getRegistryMatches(ItemStack itemStack)
    {
        return matchEntryMap.get(MapKeys.getKey(itemStack));
    }


    public ItemStack oreMatch(IOreEntry entry, ItemStack itemStack)
    {
        ItemStack[] matchStacks = entry.getOreMatches();
        if (matchStacks==null) return null;
        for (ItemStack match : matchStacks) {
            if (match != null && OreDictionary.itemMatches(match, itemStack, false)) return match;
        }
        return null;
    }

    public List<OreMatchEntry> getOres()
    {
        List<OreMatchEntry> result = new ArrayList<OreMatchEntry>();
        for (String key:ores)
        {
            result.add(matchEntryMap.get(key));
        }
        return result;
    }
}
