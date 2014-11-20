package neresources.api.entry;

import net.minecraftforge.common.ChestGenHooks;

public interface IDungeonEntry extends IBaseEntry
{
    /**
     * @return the display name for NEI can be a localization key. Translate to local is called in our nei view
     */
    public String getName();

    /**
     * @return the {@Link net.minecraftforge.common.ChestGenHooks} bound to this entry
     */
    public ChestGenHooks getChestGenHooks();
}
