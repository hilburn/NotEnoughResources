package neiresources.registry;

import neiresources.drop.DropItem;
import net.minecraft.entity.EntityHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;

public class MobRegistryEntry
{
    private EntityLivingBase entity;
    private List<DropItem> drops = new ArrayList<DropItem>();
    private int lightLevel;
    private List<String> biomes = new ArrayList<String>();

    public MobRegistryEntry(EntityLivingBase entity, int lightLevel, String[] biomes, DropItem... drops)
    {
        this.entity = entity;
        this.lightLevel = lightLevel;
        for (String biome : biomes)
            this.biomes.add(biome);
        for (DropItem drop : drops)
            this.drops.add(drop);
    }

    public MobRegistryEntry(EntityLivingBase entity, int lightLevel, DropItem... drops)
    {
        this.entity = entity;
        this.lightLevel = lightLevel;
        this.biomes.add("all");
        for (DropItem drop : drops)
            this.drops.add(drop);
    }

    public EntityLivingBase getEntity()
    {
        return entity;
    }

    public String getName()
    {
        return EntityHelper.getEntityName(this.entity);
    }

    public List<DropItem> getDrops()
    {
        return drops;
    }

    public List<String> getBiomes()
    {
        return biomes;
    }

    public int getLightLevel()
    {
        return lightLevel;
    }

    public int getExperience()
    {
        if (entity instanceof EntityLiving)
            return EntityHelper.getExperience((EntityLiving) entity);
        return 0;
    }
}
