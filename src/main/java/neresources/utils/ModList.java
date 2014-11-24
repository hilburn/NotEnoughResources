package neresources.utils;

import neresources.compatibility.CompatBase;
import neresources.compatibility.cofh.CoFHCompat;
import neresources.compatibility.metallurgy.MetallurgyCompat;
import neresources.compatibility.minecraft.MinecraftCompat;

import java.lang.reflect.InvocationTargetException;

public enum ModList
{
    cofhcore("CoFHCore", CoFHCompat.class),
    minecraft("neresources", MinecraftCompat.class),
    metallurgy("Metallurgy", MetallurgyCompat.class);

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
