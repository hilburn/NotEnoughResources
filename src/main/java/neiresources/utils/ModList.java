package main.java.neiresources.utils;

import main.java.neiresources.compatibility.CompatBase;
import main.java.neiresources.compatibility.cofh.CoFHCompat;

public enum ModList {
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
