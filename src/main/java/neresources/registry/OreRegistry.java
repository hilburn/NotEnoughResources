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
    private Map<String, OreMatchEntry> matchEntryMap = new LinkedHashMap<String, OreMatchEntry>();
    private List<String> ores = new ArrayList<String>();

    private static OreRegistry instance = null;

    public static OreRegistry getInstance()
    {
        if (instance == null)
            return instance = new OreRegistry();
        return instance;
    }

    public boolean registerOre(IOreEntry entry) {
        ItemStack[] drops = entry.getOreMatches();
        for (int i = 0;i<drops.length;i++) {
            ItemStack drop = drops[i];
            String key = MapKeys.getKey(drop);
            if (key==null) return false;
            if (ItemStack.areItemStacksEqual(drop,entry.getOre(drop))&&!ores.contains(key)) ores.add(key);
            if (matchEntryMap.containsKey(key)) {
                matchEntryMap.get(key).add(drop, entry);
            } else {
                matchEntryMap.put(key, new OreMatchEntry(drop, entry));
            }
        }
        return true;
    }

    public OreMatchEntry getRegistryMatches(ItemStack itemStack)
    {
        return matchEntryMap.get(MapKeys.getKey(itemStack));
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
