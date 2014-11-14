package neresources.api;

import neresources.api.drop.DropItem;
import net.minecraft.entity.EntityLiving;

import java.util.List;

public interface IMobEntry extends IBaseEntry{

    /**
     * @return an instance of the entity with World = null
     */
    public EntityLiving getEntity();

    /**
     * @return the maximum light level the entity will spawn in
     */
    public int getLightLevel();

    /**
     * @return any limitations the entity has for spawning
     */
    public List<String> getBiomeLimits();

    /**
     * @return drops
     */
    public List<DropItem> getDrops();
}
