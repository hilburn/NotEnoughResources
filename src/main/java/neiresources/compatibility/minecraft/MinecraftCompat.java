package neiresources.compatibility.minecraft;

import neiresources.compatibility.CompatBase;
import neiresources.drop.DropItem;
import neiresources.registry.MobRegistryEntry;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;

public class MinecraftCompat extends CompatBase
{

    public static MinecraftCompat instance = null;

    public static MinecraftCompat newInstance()
    {
        if (instance!=null)
            return instance;
        else
            return instance = new MinecraftCompat();
    }

    public MinecraftCompat()
    {
        super("neiresources");
    }

    @Override
    public void init()
    {
        //Zombie
        DropItem rottenFlesh = new DropItem(Items.rotten_flesh,0,2);
        DropItem ironIngot = new DropItem(Items.iron_ingot,1,1,0.025F);
        DropItem potato = new DropItem(Items.potato,1,1,0.025F);
        DropItem carrot = new DropItem(Items.carrot,1,1,0.025F);
        registerMob(new MobRegistryEntry(new EntityZombie(null),8, rottenFlesh,ironIngot,potato,carrot));

        //Blaze
        DropItem blazeRod = new DropItem(Items.blaze_rod,0,1);
        DropItem glowstone = new DropItem(Items.glowstone_dust,0,2);
        registerMob(new MobRegistryEntry(new EntityBlaze(null),8,blazeRod,glowstone));

        //Snow golem
        DropItem snowball = new DropItem(Items.snowball,0,15);
        registerMob(new MobRegistryEntry(new EntitySnowman(null),-1,snowball));


    }
}
