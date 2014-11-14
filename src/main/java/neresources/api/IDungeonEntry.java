package neresources.api;

import net.minecraftforge.common.ChestGenHooks;

public interface IDungeonEntry {

    public String getName();

    public ChestGenHooks getChestGenHooks();
}
