package neresources.registry;

import neresources.entries.OreEntry;
import neresources.utils.MapKeys;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OreRegistry
{
    private Map<String, OreMatchEntry> matchEntryMap = new LinkedHashMap<String, OreMatchEntry>();
    private Map<String, String> dropToOreMap = new LinkedHashMap<String, String>();

    private static OreRegistry instance = null;

    public static OreRegistry getInstance()
    {
        if (instance == null)
            return instance = new OreRegistry();
        return instance;
    }

    public void register(OreEntry entry)
    {
        String key = MapKeys.getKey(entry.getOre());
        if (key == null) return;
        ItemStack drop = entry.getOre();
        String dropKey = MapKeys.getKey(drop);
        if (dropKey != null && !dropToOreMap.containsKey(dropKey))
            dropToOreMap.put(dropKey, key);

        if (matchEntryMap.containsKey(key))
            matchEntryMap.get(key).add(entry);
        else
            matchEntryMap.put(key, new OreMatchEntry(entry));

    }

    public OreMatchEntry getRegistryMatches(ItemStack itemStack)
    {
        String key = MapKeys.getKey(itemStack);
        if (dropToOreMap.containsKey(key))
            return matchEntryMap.get(dropToOreMap.get(key));
        else
            return matchEntryMap.get(key);
    }

    public List<OreMatchEntry> getOres()
    {
        return new ArrayList<OreMatchEntry>(matchEntryMap.values());
    }

    public boolean removeDrops(ChangeOreDrop oreMod)
    {
        if (oreMod.removeDrops() == null) return true;
        String oreKey = MapKeys.getKey(oreMod.ore());
        if (oreKey == null) return false;
        for (ItemStack drop : oreMod.removeDrops())
        {
            String dropKey = MapKeys.getKey(drop);
            if (dropKey == null || !dropToOreMap.containsKey(dropKey)) continue;
            if (dropToOreMap.get(dropKey).equalsIgnoreCase(oreKey))
            {
                dropToOreMap.remove(dropKey);
                matchEntryMap.get(oreKey).removeDrop(drop);
            }
        }
        return true;
    }

    public boolean addDrops(ChangeOreDrop oreMod)
    {
        if (oreMod.addDrops() == null) return true;
        String oreKey = MapKeys.getKey(oreMod.ore());
        if (oreKey == null || !matchEntryMap.containsKey(oreKey)) return false;
        for (ItemStack drop : oreMod.addDrops())
        {
            String dropKey = MapKeys.getKey(drop);
            if (dropKey == null || dropToOreMap.containsKey(dropKey)) continue;
            dropToOreMap.put(dropKey, oreKey);
            matchEntryMap.get(oreKey).addDrop(drop);
        }
        return true;
    }

    public boolean addOreLink(String from, String to)
    {
        if (from == null || to == null || dropToOreMap.containsKey(from)) return false;
        dropToOreMap.put(from, to);
        return true;
    }
}
