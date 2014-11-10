package neiresources.compatibility.minecraft;

import neiresources.compatibility.CompatBase;
import neiresources.drop.DropItem;
import neiresources.registry.MobRegistryEntry;
import neiresources.utils.LightLevel;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
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
        //Iron Golem
        DropItem ironIngot = new DropItem(Items.iron_ingot,3,5,0.025F);
        //DropItem poppy = new DropItem() should probably make it itemStacks becuse roses are blocks...
        registerMob(new MobRegistryEntry(new EntityIronGolem(null),LightLevel.any,ironIngot));

        //Snow golem
        DropItem snowball = new DropItem(Items.snowball,0,15);
        registerMob(new MobRegistryEntry(new EntitySnowman(null),LightLevel.any,snowball));

        //Cow
        DropItem leather = new DropItem(Items.leather,0,2);
        DropItem steak = new DropItem(Items.beef,1,3);
        registerMob(new MobRegistryEntry(new EntityCow(null),LightLevel.any,leather,steak));

        //Mooshroom
        registerMob(new MobRegistryEntry(new EntityMooshroom(null),LightLevel.any,leather,steak));

        //Chicken
        DropItem feather = new DropItem(Items.feather,0,2);
        DropItem chicken = new DropItem(Items.chicken,1,1);
        registerMob(new MobRegistryEntry(new EntityChicken(null),LightLevel.any,feather,chicken));

        //Pig
        DropItem pork =new DropItem(Items.porkchop,1,3);
        registerMob(new MobRegistryEntry(new EntityPig(null),LightLevel.any,pork));

        //Wither
        DropItem star = new DropItem(Items.nether_star,1,1);
        registerMob(new MobRegistryEntry(new EntityWither(null),LightLevel.any,star));

        //End Dragon
        //DropItem egg = new Dropitem(Items.)
        registerMob(new MobRegistryEntry(new EntityDragon(null),LightLevel.any,new DropItem[]{}));

        //Zombie
        DropItem rottenFlesh = new DropItem(Items.rotten_flesh,0,2);
        ironIngot = new DropItem(Items.iron_ingot,1,1,0.025F);
        DropItem potato = new DropItem(Items.potato,1,1,0.025F);
        DropItem carrot = new DropItem(Items.carrot,1,1,0.025F);
        registerMob(new MobRegistryEntry(new EntityZombie(null), LightLevel.hostile,rottenFlesh,ironIngot,potato,carrot));

        //Skeleton
        DropItem bone = new DropItem(Items.bone,0,2);
        DropItem arrow = new DropItem(Items.arrow,0,2);
        registerMob(new MobRegistryEntry(new EntitySkeleton(null),LightLevel.hostile,bone,arrow));

        //Wither Skeleton
        DropItem coal = new DropItem(Items.coal,0,1);
        DropItem skull = new DropItem(Items.skull,1,1,0.025F);
        EntitySkeleton witherSkeleton = new EntitySkeleton(null);
        witherSkeleton.setSkeletonType(1);
        registerMob(new MobRegistryEntry(witherSkeleton, LightLevel.hostile, bone, coal, skull));

        //Creeper

        //Witches

        //Slimes

        //Magma Creams

        //SIlverfish

        //Bats

        //Blaze
        DropItem blazeRod = new DropItem(Items.blaze_rod,0,1);
        DropItem glowstone = new DropItem(Items.glowstone_dust,0,2);
        registerMob(new MobRegistryEntry(new EntityBlaze(null),LightLevel.blaze,new String[]{"Nether Fortress"},blazeRod,glowstone));
    }
}
