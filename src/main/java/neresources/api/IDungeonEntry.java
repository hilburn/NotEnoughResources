package neresources.api;

import net.minecraftforge.common.ChestGenHooks;

public interface IDungeonEntry extends IBaseEntry{

    /**
     * @return the display name for NEI
     */
    public String getName();

    /**
     * @return chest items
     */
    public ChestGenHooks getChestGenHooks();
}
