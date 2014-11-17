package net.minecraftforge.common;

import neresources.registry.MySeedEntry;

import java.util.ArrayList;
import java.util.List;

public class SeedHelper {

    public static List<MySeedEntry> getSeedList()
    {
        List<MySeedEntry> result = new ArrayList<MySeedEntry>();
        for (ForgeHooks.SeedEntry seedEntry : ForgeHooks.seedList)
        {
            result.add(new MySeedEntry(seedEntry.seed, seedEntry.itemWeight));
        }
        return result;
    }
}
