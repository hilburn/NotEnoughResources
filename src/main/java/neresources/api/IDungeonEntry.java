package neresources.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public interface IDungeonEntry {

    public String getName();

    public ChestGenHooks getChestGenHooks();

    public boolean hasItem(ItemStack itemStack);

    public double getChance(WeightedRandomChestContent content);
}
