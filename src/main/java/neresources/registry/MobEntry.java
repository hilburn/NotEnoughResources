package neresources.registry;

import neresources.api.entry.IMobEntry;
import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import net.minecraft.entity.EntityLivingBase;

import java.util.ArrayList;
import java.util.List;

public class MobEntry implements IMobEntry
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

    @Override
    public EntityLivingBase getEntity()
    {
        return entity;
    }

    @Override
    public String getMobName()
    {
        return entity.getCommandSenderName();
    }

    public DropItem[] getDrops()
    {
        return drops.toArray(new DropItem[drops.size()]);
    }

    public String[] getBiomes()
    {
        return biomes.toArray(new String[biomes.size()]);
    }

    @Override
    public LightLevel getLightLevel()
    {
        return lightLevel;
    }
}
