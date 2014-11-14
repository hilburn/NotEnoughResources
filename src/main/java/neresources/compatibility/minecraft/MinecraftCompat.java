package neresources.compatibility.minecraft;

import neresources.api.distributions.DistributionSquare;
import neresources.api.distributions.DistributionTriangular;
import neresources.compatibility.CompatBase;
import neresources.api.drop.DropItem;
import neresources.registry.DungeonRegistry;
import neresources.registry.MobEntry;
import neresources.registry.OreEntry;
import neresources.utils.LightLevel;
import neresources.utils.ReflectionHelper;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ChestGenHooks;

import java.util.HashMap;

public class MinecraftCompat extends CompatBase
{

    public static MinecraftCompat instance = null;

    public static MinecraftCompat newInstance()
    {
        if (instance != null)
            return instance;
        else
            return instance = new MinecraftCompat();
    }

    public MinecraftCompat()
    {
        super("neresources");
    }

    @Override
    public void init()
    {
        registerVanillaMobs();
        registerDungeonLoot();
        registerOres();
    }

    private void registerVanillaMobs()
    {
        //Iron Golem
        DropItem ironIngot = new DropItem(Items.iron_ingot, 3, 5, 0.025F);
        DropItem poppy = new DropItem(new ItemStack(Blocks.red_flower), 0, 2);
        registerMob(new MobEntry(new EntityIronGolem(null), LightLevel.any, ironIngot, poppy));

        //Snow golem
        DropItem snowball = new DropItem(Items.snowball, 0, 15);
        registerMob(new MobEntry(new EntitySnowman(null), LightLevel.any, snowball));

        //Cow
        DropItem leather = new DropItem(Items.leather, 0, 2);
        DropItem steak = new DropItem(Items.beef, 1, 3);
        registerMob(new MobEntry(new EntityCow(null), LightLevel.any, leather, steak));

        //Mooshroom
        registerMob(new MobEntry(new EntityMooshroom(null), LightLevel.any, leather, steak));

        //Chicken
        DropItem feather = new DropItem(Items.feather, 0, 2);
        DropItem chicken = new DropItem(Items.chicken, 1, 1);
        registerMob(new MobEntry(new EntityChicken(null), LightLevel.any, feather, chicken));

        //Pig
        DropItem pork = new DropItem(Items.porkchop, 1, 3);
        registerMob(new MobEntry(new EntityPig(null), LightLevel.any, pork));

        //Sheep
        DropItem wool = new DropItem(new ItemStack(Blocks.wool), 1, 1);
        registerMob(new MobEntry(new EntitySheep(null), LightLevel.any, wool));

        //Wither
        DropItem star = new DropItem(Items.nether_star, 1, 1);
        registerMob(new MobEntry(new EntityWither(null), LightLevel.any, star));

        //End Dragon
        DropItem egg = new DropItem(new ItemStack(Blocks.dragon_egg), 1, 1);
        registerMob(new MobEntry(new EntityDragon(null), LightLevel.any, egg));

        //Enderman
        DropItem pearl = new DropItem(Items.ender_pearl, 0, 1);
        registerMob(new MobEntry(new EntityEnderman(null), LightLevel.hostile, pearl));

        //Zombie
        DropItem rottenFlesh = new DropItem(Items.rotten_flesh, 0, 2);
        ironIngot = new DropItem(Items.iron_ingot, 1, 1, 0.025F);
        DropItem potato = new DropItem(Items.potato, 1, 1, 0.025F);
        DropItem carrot = new DropItem(Items.carrot, 1, 1, 0.025F);
        registerMob(new MobEntry(new EntityZombie(null), LightLevel.hostile, rottenFlesh, ironIngot, potato, carrot));

        //Zombie Pigman
        DropItem goldNugget = new DropItem(Items.gold_nugget, 0, 1);
        DropItem goldIngot = new DropItem(Items.gold_nugget, 0, 1, 0.025F);
        registerMob(new MobEntry(new EntityPigZombie(null), LightLevel.any, new String[]{"Nether"}, rottenFlesh, goldIngot, goldNugget));

        //Skeleton
        DropItem bone = new DropItem(Items.bone, 0, 2);
        DropItem arrow = new DropItem(Items.arrow, 0, 2);
        registerMob(new MobEntry(new EntitySkeleton(null), LightLevel.hostile, bone, arrow));

        //Wither Skeleton
        DropItem coal = new DropItem(Items.coal, 0, 1, 0.33F);
        DropItem skull = new DropItem(Items.skull, 1, 1, 0.025F);
        EntitySkeleton witherSkeleton = new EntitySkeleton(null);
        witherSkeleton.setSkeletonType(1);
        registerMob(new MobEntry(witherSkeleton, LightLevel.hostile, new String[]{"Nether Fortress"}, bone, coal, skull));

        //Creeper
        DropItem gunpowder = new DropItem(Items.gunpowder, 0, 2);
        registerMob(new MobEntry(new EntityCreeper(null), LightLevel.hostile, gunpowder));

        //Ghast
        DropItem tear = new DropItem(Items.ghast_tear, 0, 1);
        registerMob(new MobEntry(new EntityGhast(null), LightLevel.hostile, new String[]{"Nether"}, gunpowder, tear));

        //Witches
        DropItem bottle = new DropItem(Items.glass_bottle, 0, 6);
        DropItem glowstone = new DropItem(Items.glowstone_dust, 0, 6);
        gunpowder = new DropItem(Items.gunpowder, 0, 6);
        DropItem redstone = new DropItem(Items.redstone, 0, 6);
        DropItem spider = new DropItem(Items.spider_eye, 0, 6);
        DropItem stick = new DropItem(Items.stick, 0, 6);
        DropItem sugar = new DropItem(Items.sugar, 0, 6);
        registerMob(new MobEntry(new EntityWitch(null), LightLevel.hostile, bottle, glowstone, gunpowder, redstone, spider, stick, sugar));

        //Slimes
        DropItem slimeball = new DropItem(Items.slime_ball, 0, 2);
        registerMob(new MobEntry(MonsterHelper.setSlimeSize(new EntitySlime(null), 1), LightLevel.hostile, slimeball));

        //Magma Cube
        DropItem magma = new DropItem(Items.magma_cream, 0, 1);
        registerMob(new MobEntry(MonsterHelper.setSlimeSize(new EntityMagmaCube(null), 1), LightLevel.hostile, new String[]{"Nether"}, magma));

        //Silverfish
        registerMob(new MobEntry(new EntitySilverfish(null), LightLevel.hostile, new DropItem[]{}));

        //Bats
        registerMob(new MobEntry(new EntityBat(null), LightLevel.hostile, new DropItem[]{}));

        //Spider
        DropItem string = new DropItem(Items.string, 0, 2);
        spider = new DropItem(Items.spider_eye, 1, 1, 0.33F);
        registerMob(new MobEntry(new EntitySpider(null), LightLevel.hostile, string, spider));

        //Cave Spider
        registerMob(new MobEntry(new EntityCaveSpider(null), LightLevel.hostile, string, spider));

        //Blaze
        DropItem blazeRod = new DropItem(Items.blaze_rod, 0, 1);
        glowstone = new DropItem(Items.glowstone_dust, 0, 2);
        registerMob(new MobEntry(new EntityBlaze(null), LightLevel.blaze, new String[]{"Nether Fortress"}, blazeRod, glowstone));

        //Squid
        DropItem ink = new DropItem(Items.dye, 0, 1, 3);
        registerMob(new MobEntry(new EntitySquid(null), LightLevel.any, new String[]{"In water"}, ink));
    }

