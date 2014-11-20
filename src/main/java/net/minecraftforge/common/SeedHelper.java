package net.minecraftforge.common;

import neresources.api.entry.ISeedEntry;
import neresources.registry.SeedEntry;

import java.util.ArrayList;
import java.util.List;

public class SeedHelper {

    public static List<ISeedEntry> getSeedList()
    {
        List<ISeedEntry> result = new ArrayList<ISeedEntry>();
        for (ForgeHooks.SeedEntry seedEntry : ForgeHooks.seedList)
            result.add(new SeedEntry(seedEntry.seed, seedEntry.itemWeight));
        return result;
    }
}
