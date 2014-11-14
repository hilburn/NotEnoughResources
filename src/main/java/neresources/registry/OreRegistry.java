package neresources.registry;

import neresources.api.entry.IOreEntry;
import neresources.api.utils.MapKeys;
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
        return true;
    }

    public OreMatchEntry getRegistryMatches(ItemStack itemStack)
    {
        String key = MapKeys.getKey(itemStack);
        if (matchEntryMap.containsKey(key)) return matchEntryMap.get(key);
        Map<ItemStack,IOreEntry> matches = new LinkedHashMap<ItemStack,IOreEntry>();
        for (IOreEntry entry:registry.values())
        {
            ItemStack match = oreMatch(entry,itemStack);
            if (match != null) matches.put(match,entry);
        }
        OreMatchEntry matchEntry = matches.size() == 0 ? null : new OreMatchEntry(matches);
        matchEntryMap.put(key,matchEntry);
        return matchEntry;
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
        return new ArrayList<OreMatchEntry>(matchEntryMap.values());
    }
}
