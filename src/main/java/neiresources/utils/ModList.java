package neiresources.utils;

import neiresources.compatibility.CompatBase;
import neiresources.compatibility.cofh.CoFHCompat;
import neiresources.compatibility.minecraft.MinecraftCompat;

public enum ModList
{
    minecraft("neiresources", MinecraftCompat.class),
    cofhcore("CoFHCore",CoFHCompat.class);

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
        try {
            return (CompatBase) compat.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
