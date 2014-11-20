package neresources.registry;

import neresources.api.entry.IDungeonEntry;
import neresources.utils.DungeonHelper;
import neresources.utils.ReflectionHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

import java.util.*;

public class DungeonRegistry
{
    private Map<String, IDungeonEntry> registry = new LinkedHashMap<String, IDungeonEntry>();
    public static Map<String, String> categoryToNameMap = new LinkedHashMap<String, String>();
    private static DungeonRegistry instance = null;

    public static DungeonRegistry getInstance()
    {
        if (instance == null)
            return instance = new DungeonRegistry();
        return instance;
    }

    public DungeonRegistry()
    {
        addCategoryMapping("mineshaftCorridor", "Mineshaft");
        addCategoryMapping("pyramidDesertyChest", "Desert Temple");
        addCategoryMapping("pyramidJungleChest", "Jungle Temple");
        addCategoryMapping("pyramidJungleDispenser", "Jungle Temple");
        addCategoryMapping("strongholdCorridor", "Stronghold Corridor");
        addCategoryMapping("strongholdLibrary", "Stronghold Library");
        addCategoryMapping("strongholdCrossing", "Stronghold Crossing");
        addCategoryMapping("villageBlacksmith", "Blacksmith");
        addCategoryMapping("bonusChest", "Bonus");
        addCategoryMapping("dungeonChest", "Dungeon");
    }

    public static boolean addCategoryMapping(String category, String name)
    {
        if (!categoryToNameMap.containsKey(category))
        {
            categoryToNameMap.put(category, name);
            return true;
        }
        return false;
    }

    public boolean registerChestHook(String name, ChestGenHooks chestGenHooks)
    {
        if (!registry.containsKey(name))
        {
            registry.put(name, new DungeonEntry(name, chestGenHooks));
            return true;
        }
        return false;
    }

    public boolean registerChestHook(ChestGenHooks chestGenHooks)
    {
        String name = ReflectionHelper.getString(ChestGenHooks.class, "category", chestGenHooks);
        if (categoryToNameMap.containsKey(name)) return registerChestHook(categoryToNameMap.get(name), chestGenHooks);
        return registerChestHook(name, chestGenHooks);
    }

    public void registerDungeonEntry(IDungeonEntry entry) {
        registerChestHook(entry.getName(),entry.getChestGenHooks());
    }

    public List<IDungeonEntry> getDungeons(ItemStack item)
    {
        List<IDungeonEntry> list = new ArrayList<IDungeonEntry>();
        for (IDungeonEntry entry : registry.values())
            if (DungeonHelper.hasItem(entry, item)) list.add(entry);
        return list;
    }

    public List<IDungeonEntry> getDungeons()
    {
        return new ArrayList<IDungeonEntry>(registry.values());
    }

    public String getNumStacks(IDungeonEntry entry)
    {
        int max = entry.getChestGenHooks().getMax();
        int min = entry.getChestGenHooks().getMin();
        if (min == max) return max + " Stacks";
        return min + " - " + max + " Stacks";
    }
}
