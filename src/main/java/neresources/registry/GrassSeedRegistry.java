package neresources.registry;

import neresources.api.entry.ISeedEntry;
import neresources.api.utils.KeyGen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.SeedHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GrassSeedRegistry
{
    private Map<String, ISeedEntry> seedDrops = new LinkedHashMap<String, ISeedEntry>();
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
        List<ISeedEntry> seedEntryList = SeedHelper.getSeedList();
        for (ISeedEntry entry : seedEntryList) {
            totalWeight += entry.getWeight();
            seedDrops.put(KeyGen.getKey(entry.getDrop()), entry);
        }
        totalWeight*=8;
    }

    public void add(ISeedEntry entry)
    {
        String key = KeyGen.getKey(entry.getDrop());
        if (!seedDrops.containsKey(key)) return;
        seedDrops.put(key,new SeedEntry(entry.getDrop(),(totalWeight*entry.getWeight())));
    }

    public boolean contains(ItemStack itemStack)
    {
        return contains(KeyGen.getKey(itemStack));
    }

    private boolean contains(String key)
    {
        return seedDrops.containsKey(key);
    }

    public float getChance(ItemStack itemStack)
    {
        String key = KeyGen.getKey(itemStack);
        if (!contains(key)) return -1;
        else return ((float)seedDrops.get(key).getWeight())/totalWeight;
    }

    public Map<ItemStack,Float> getAllDrops()
    {
        Map<ItemStack,Float> result = new LinkedHashMap<ItemStack, Float>();
        for (ISeedEntry entry: seedDrops.values())
        {
            result.put(entry.getDrop(),((float)entry.getWeight())/totalWeight);
        }
        return result;
    }
}