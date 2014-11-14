package neresources.api;

import neresources.api.drop.DropItem;
import net.minecraft.entity.EntityLivingBase;

public interface IMobEntry {

    public EntityLivingBase getEntity();

    public int getLightLevel();

    public String[] getBiomeLimits();

    public DropItem[] getDrops();
}
