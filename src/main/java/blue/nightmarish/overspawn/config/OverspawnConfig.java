package blue.nightmarish.overspawn.config;

import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class OverspawnConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> CREATURE_SPAWN_CAP;

    static {
        BUILDER.push("configuration for overspawn");
        CREATURE_SPAWN_CAP = BUILDER.comment("the maximum spawn cap. vanilla is 10 (i think)").defineInRange("Creature Spawn Cap", 10, 0, 1000); // dunno how to make it greater than 0 without a max.
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
    
}
