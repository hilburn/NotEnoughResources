package neresources.api.entry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
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

    /**
     * @param itemStack
     * @return does the chest contain the itemStack
     */
    public boolean hasItem(ItemStack itemStack);

    /**
     * @param content
     * @return the chance of given chesContent
     */
    public double getChance(WeightedRandomChestContent content);
}
