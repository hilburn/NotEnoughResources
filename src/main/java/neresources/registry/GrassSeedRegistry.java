package neresources.registry;

import neresources.api.entry.IGrassEntry;
import neresources.api.utils.KeyGen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.SeedHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GrassSeedRegistry
{
    private Map<String,MySeedEntry> seedDrops = new LinkedHashMap<String, MySeedEntry>();
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
        List<MySeedEntry> seedEntryList=SeedHelper.getSeedList();
        for (MySeedEntry entry:seedEntryList) {
            totalWeight += entry.itemWeight;
            seedDrops.put(KeyGen.getKey(entry.seed),entry);
        }
        totalWeight*=8;
    }

    public void add(IGrassEntry entry)
    {
        String key = KeyGen.getKey(entry.getDrop());
        if (!seedDrops.containsKey(key)) return;
        seedDrops.put(key,new MySeedEntry(entry.getDrop(),(int)(totalWeight*entry.getChance())));
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
        else return ((float)seedDrops.get(key).itemWeight)/totalWeight;
    }

    public Map<ItemStack,Float> getAllDrops()
    {
        Map<ItemStack,Float> result = new LinkedHashMap<ItemStack, Float>();
        for (MySeedEntry entry: seedDrops.values())
        {
            result.put(entry.seed,((float)entry.itemWeight)/totalWeight);
        }
        return result;
    }
}
