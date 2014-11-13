package neiresources.registry;

import neiresources.utils.ReflectionHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DungeonRegistry
{
    private Map<String, DungeonEntry> registry = new LinkedHashMap<String, DungeonEntry>();
    public static Map<String,String> categoryToNameMap = new LinkedHashMap<String, String>();
    private static DungeonRegistry instance = null;

    public static DungeonRegistry getInstance()
    {
        if (instance==null)
            return instance=new DungeonRegistry();
        return instance;
    }

    public DungeonRegistry()
    {
        addCategoryMapping("mineshaftCorridor","Mineshaft");
        addCategoryMapping("pyramidDesertyChest","Desert Temple");
        addCategoryMapping("pyramidJungleChest","Jungle Temple");
        addCategoryMapping("pyramidJungleDispenser","Jungle Temple");
        addCategoryMapping("strongholdCorridor","Stronghold Corridor");
        addCategoryMapping("strongholdLibrary","Stronghold Library");
        addCategoryMapping("strongholdCrossing","Stronghold Crossing");
        addCategoryMapping("villageBlacksmith", "Blacksmith");
        addCategoryMapping("bonusChest","Bonus");
        addCategoryMapping("dungeonChest", "Dungeon");
    }

    public static boolean addCategoryMapping(String category, String name)
    {
        if (!categoryToNameMap.containsKey(category)) {
            categoryToNameMap.put(category,name);
            return true;
        }
        return false;
    }

    public boolean registerChestHook(String name, ChestGenHooks chestGenHooks)
    {
        if (!registry.containsKey(name)) {
            registry.put(name, new DungeonEntry(name,chestGenHooks));
            return true;
        }
        return false;
    }

    public boolean registerChestHook(ChestGenHooks chestGenHooks)
    {
        String name = ReflectionHelper.getString(ChestGenHooks.class, "category", chestGenHooks);
        if (categoryToNameMap.containsKey(name)) return registerChestHook(categoryToNameMap.get(name),chestGenHooks);
        return registerChestHook(name,chestGenHooks);
    }

    public List<DungeonEntry> getDungeons(ItemStack item) {
        List<DungeonEntry> list = new ArrayList<DungeonEntry>();
        for (DungeonEntry entry : registry.values())
            if (entry.hasItem(item)) list.add(entry);
        return list;
    }

    public List<DungeonEntry> getDungeons() {
        return new ArrayList<DungeonEntry>(registry.values());
    }

}
