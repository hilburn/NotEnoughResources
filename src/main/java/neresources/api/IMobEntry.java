package neresources.api;

import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IMobEntry {

    public EntityLivingBase getEntity();

    public LightLevel getLightLevel();

    public String[] getBiomes();

    public DropItem[] getDrops();

    public boolean dropsItem(ItemStack itemStack);

    public String getMobName();

    public int getExperience();
}
