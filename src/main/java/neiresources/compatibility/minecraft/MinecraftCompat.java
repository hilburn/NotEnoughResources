package neiresources.compatibility.minecraft;

import neiresources.compatibility.CompatBase;
import neiresources.drop.DropItem;
import neiresources.registry.MobRegistryEntry;
import neiresources.utils.LightLevel;
import net.minecraft.entity.EntityHelper;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
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
        registerVanillaMobs();
    }

    private void registerVanillaMobs()
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
        DropItem coal = new DropItem(Items.coal,0,1,0.33F);
        DropItem skull = new DropItem(Items.skull,1,1,0.025F);
        EntitySkeleton witherSkeleton = new EntitySkeleton(null);
        witherSkeleton.setSkeletonType(1);
        registerMob(new MobRegistryEntry(witherSkeleton, LightLevel.hostile,new String[]{"Nether Fortress"}, bone, coal, skull));

        //Creeper
        DropItem gunpowder = new DropItem(Items.gunpowder,0,2);
        registerMob(new MobRegistryEntry(new EntityCreeper(null),LightLevel.hostile,gunpowder));

        //Ghast
        DropItem tear = new DropItem(Items.ghast_tear,0,1);
        registerMob(new MobRegistryEntry(new EntityCreeper(null),LightLevel.hostile,gunpowder,tear));

        //Witches
        DropItem bottle = new DropItem(Items.glass_bottle,0,6);
        DropItem glowstone = new DropItem(Items.glowstone_dust,0,6);
        gunpowder = new DropItem(Items.gunpowder,0,6);
        DropItem redstone = new DropItem(Items.redstone,0,6);
        DropItem spider = new DropItem(Items.spider_eye,0,6);
        DropItem stick = new DropItem(Items.stick,0,6);
        DropItem sugar = new DropItem(Items.sugar,0,6);
        registerMob(new MobRegistryEntry(new EntityWitch(null),LightLevel.hostile,bottle,glowstone,gunpowder,redstone,spider,stick,sugar));

        //Slimes
        DropItem slimeball = new DropItem(Items.slime_ball,0,2);
        registerMob(new MobRegistryEntry(MonsterHelper.setSlimeSize(new EntitySlime(null),1), LightLevel.hostile, slimeball));

        //Magma Cube
        DropItem magma = new DropItem(Items.magma_cream,0,1);
        registerMob(new MobRegistryEntry(MonsterHelper.setSlimeSize(new EntityMagmaCube(null),1), LightLevel.hostile, magma));

        //Silverfish
        registerMob(new MobRegistryEntry(new EntitySilverfish(null),LightLevel.hostile,new DropItem[]{}));

        //Bats
        registerMob(new MobRegistryEntry(new EntityBat(null),LightLevel.hostile,new DropItem[]{}));

        //Spider
        DropItem string = new DropItem(Items.string,0,2);
        spider = new DropItem(Items.spider_eye,1,1,0.33F);
        registerMob(new MobRegistryEntry(new EntitySpider(null),LightLevel.hostile,string,spider));

        //Cave Spider
        registerMob(new MobRegistryEntry(new EntityCaveSpider(null),LightLevel.hostile,string,spider));

        //Blaze
        DropItem blazeRod = new DropItem(Items.blaze_rod,0,1);
        glowstone = new DropItem(Items.glowstone_dust,0,2);
        registerMob(new MobRegistryEntry(new EntityBlaze(null),LightLevel.blaze,new String[]{"Nether Fortress"},blazeRod,glowstone));
    }
}
