package neresources.utils;

import neresources.compatibility.CompatBase;
import neresources.compatibility.cofh.CoFHCompat;
import neresources.compatibility.minecraft.MinecraftCompat;

public enum ModList
{
    minecraft("neresources", MinecraftCompat.class),
    cofhcore("CoFHCore", CoFHCompat.class);

    private String name;
    private Class compat;


    ModList(String name, Class compat)
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
        try
        {
            return (CompatBase) compat.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
