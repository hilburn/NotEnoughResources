package neiresources.registry;

import neiresources.drop.DropItem;
import neiresources.utils.LightLevel;
import net.minecraft.entity.EntityHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MobEntry
{
    private EntityLivingBase entity;
    private List<DropItem> drops = new ArrayList<DropItem>();
    private LightLevel lightLevel;
    private List<String> biomes = new ArrayList<String>();

    public MobEntry(EntityLivingBase entity, LightLevel lightLevel, String[] biomes, DropItem... drops)
    {
        this.entity = entity;
        this.lightLevel = lightLevel;
        for (String biome : biomes)
            this.biomes.add(biome);
        for (DropItem drop : drops)
            this.drops.add(drop);
    }

    public MobEntry(EntityLivingBase entity, LightLevel lightLevel, DropItem... drops)
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
        return entity.getCommandSenderName();
    }

    public String getMobName()
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

    public String getLightLevel()
    {
        return lightLevel.getString();
    }

    public int getExperience()
    {
        if (entity instanceof EntityLiving)
            return EntityHelper.getExperience((EntityLiving) entity);
        return 0;
    }

    public boolean dropsItem(ItemStack item)
    {
        for(DropItem dropItem : drops)
            if (dropItem.item.isItemEqual(item)) return true;
        return false;
    }
}
