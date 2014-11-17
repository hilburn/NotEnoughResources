package neresources.registry;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class MySeedEntry extends WeightedRandom.Item
{
    public ItemStack seed;
    public MySeedEntry(ItemStack seed,int p_i1556_1_) {
        super(p_i1556_1_);
        this.seed = seed;
    }
}
