package neresources.compatibility.mobproperties;

import neresources.api.utils.DropItem;
import neresources.api.utils.conditionals.Conditional;
import neresources.api.utils.conditionals.ExtendedConditional;
import neresources.registry.ChangeMobDrop;
import neresources.utils.TranslationHelper;
import net.minecraft.block.Block;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StringParser
{
    private static Map<String,Conditional> conditionalMap = new LinkedHashMap<String, Conditional>();

    StringParser()
    {
        conditionalMap.put("if_burning", Conditional.burning);
        conditionalMap.put("if_wet",Conditional.wet);
        conditionalMap.put("if_has_potion_",Conditional.hasPotion);
        conditionalMap.put("if_beyond_",Conditional.beyond);
        conditionalMap.put("if_raining",Conditional.raining);
        conditionalMap.put("if_thundering",Conditional.thundering);
        conditionalMap.put("if_moon_phase_",Conditional.moonPhase);
        conditionalMap.put("if_past_day_time_",Conditional.pastTime);
        conditionalMap.put("if_past_world_time_",Conditional.pastWorldTime);
        conditionalMap.put("if_past_world_difficulty_",Conditional.pastWorldDifficulty);
        conditionalMap.put("if_difficulty_",Conditional.gameDifficulty);
        conditionalMap.put("if_in_dimension_",Conditional.inDimension);
        conditionalMap.put("if_in_biome_",Conditional.inBiome);
        conditionalMap.put("if_on_block_", Conditional.onBlock);
        conditionalMap.put("if_below_",Conditional.below);
        conditionalMap.put("if_player_online_",Conditional.playerOnline);
        conditionalMap.put("if_recently_hit", Conditional.playerKill);
        conditionalMap.put("if_above_looting_", Conditional.aboveLooting);
        conditionalMap.put("if_killed_by_",Conditional.killedBy);
    }
    public static final String witherString = "if_wither_skeleton";

    public static ChangeMobDrop addConditionals(ChangeMobDrop mobDrop, List<String> conditions)
    {
        List<String> addConditions = new ArrayList<String>();
        for (String condition:conditions)
        {
            if (condition.equals(witherString)) mobDrop.setWitherSkeleton(true);
            Conditional conditional = stringToConditional(condition);
            if (conditional!=null)
                addConditions.add(conditional.toString());
        }
        if (!addConditions.isEmpty())
            for (DropItem item: mobDrop.addItems())
                item.addConditionals(addConditions);

        return mobDrop;
    }

    private static Conditional stringToConditional(String condition)
    {
        condition = condition.trim();
        Conditional result = null;
        boolean reverse = condition.startsWith("!");
        boolean moon = false, block = false, biome = false, gameDifficulty = false,worldDifficulty = false;
        if (reverse) condition = condition.replace("!","");
        for (String key:conditionalMap.keySet())
        {
            if (condition.startsWith(key))
            {
                result = conditionalMap.get(key);
                if (result == Conditional.moonPhase) moon = true;
                else if(result == Conditional.onBlock) block = true;
                else if(result == Conditional.inBiome) biome = true;
                else if(result == Conditional.gameDifficulty) gameDifficulty=true;
                else if(result == Conditional.beforeWorldDifficulty) return null; //worldDifficulty=true;
                if (reverse) result = Conditional.reverse.get(result);
                condition = condition.replace(key,"");
            }
        }
        if (result == null) return null;
        if (condition.length()>0)
        {
            if (moon) condition = TranslationHelper.translateToLocal("ner."+condition+".moon");
            else if (block)
            {
                Block thisBlock = Block.getBlockById(Integer.valueOf(condition));
                if (thisBlock==null) return null;
                condition = thisBlock.getLocalizedName();
            }
            else if(biome)
            {
                BiomeGenBase biomeGenBase = BiomeGenBase.getBiome(Integer.valueOf(condition));
                if (biomeGenBase==null) return null;
                condition = biomeGenBase.biomeName;
            }
            else if(gameDifficulty)
            {
                int val = Integer.valueOf(condition);
                if (val<0||val>3) return null;
                condition = TranslationHelper.translateToLocal(EnumDifficulty.getDifficultyEnum(val).getDifficultyResourceKey());
            }
            result = new ExtendedConditional(result,condition);
        }
        return result;
    }
}
