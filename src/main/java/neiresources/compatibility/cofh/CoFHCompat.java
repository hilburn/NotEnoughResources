package main.java.neiresources.compatibility.cofh;

import cofh.api.world.IFeatureGenerator;
import main.java.neiresources.compatibility.CompatBase;
import main.java.neiresources.utils.ModList;
import cofh.core.world.WorldHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CoFHCompat extends CompatBase {

    private static List<IFeatureGenerator> features = new ArrayList();

    public static CoFHCompat instance = new CoFHCompat();
    
    public CoFHCompat()
    {
        super(ModList.cofhcore.toString());
    }

    @Override
    public void init()
    {
        try{
            Class<?> clazz = WorldHandler.class;
            Field cofhFeatures = clazz.getDeclaredField("features");
            cofhFeatures.setAccessible(true);
            Object result=cofhFeatures.get(null);
            features = (ArrayList) result;
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
