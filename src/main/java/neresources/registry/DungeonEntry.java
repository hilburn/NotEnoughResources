package neresources.registry;

import neresources.api.entry.IDungeonEntry;
import net.minecraftforge.common.ChestGenHooks;

public class DungeonEntry implements IDungeonEntry
{
    private String name;
    private ChestGenHooks chestGenHooks;

    public DungeonEntry(String name, ChestGenHooks chestGenHooks)
    {
        setName(name);
        setContents(chestGenHooks);
    }

    public void setContents(ChestGenHooks chestGenHooks)
    {
        this.chestGenHooks = chestGenHooks;
    }

    public void setName(String name)
    {
        this.name = name;
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

    @Override
    public String getKey()
    {
        return getName();
    }
}
