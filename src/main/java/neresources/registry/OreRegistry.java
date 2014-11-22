package neresources.registry;

import neresources.api.entry.IModifyOre;
import neresources.api.entry.IOreEntry;
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
        if (key==null) return;
        ItemStack drop = entry.getOre();
        String dropKey = MapKeys.getKey(drop);
        if (dropKey != null && !dropToOreMap.containsKey(dropKey))
            dropToOreMap.put(dropKey, key);

        if (matchEntryMap.containsKey(key))
            matchEntryMap.get(key).add(entry);
        else
            matchEntryMap.put(key, new OreMatchEntry(entry));

    }

    public boolean registerOre(IOreEntry entry)
    {
        ItemStack[] drops = entry.getOreMatches();
        List<ItemStack> nonOres = new ArrayList<ItemStack>();
        for (ItemStack drop : drops)
        {
            String key = MapKeys.getKey(drop);
            if (key == null) return false;
            if (!ItemStack.areItemStacksEqual(drop, entry.getOre(drop)))
            {
                if (!dropToOreMap.containsKey(key))
                {
                    String oreKey = MapKeys.getKey(entry.getOre(drop));
                    if (oreKey == null) continue;
                    dropToOreMap.put(key, oreKey);
                }
                nonOres.add(drop);
            } else
            {
                OreEntry oreEntry = new OreEntry(drop, entry.getDistribution(drop), entry.getColour(drop));
                if (matchEntryMap.containsKey(key))
                {
                    matchEntryMap.get(key).add(oreEntry);
                } else
                {
                    matchEntryMap.put(key, new OreMatchEntry(oreEntry));
                }
            }
        }
        for (ItemStack nonOre:nonOres)
        {
            String key = MapKeys.getKey(nonOre);
            if (dropToOreMap.containsKey(key))
            {
                if (matchEntryMap.containsKey(dropToOreMap.get(key)))
                    matchEntryMap.get(dropToOreMap.get(key)).addDrop(nonOre);
            }
        }
        return true;
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

    public boolean removeDrops(IModifyOre oreMod)
    {
        if (oreMod.removeDrops() == null) return true;
        String oreKey = MapKeys.getKey(oreMod.ore());
        if (oreKey == null) return false;
        for (ItemStack drop: oreMod.removeDrops())
        {
            String dropKey = MapKeys.getKey(drop);
            if (dropKey==null || !dropToOreMap.containsKey(dropKey)) continue;
            if (dropToOreMap.get(dropKey).equalsIgnoreCase(oreKey))
            {
                dropToOreMap.remove(dropKey);
                matchEntryMap.get(oreKey).removeDrop(drop);
            }
        }
        return true;
    }

    public boolean addDrops(IModifyOre oreMod)
    {
        if (oreMod.addDrops() == null) return true;
        String oreKey = MapKeys.getKey(oreMod.ore());
        if (oreKey == null || !matchEntryMap.containsKey(oreKey)) return false;
        for (ItemStack drop: oreMod.addDrops())
        {
            String dropKey = MapKeys.getKey(drop);
            if (dropKey == null || dropToOreMap.containsKey(dropKey)) continue;
            dropToOreMap.put(dropKey,oreKey);
            matchEntryMap.get(oreKey).addDrop(drop);
        }
        return true;
    }
}