    private void registerDungeonLoot()
    {
        HashMap<String, ChestGenHooks> dungeons = (HashMap<String, ChestGenHooks>) ReflectionHelper.getObject(ChestGenHooks.class, "chestInfo", null);
        ChestGenHooks bonusChest = ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST);
        for (ChestGenHooks chestGenHook : dungeons.values())
        {
            if (chestGenHook != bonusChest)
                DungeonRegistry.getInstance().registerChestHook(chestGenHook);
        }
    }

    private void registerOres()
    {
        registerOre(new OreEntry(new ItemStack(Blocks.lapis_ore), new DistributionTriangular(15, 15, 0.001D), new ItemStack(Items.dye,1,4)));
        registerOre(new OreEntry(new ItemStack(Blocks.iron_ore), new DistributionSquare(0, 5, 54, 65, 0.006D)));
        registerOre(new OreEntry(new ItemStack(Blocks.redstone_ore), new DistributionSquare(0, 5, 12, 17, 0.0083D), new ItemStack(Items.redstone)));
        registerOre(new OreEntry(new ItemStack(Blocks.diamond_ore), new DistributionSquare(0, 5, 12, 17, 0.0012D), new ItemStack(Items.diamond)));
        registerOre(new OreEntry(new ItemStack(Blocks.emerald_ore), new DistributionSquare(0, 5, 12, 17, 0.0012D), new ItemStack(Items.emerald)));
        registerOre(new OreEntry(new ItemStack(Blocks.gold_ore), new DistributionSquare(0, 5, 29, 35, 0.0012D)));
        registerOre(new OreEntry(new ItemStack(Blocks.coal_ore), new DistributionSquare(0, 5, 54, 76, 0.01D)));
    }
}
