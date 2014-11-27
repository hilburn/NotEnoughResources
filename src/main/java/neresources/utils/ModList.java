package neresources.utils;

import cpw.mods.fml.common.Loader;
import neresources.compatibility.CompatBase;
import neresources.compatibility.cofh.CoFHCompat;
import neresources.compatibility.metallurgy.MetallurgyCompat;
import neresources.compatibility.minecraft.MinecraftCompat;
import neresources.compatibility.netherores.NetherOresCompat;

public enum ModList
{
    minecraft("neresources", MinecraftCompat.instance()),
    cofhcore("CoFHCore", CoFHCompat.instance()),
    metallurgy("Metallurgy", MetallurgyCompat.instance()),
    netherores("NetherOres", NetherOresCompat.instance()),
    denseores("denseores");

    private String name;
    private CompatBase compat;
    private boolean isLoaded;

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
