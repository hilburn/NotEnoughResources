package neresources.registry;

import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

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
        this.biomes.addAll(Arrays.asList(biomes));
        this.drops.addAll(Arrays.asList(drops));
    }

    public MobEntry(EntityLivingBase entity, LightLevel lightLevel, DropItem... drops)
    {
        this.entity = entity;
        this.lightLevel = lightLevel;
        this.biomes.add("Any");
        this.drops.addAll(Arrays.asList(drops));
    }

    public EntityLivingBase getEntity()
    {
        return entity;
    }

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

    public boolean addDrop(DropItem item)
    {
        for (DropItem drop : drops)
            if (drop.item.isItemEqual(item.item)) return false;
        drops.add(item);
        return true;
    }

    public LightLevel getLightLevel()
    {
        return lightLevel;
    }

    public void removeDrop(ItemStack item)
    {
        int i = 0;
        for (; i < drops.size(); i++)
            if (drops.get(i).item.isItemEqual(item)) break;
        if (i < drops.size()) drops.remove(i);
    }
}
