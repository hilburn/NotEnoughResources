package neresources.utils;

import neresources.entries.SeedEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class SeedHelper
{
    public static List<SeedEntry> getSeeds()
    {
        List<SeedEntry> result = new ArrayList<SeedEntry>();
        Class<?> seedEntry;
        seedEntry = ReflectionHelper.findClass("net.minecraftforge.common.ForgeHooks$SeedEntry");
        if (seedEntry == null) return result;
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
