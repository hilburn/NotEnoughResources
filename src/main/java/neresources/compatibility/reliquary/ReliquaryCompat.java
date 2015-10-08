package neresources.compatibility.reliquary;

import neresources.api.messages.ModifyMobMessage;
import neresources.api.utils.DropItem;
import neresources.api.utils.LightLevel;
import neresources.api.utils.conditionals.Conditional;
import neresources.compatibility.CompatBase;
import neresources.entries.MobEntry;
import neresources.registry.MessageRegistry;
import neresources.utils.LoaderHelper;
import neresources.utils.ModList;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.item.Item;
import xreliquary.event.CommonEventHandler;
import xreliquary.init.XRRecipes;
import xreliquary.lib.Names;
import xreliquary.lib.Reference;

public class ReliquaryCompat extends CompatBase
{

    @Override
    protected void init()
    {
        if (LoaderHelper.isModVersion(ModList.Names.RELIQUARY, "1.2"))
            registerReliquaryOldMobs();
        else
            registerReliquaryMobs();
    }

    private void registerReliquaryOldMobs()
    {
        //Squid
        DropItem beak = new DropItem((Item)Item.itemRegistry.getObject("xreliquary:" + Names.squid_beak), 1, 1, 0.04F, Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntitySquid.class, beak));

        //Witch
        DropItem witch_hat = new DropItem((Item)Item.itemRegistry.getObject("xreliquary:" + Names.witch_hat), 0, 1, Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityWitch.class, witch_hat));
    }

    private void registerReliquaryMobs()
    {
        CommonEventHandler eventHandler = new CommonEventHandler();

        //Squid
        DropItem squid_beak = new DropItem(XRRecipes.ingredient(Reference.SQUID_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.squid_beak), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntitySquid.class, squid_beak));

        //Witch
        DropItem witch_hat = new DropItem(XRRecipes.getItem(Names.witch_hat), 1, 1, eventHandler.getBaseDrop(Names.witch_hat), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityWitch.class, witch_hat));

        //Spider
        DropItem spider_fangs = new DropItem(XRRecipes.ingredient(Reference.SPIDER_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.spider_fangs), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntitySpider.class, true, spider_fangs));

        //Cave Spider
        DropItem cave_spider_fangs = new DropItem(XRRecipes.ingredient(Reference.SPIDER_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.cave_spider_fangs), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityCaveSpider.class, cave_spider_fangs));

        //Skeleton
        DropItem rib_bone = new DropItem(XRRecipes.ingredient(Reference.SKELETON_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.rib_bone), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntitySkeleton.class, true, rib_bone));

        //Wither Skeleton
        DropItem withered_rib = new DropItem(XRRecipes.ingredient(Reference.WITHER_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.withered_rib), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntitySkeleton.class, true, true, withered_rib));

        //Zombie
        DropItem zombie_heart = new DropItem(XRRecipes.ingredient(Reference.ZOMBIE_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.zombie_heart), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityZombie.class, true, zombie_heart));

        //Zombie Pigman
        DropItem pigman_heart = new DropItem(XRRecipes.ingredient(Reference.ZOMBIE_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.pigman_heart), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityPigZombie.class, pigman_heart));

        //Slime
        DropItem slime_pearl = new DropItem(XRRecipes.ingredient(Reference.SLIME_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.slime_pearl), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntitySlime.class, slime_pearl));

        //Blaze
        DropItem blaze_molten_core = new DropItem(XRRecipes.ingredient(Reference.MOLTEN_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.blaze_molten_core), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityBlaze.class, blaze_molten_core));

        //Magma Cube
        DropItem magma_cube_molten_core = new DropItem(XRRecipes.ingredient(Reference.MOLTEN_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.magma_cube_molten_core), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityMagmaCube.class, magma_cube_molten_core));

        //Ghast
        DropItem ghast_gland = new DropItem(XRRecipes.ingredient(Reference.CREEPER_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.ghast_gland), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityGhast.class, ghast_gland));

        //Creeper
        DropItem creeper_gland = new DropItem(XRRecipes.ingredient(Reference.CREEPER_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.creeper_gland), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityCreeper.class, creeper_gland));

        //Charged Creeper
        DropItem eye_of_the_storm = new DropItem(XRRecipes.ingredient(Reference.STORM_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.eye_of_the_storm), Conditional.playerKill);
        EntityCreeper chargedCreeper = new EntityCreeper(world);
        chargedCreeper.onStruckByLightning(null);
        chargedCreeper.extinguish();
        registerMob(new MobEntry(chargedCreeper, LightLevel.hostile, eye_of_the_storm));

        //Enderman
        DropItem ender_heart = new DropItem(XRRecipes.ingredient(Reference.ENDER_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.ender_heart), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityEnderman.class, ender_heart));

        //Bat
        DropItem bat_wing = new DropItem(XRRecipes.ingredient(Reference.BAT_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.bat_wing), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntityBat.class, bat_wing));

        //Snow Golem
        DropItem frozen_core = new DropItem(XRRecipes.ingredient(Reference.FROZEN_INGREDIENT_META), 1, 1, eventHandler.getBaseDrop(Names.frozen_core), Conditional.playerKill);
        MessageRegistry.addMessage(new ModifyMobMessage(EntitySnowman.class, frozen_core));
    }
}
