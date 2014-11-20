package neresources.api.entry;

import net.minecraft.item.ItemStack;

public interface ISeedEntry extends IBaseEntry
{
    /**
     * @return the drop this entry adds
     */
    public ItemStack getDrop();

    /**
     * @return the chance for the given drop
     */
    public int getWeight();
}
