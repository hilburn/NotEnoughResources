package neiresources.compatibility.cofh;

import cofh.api.world.IFeatureGenerator;
import cofh.core.world.WorldHandler;
import cofh.lib.world.feature.FeatureGenNormal;
import cofh.lib.world.feature.FeatureGenSurface;
import cofh.lib.world.feature.FeatureGenUniform;
import neiresources.compatibility.CompatBase;
import neiresources.utils.ModList;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CoFHCompat extends CompatBase
{

    private static List<IFeatureGenerator> features = new ArrayList();

    public static CoFHCompat instance = new CoFHCompat();

    public CoFHCompat()
    {
        super(ModList.cofhcore.toString());
    }

    @Override
    public void init()
    {
        try
        {
            Class<?> clazz = WorldHandler.class;
            Field cofhFeatures = clazz.getDeclaredField("features");
            cofhFeatures.setAccessible(true);
            Object result = cofhFeatures.get(null);
            features = (ArrayList) result;
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        registerOres();
    }

    private void registerOres()
    {
        for (IFeatureGenerator feature : features)
        {
            if (feature instanceof FeatureGenUniform)
            {
                FeatureGenUniform val = (FeatureGenUniform) feature;
                Class clazz = FeatureGenUniform.class;
                try
                {
                    Field maxY = clazz.getDeclaredField("maxY");
                    System.out.println(maxY.get(val));
                } catch (NoSuchFieldException e)
                {
                    e.printStackTrace();
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            } else if (feature instanceof FeatureGenNormal)
            {

            } else if (feature instanceof FeatureGenSurface)
            {

            }
        }
    }
}
