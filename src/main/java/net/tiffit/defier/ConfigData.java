package net.tiffit.defier;

import net.minecraftforge.common.config.Configuration;

public class ConfigData {

	public static int COMPRESSOR_ITEM_AMOUNT;
	public static int COMPRESSOR_RF_USAGE;
	public static boolean COMPRESSOR_EXPLODE;
	public static float COMPRESSOR_EXPLOSION_MULT;
	
	public static int ENERGYPROVIDER_MAX_STORAGE;
	public static float ENERGYPROVIDER_STORAGE_BASE;
	
	public static int MASSIVESTAR_SIZE;
	
	public static void load(Configuration config){
		COMPRESSOR_ITEM_AMOUNT = config.getInt("compressor.item_amount", "Balance", 2_000_000_000, 1, Integer.MAX_VALUE, "The amount of items needed to create a defier core.");
		COMPRESSOR_RF_USAGE = config.getInt("compressor.rf_usage", "Balance", 100_000, 1, Integer.MAX_VALUE, "The max amount of RF the compressor can use per tick.");
		COMPRESSOR_EXPLODE = config.getBoolean("compressor.explode", "Balance", true, "Should the compressor create an explosion when it runs out of RF?");
		COMPRESSOR_EXPLOSION_MULT = config.getFloat("compressor.explosion_multiplier", "Balance", 1f, 0, 5f, "The multiplier for the compressor explosion radius.");
		
		ENERGYPROVIDER_MAX_STORAGE = config.getInt("energyprovider.max_storage", "Balance", 1_000_000, 1, Integer.MAX_VALUE, "The max amount of RF an energy provider can hold. This is also the max transfer rate.");
		ENERGYPROVIDER_STORAGE_BASE = config.getFloat("energyprovider.storage_upgrade_base", "Balance", 10, 1, 20, "Energy Provider Max Storage = (energyprovider.max_storage)*(energyprovider.storage_upgrade_base^upgrades)");
		
		MASSIVESTAR_SIZE = config.getInt("massive_star_size", "Balance", 1_000_000, 1, Integer.MAX_VALUE, "How many items should the massive star count as?");

		
		config.save();
	}
	
}
