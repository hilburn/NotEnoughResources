package neiresources.utils;

import neiresources.compatibility.CompatBase;
import neiresources.compatibility.cofh.CoFHCompat;

public enum ModList
{
    cofhcore("CoFHCore");

    private String name;

    ModList(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }

}
