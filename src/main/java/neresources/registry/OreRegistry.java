package neresources.registry;

import neresources.config.Settings;
import neresources.utils.MapKeys;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OreRegistry
{
    private Map<String, OreRegistryEntry> registry = new LinkedHashMap<String, OreRegistryEntry>();

    private static OreRegistry instance = null;

    public static OreRegistry getInstance()
    {
        if (instance==null)
            return instance=new OreRegistry();
        return instance;
    }

    public boolean registerOre(String oreName, OreEntry oreEntry)
    {
        OreRegistryEntry put = registry.get(oreName);
        if (put == null) put = new OreRegistryEntry(oreEntry);
        else put.add(oreEntry);
        registry.put(oreName, put);
        return true;
    }

    public boolean registerOre(ItemStack stack, OreEntry entry)
    {
        String[] keys = MapKeys.getKeys(stack);
        if (keys!=null)
        {
            for (String key:keys)
            {
                registerOre(key,entry);
            }
            return true;
        }
        return false;
    }

    public boolean registerOre(OreEntry entry)
    {
        return registerOre(entry.getOre(),entry);
    }

    public double[] getChances(String oreName)
    {
        return getChances(oreName, Settings.EXTRA_RANGE);
    }

    public double[] getChances(String oreName, int extraRange)
    {
        OreRegistryEntry entry = registry.get(oreName);
        if (entry!=null)
            return entry.getChances(extraRange);
        return null;
    }

    public double[] getChances(ItemStack stack)
    {
        String[] keys = MapKeys.getKeys(stack);
        if (keys!=null && keys.length>0)
            return getChances(keys[0]);
        return null;
    }

    public double[] getChances(ItemStack stack, int extraRange)
    {
        String[] keys = MapKeys.getKeys(stack);
        if (keys.length>0)
            return getChances(keys[0],extraRange);
        return null;
    }

    public OreRegistryEntry getEntry(String oreName)
    {
        return registry.get(oreName);
    }

    public List<OreRegistryEntry> getEntries(ItemStack stack)
    {
        List<OreRegistryEntry> result = new ArrayList<OreRegistryEntry>();
        String[] keys = MapKeys.getKeys(stack);
        for (String key:keys) {
            OreRegistryEntry entry = getEntry(key);
            if (entry!=null)
                result.add(entry);
        }
        return result;
    }

    public List<OreRegistryEntry> getOres()
    {
        return new ArrayList<OreRegistryEntry>(registry.values());
    }

    public void removeMod(String modName)
    {
        for (OreRegistryEntry entry:registry.values())
        {
            entry.remove(modName);
        }
    }
}
