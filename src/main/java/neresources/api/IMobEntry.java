package neresources.api;

import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IMobEntry extends IBaseEntry{

    /**
     * @return an instance of the entity with World = null
     */
    public EntityLiving getEntity();

    public LightLevel getLightLevel();

    public String[] getBiomes();

    public DropItem[] getDrops();

    public boolean dropsItem(ItemStack itemStack);

    public String getMobName();

    public int getExperience();

}
