package neresources.utils;

import neresources.api.entry.ISeedEntry;
import neresources.registry.SeedEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.common.ForgeHooks;

import java.util.ArrayList;
import java.util.List;

public class SeedHelper {

    public static List<ISeedEntry> getSeeds()
    {
        List<ISeedEntry> result = new ArrayList<ISeedEntry>();
        Class<?> seedEntry;
        try {
            seedEntry = Class.forName("net.minecraftforge.common.ForgeHooks$SeedEntry");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        List seedList = (List)ReflectionHelper.getObject(ForgeHooks.class,"seedList",null);
        for (Object o:seedList)
        {
            if (o==null || o.getClass()!=seedEntry) continue;
            ItemStack seed = (ItemStack) ReflectionHelper.getObject(seedEntry,"seed", o);
            int weight = ReflectionHelper.getInt(WeightedRandom.Item.class,"itemWeight",o);
            result.add(new SeedEntry(seed, weight));
        }
        return result;
    }
}
