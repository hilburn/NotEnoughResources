package neresources.registry;

import neresources.api.entry.IDungeonEntry;
import net.minecraftforge.common.ChestGenHooks;

public class DungeonEntry implements IDungeonEntry
{
    private String name;
    private ChestGenHooks chestGenHooks;

    public DungeonEntry(String name, ChestGenHooks chestGenHooks)
    {
        this.name = name;
        this.chestGenHooks = chestGenHooks;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public ChestGenHooks getChestGenHooks()
    {
        return this.chestGenHooks;
    }
}
