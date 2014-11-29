package neresources.utils;

import cpw.mods.fml.common.Loader;
import neresources.compatibility.CompatBase;
import neresources.compatibility.appliedenergistics2.AE2Compat;
import neresources.compatibility.bigreactors.BigReactorsCompat;
import neresources.compatibility.cofh.CoFHCompat;
import neresources.compatibility.forestry.ForestryCompat;
import neresources.compatibility.metallurgy.MetallurgyCompat;
import neresources.compatibility.minecraft.MinecraftCompat;
import neresources.compatibility.netherores.NetherOresCompat;
import neresources.compatibility.reika.ElectriCraftCompat;
import neresources.compatibility.reika.ReactorCraftCompat;
import neresources.compatibility.thaumcraft.ThaumcraftCompat;
import neresources.compatibility.tinkersconstruct.TiConCompat;

public enum ModList
{
    minecraft(new MinecraftCompat()),
    cofhcore(Names.COFHCORE, new CoFHCompat()),
    metallurgy(Names.METALLURGY, new MetallurgyCompat()),
    netherores(Names.NETHERORES, new NetherOresCompat()),
    bigreactors(Names.BIGREACTORS, new BigReactorsCompat()),
    ae2(Names.APPLIEDENERGISTICS, new AE2Compat()),
    thaumcraft(Names.THAUMCRAFT, new ThaumcraftCompat()),
    electricraft(Names.ELECTRICRAFT, new ElectriCraftCompat()),
    reactorcraft(Names.REACTORCRAFT, new ReactorCraftCompat()),
    forestry(Names.FORESTRY,new ForestryCompat()),
    ticon(Names.TICON, new TiConCompat()),
    denseores(Names.DENSEORES);

    private String name;
    private CompatBase compat;
    private boolean isLoaded;

    ModList(CompatBase compat)
    {
        name = "minecraft";
        this.compat = compat;
        isLoaded = true;
    }

    ModList(String name)
    {
        this(name,null);
    }

    ModList(String name, CompatBase compat)
    {
        this.name = name;
        this.compat = compat;
        this.isLoaded = Loader.isModLoaded(this.name);
    }

    public boolean isLoaded()
    {
        return isLoaded;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public boolean initialise()
    {
        if (compat == null) return false;
        return compat.load(this);
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
    }
}
