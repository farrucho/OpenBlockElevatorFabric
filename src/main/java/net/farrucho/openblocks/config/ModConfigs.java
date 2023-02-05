package net.farrucho.openblocks.config;

import com.mojang.datafixers.util.Pair;
import net.farrucho.openblocks.OpenBlocks;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    //public static String TEST;
    //public static int SOME_INT;
    //public static double SOME_DOUBLE;
    //public static int MAX_DAMAGE_DOWSING_ROD;

    public static int ELEVATOR_BLOCK_RANGE;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(OpenBlocks.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        // se a config nao existe cria config
        //configs.addKeyValuePair(new Pair<>("key.test.value1", "Just a Testing string!"), "String");
        //configs.addKeyValuePair(new Pair<>("key.test.value2", 50), "int");
        //configs.addKeyValuePair(new Pair<>("key.test.value3", 4142.5), "double");
        //configs.addKeyValuePair(new Pair<>("dowsing.rod.max.damage", 32), "int");
        configs.addKeyValuePair(new Pair<>("openblocks.elevator.block.range", 20), "int");
    }

    private static void assignConfigs() {
        // obtem valores escritos na config
        //TEST = CONFIG.getOrDefault("key.test.value1", "Nothing");
        //SOME_INT = CONFIG.getOrDefault("key.test.value2", 42);
        //SOME_DOUBLE = CONFIG.getOrDefault("key.test.value3", 42.0d);
        //MAX_DAMAGE_DOWSING_ROD = CONFIG.getOrDefault("dowsing.rod.max.damage", 32);
        ELEVATOR_BLOCK_RANGE = CONFIG.getOrDefault("openblocks.elevator.block.range", 20);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
