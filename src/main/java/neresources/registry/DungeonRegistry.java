package neresources.registry;

import neresources.api.entry.IDungeonEntry;
import neresources.utils.ReflectionHelper;
import neresources.utils.TranslationHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DungeonRegistry
{
    private Map<String, DungeonEntry> registry = new LinkedHashMap<String, DungeonEntry>();
    public static Map<String, String> categoryToLocalKeyMap = new LinkedHashMap<String, String>();
    private static DungeonRegistry instance = null;

    public static DungeonRegistry getInstance()
    {
        if (instance == null)
            return instance = new DungeonRegistry();
        return instance;
    }

    public DungeonRegistry()
    {
        addCategoryMapping("mineshaftCorridor", "ner.dungeon.mineshaftCorridor");
        addCategoryMapping("pyramidDesertyChest", "ner.dungeon.pyramidDesertyChest");
        addCategoryMapping("pyramidJungleChest", "ner.dungeon.pyramidJungleChest");
        addCategoryMapping("pyramidJungleDispenser", "ner.dungeon.pyramidJungleDispenser");
        addCategoryMapping("strongholdCorridor", "ner.dungeon.strongholdCorridor");
        addCategoryMapping("strongholdLibrary", "ner.dungeon.strongholdLibrary");
        addCategoryMapping("strongholdCrossing", "ner.dungeon.strongholdCrossing");
        addCategoryMapping("villageBlacksmith", "ner.dungeon.villageBlacksmith");
        addCategoryMapping("bonusChest", "ner.dungeon.bonusChest");
        addCategoryMapping("dungeonChest", "ner.dungeon.dungeonChest");
    }

    public static boolean addCategoryMapping(String category, String name)
    {
        if (!categoryToLocalKeyMap.containsKey(category))
        {
            categoryToLocalKeyMap.put(category, name);
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
        if (categoryToLocalKeyMap.containsKey(name))
            return registerChestHook(categoryToLocalKeyMap.get(name), chestGenHooks);
        return registerChestHook(name, chestGenHooks);
    }

    public void registerDungeonEntry(IDungeonEntry entry)
    {
        registerChestHook(entry.getName(), entry.getChestGenHooks());
    }

    public List<DungeonEntry> getDungeons(ItemStack item)
    {
        List<DungeonEntry> list = new ArrayList<DungeonEntry>();
        for (DungeonEntry entry : registry.values())
            if (entry.containsItem(item)) list.add(entry);
        return list;
    }

    public List<DungeonEntry> getDungeons()
    {
        return new ArrayList<DungeonEntry>(registry.values());
    }

    public String getNumStacks(DungeonEntry entry)
    {
        int max = entry.getMaxStacks();
        int min = entry.getMinStacks();
        if (min == max) return max + " " + TranslationHelper.translateToLocal("ner.stacks");
        return min + " - " + max + " " + TranslationHelper.translateToLocal("ner.stacks");
    }
}
