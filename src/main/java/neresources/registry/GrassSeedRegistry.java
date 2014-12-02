package neresources.registry;

import neresources.entries.SeedEntry;
import neresources.utils.MapKeys;
import neresources.utils.SeedHelper;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GrassSeedRegistry
{
    private Map<String, SeedEntry> seedDrops = new LinkedHashMap<String, SeedEntry>();
    private int totalWeight = 0;
    private static GrassSeedRegistry instance = null;

    public static GrassSeedRegistry getInstance()
    {
        if (instance == null)
            return instance = new GrassSeedRegistry();
        return instance;
    }

    public GrassSeedRegistry()
    {
        List<SeedEntry> seedEntryList = SeedHelper.getSeeds();
        for (SeedEntry entry : seedEntryList)
        {
            totalWeight += entry.getWeight();
            seedDrops.put(MapKeys.getKey(entry.getDrop()), entry);
        }
        totalWeight *= 8;
    }

    public void add(SeedEntry entry)
    {
        String key = MapKeys.getKey(entry.getDrop());
        if (!seedDrops.containsKey(key)) return;
        seedDrops.put(key, new SeedEntry(entry.getDrop(), (totalWeight * entry.getWeight())));
    }

    public boolean contains(ItemStack itemStack)
    {
        return contains(MapKeys.getKey(itemStack));
    }

    private boolean contains(String key)
    {
        return seedDrops.containsKey(key);
    }

    public float getChance(ItemStack itemStack)
    {
        String key = MapKeys.getKey(itemStack);
        if (!contains(key)) return -1;
        else return ((float) seedDrops.get(key).getWeight()) / totalWeight;
    }

    public Map<ItemStack, Float> getAllDrops()
    {
        Map<ItemStack, Float> result = new LinkedHashMap<ItemStack, Float>();
        for (SeedEntry entry : seedDrops.values())
        {
            result.put(entry.getDrop(), ((float) entry.getWeight()) / totalWeight);
        }
        return result;
    }
}
