package neresources.utils;

import neresources.api.entry.ISeedEntry;
import neresources.registry.SeedEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class SeedHelper
{
    public static List<ISeedEntry> getSeeds()
    {
        List<ISeedEntry> result = new ArrayList<ISeedEntry>();
        Class<?> seedEntry;
        seedEntry = ReflectionHelper.findClass("net.minecraftforge.common.ForgeHooks$SeedEntry");
        if (seedEntry==null) return result;
        List seedList = (List) ReflectionHelper.getObject(ForgeHooks.class, "seedList", null);
        for (Object o : seedList)
        {
            if (o == null || o.getClass() != seedEntry) continue;
            ItemStack seed = (ItemStack) ReflectionHelper.getObject(seedEntry, "seed", o);
            if (seed == null || seed.getItem() == null) continue;
            int weight = ReflectionHelper.getInt(WeightedRandom.Item.class, DeObfMappings.itemWeight.getFieldName(), o);
            result.add(new SeedEntry(seed, weight));
        }
        return result;
    }
}
