package neresources.utils;

import cpw.mods.fml.common.Loader;
import neresources.compatibility.appliedenergistics2.AE2Compat;
import neresources.compatibility.bigreactors.BigReactorsCompat;
import neresources.compatibility.CompatBase;
import neresources.compatibility.cofh.CoFHCompat;
import neresources.compatibility.metallurgy.MetallurgyCompat;
import neresources.compatibility.minecraft.MinecraftCompat;
import neresources.compatibility.netherores.NetherOresCompat;
import neresources.compatibility.reika.ElectriCraftCompat;
import neresources.compatibility.reika.ReactorCraftCompat;
import neresources.compatibility.thaumcraft.ThaumcraftCompat;

public enum ModList
{
    minecraft(MinecraftCompat.instance()),
    cofhcore("CoFHCore", CoFHCompat.instance()),
    metallurgy("Metallurgy", MetallurgyCompat.instance()),
    netherores("NetherOres", NetherOresCompat.instance()),
    bigreactors("BigReactors", BigReactorsCompat.instance()),
    ae2("appliedenergistics2", AE2Compat.instance()),
    thaumcraft("Thaumcraft", ThaumcraftCompat.instance()),
    electricraft("ElectriCraft", ElectriCraftCompat.instance()),
    reactorcraft("ReactorCraft", ReactorCraftCompat.instance()),
    denseores("denseores");

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

}
