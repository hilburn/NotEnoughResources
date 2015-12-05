package neresources.utils;

import cpw.mods.fml.common.Loader;
import neresources.compatibility.CompatBase;
import neresources.compatibility.appliedenergistics2.AE2Compat;
import neresources.compatibility.bigreactors.BigReactorsCompat;
import neresources.compatibility.bluepower.BluePowerCompat;
import neresources.compatibility.cofh.CoFHCompat;
import neresources.compatibility.forestry.ForestryCompat;
import neresources.compatibility.ic2.IC2Compat;
import neresources.compatibility.metallurgy.MetallurgyCompat;
import neresources.compatibility.minecraft.MinecraftCompat;
import neresources.compatibility.mobproperties.MobPropertiesCompat;
import neresources.compatibility.netherores.NetherOresCompat;
import neresources.compatibility.reika.ElectriCraftCompat;
import neresources.compatibility.reika.ReactorCraftCompat;
import neresources.compatibility.reliquary.ReliquaryCompat;
import neresources.compatibility.thaumcraft.ThaumcraftCompat;
import neresources.compatibility.tinkersconstruct.TiConCompat;

public enum ModList
{
    minecraft(MinecraftCompat.class),
    cofhcore(Names.COFHCORE, CoFHCompat.class),
    metallurgy(Names.METALLURGY, MetallurgyCompat.class),
    netherores(Names.NETHERORES, NetherOresCompat.class),
    bigreactors(Names.BIGREACTORS, BigReactorsCompat.class),
    ae2(Names.APPLIEDENERGISTICS, AE2Compat.class),
    thaumcraft(Names.THAUMCRAFT, ThaumcraftCompat.class),
    electricraft(Names.ELECTRICRAFT, ElectriCraftCompat.class),
    reactorcraft(Names.REACTORCRAFT, ReactorCraftCompat.class),
    forestry(Names.FORESTRY, ForestryCompat.class),
    ticon(Names.TICON, TiConCompat.class),
    denseores(Names.DENSEORES),
    mystcraft(Names.MYSTCRAFT),
    ic2(Names.IC2, IC2Compat.class),
    mobproperties(Names.MOBPROPERTIES, MobPropertiesCompat.class),
    reliquary(Names.RELIQUARY, ReliquaryCompat.class),
    bluepower(Names.BLUEPOWER, BluePowerCompat.class);

    private String name;
    private Class compat;
    private boolean isLoaded;

    ModList(Class compat)
    {
        name = "minecraft";
        this.compat = compat;
        isLoaded = true;
    }

    ModList(String name)
    {
        this(name, null);
    }

    ModList(String name, Class compat)
    {
        this.name = name;
        this.compat = compat;
        this.isLoaded = Loader.isModLoaded(this.name);
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }

    public Class compatClass()
    {
        return compat;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public boolean initialise()
    {
        return compat != null && CompatBase.load(this);
    }

    public class Names
    {
        public static final String COFHCORE = "CoFHCore";
        public static final String METALLURGY = "Metallurgy";
        public static final String APPLIEDENERGISTICS = "appliedenergistics2";
        public static final String BIGREACTORS = "BigReactors";
        public static final String FORESTRY = "Forestry";
        public static final String NETHERORES = "NetherOres";
        public static final String ELECTRICRAFT = "ElectriCraft";
        public static final String REACTORCRAFT = "ReactorCraft";
        public static final String THAUMCRAFT = "Thaumcraft";
        public static final String TICON = "TConstruct";
        public static final String DENSEORES = "denseores";
        public static final String MYSTCRAFT = "Mystcraft";
        public static final String IC2= "IC2";
        public static final String MOBPROPERTIES = "MobProperties";
        public static final String RELIQUARY = "xreliquary";
        public static final String BLUEPOWER = "bluepower";
    }
}
