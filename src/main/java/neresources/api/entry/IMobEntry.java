package neresources.api.entry;

import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import net.minecraft.entity.EntityLivingBase;

public interface IMobEntry extends IBaseEntry{

    /**
     * @return an instance of the entity World can be null
     */
    public EntityLivingBase getEntity();

    /**
     * @return the {@link neresources.api.utils.LightLevel} need for the mob to spawn
     */
    public LightLevel getLightLevel();

    /**
     * @return an array with all biomes the mob spawns in
     */
    public String[] getBiomes();

    /**
     * @return an array of {@link neresources.api.utils.DropItem} that list all the possible drops
     */
    public DropItem[] getDrops();

    /**
     * @return A readable name for the mob
     */
    public String getMobName();

}
