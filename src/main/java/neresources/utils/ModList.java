package neresources.utils;

import neresources.compatibility.CompatBase;
import neresources.compatibility.cofh.CoFHCompat;
import neresources.compatibility.metallurgy.MetallurgyCompat;
import neresources.compatibility.minecraft.MinecraftCompat;
import neresources.compatibility.netherores.NetherOresCompat;

import java.lang.reflect.InvocationTargetException;

public enum ModList
{
    cofhcore("CoFHCore", CoFHCompat.class),
    minecraft("neresources", MinecraftCompat.class),
    metallurgy("Metallurgy", MetallurgyCompat.class),
    netherores("NetherOres", NetherOresCompat.class),
    denseores("denseores");

    private String name;
    private Class<? extends CompatBase> compat;

    ModList(String name)
    {
        this(name,null);
    }

    ModList(String name, Class<? extends CompatBase> compat)
    {
        this.name = name;
        this.compat = compat;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public CompatBase initialise()
    {
        if (compat == null) return null;
        try
        {
            return (CompatBase) compat.getMethod("newInstance").invoke(null);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        } catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
