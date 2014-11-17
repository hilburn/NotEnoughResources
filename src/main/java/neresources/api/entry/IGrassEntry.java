package neresources.api.entry;

import net.minecraft.item.ItemStack;

public interface IGrassEntry extends IBaseEntry{
    public ItemStack getDrop();

    public float getChance();
}
