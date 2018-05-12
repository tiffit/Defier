package net.tiffit.defier;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigData {

	public static int COMPRESSOR_ITEM_AMOUNT;
	public static int COMPRESSOR_RF_USAGE;
	public static boolean COMPRESSOR_EXPLODE;
	public static float COMPRESSOR_EXPLOSION_MULT;
	public static String[] COMPRESSOR_BLACKLIST;
	
	public static int ENERGYPROVIDER_MAX_STORAGE;
	public static float ENERGYPROVIDER_STORAGE_BASE;
	
	public static int ADVANCEDPROVIDER_MAX_STORAGE;
	public static float ADVANCEDPROVIDER_STORAGE_BASE;
	
	public static int MASSIVESTAR_SIZE;
	
	public static int MODIFIER_ATTACK_COST;
	public static int MODIFIER_ATTACK_RANGE;
	
	public static ConfigCategory _CATEGORY_BALANCE;
	
	public static void load(Configuration config){
		_CATEGORY_BALANCE = config.getCategory("Balance");
		COMPRESSOR_ITEM_AMOUNT = config.getInt("compressor.item_amount", _CATEGORY_BALANCE.getName(), 2_000_000_000, 1, Integer.MAX_VALUE, "The amount of items needed to create a defier core.");
		COMPRESSOR_RF_USAGE = config.getInt("compressor.rf_usage", _CATEGORY_BALANCE.getName(), 100_000, 1, Integer.MAX_VALUE, "The max amount of RF the compressor can use per tick.");
		COMPRESSOR_EXPLODE = config.getBoolean("compressor.explode", _CATEGORY_BALANCE.getName(), true, "Should the compressor create an explosion when it runs out of RF?");
		COMPRESSOR_EXPLOSION_MULT = config.getFloat("compressor.explosion_multiplier", _CATEGORY_BALANCE.getName(), 1f, 0, 5f, "The multiplier for the compressor explosion radius.");
		COMPRESSOR_BLACKLIST = config.getStringList("compressor.blacklist", _CATEGORY_BALANCE.getName(), new String[0], "Item blacklist for the compressor. Example for cobblestone: \"minecraft:cobblestone\"");
		
		ENERGYPROVIDER_MAX_STORAGE = config.getInt("energyprovider.max_storage", _CATEGORY_BALANCE.getName(), 1_000_000, 1, Integer.MAX_VALUE, "The max amount of RF an energy provider can hold. This is also the max transfer rate.");
		ENERGYPROVIDER_STORAGE_BASE = config.getFloat("energyprovider.storage_upgrade_base", _CATEGORY_BALANCE.getName(), 10, 1, 20, "Energy Provider Max Storage = (energyprovider.max_storage)*(energyprovider.storage_upgrade_base^upgrades)");
		
		ADVANCEDPROVIDER_MAX_STORAGE = config.getInt("advancedprovider.max_storage", _CATEGORY_BALANCE.getName(), 1_000_000_000, 1, Integer.MAX_VALUE, "The max amount of RF an advanced provider can hold. This is also the max transfer rate.");
		ADVANCEDPROVIDER_STORAGE_BASE = config.getFloat("advancedprovider.storage_upgrade_base", _CATEGORY_BALANCE.getName(), 10, 1, 20, "Advanced Provider Max Storage = (advancedprovider.max_storage)*(advancedprovider.storage_upgrade_base^upgrades)");
		
		MASSIVESTAR_SIZE = config.getInt("massive_star_size", _CATEGORY_BALANCE.getName(), 1_000_000, 1, Integer.MAX_VALUE, "How many items should the massive star count as?");
		
		MODIFIER_ATTACK_COST = config.getInt("modifier.attack.rfcost", _CATEGORY_BALANCE.getName(), 100_000, 1, Integer.MAX_VALUE, "How much RF should the attack modifier cost?");
		MODIFIER_ATTACK_RANGE = config.getInt("modifier.attack.range", _CATEGORY_BALANCE.getName(), 5, 1, 20, "What is the max range of the attack modifier?");

		
		config.save();
	}
	
	public static boolean isCompressorBlacklisted(ItemStack stack){
		for(String blacklist : COMPRESSOR_BLACKLIST){
			if(Item.getByNameOrId(blacklist) == stack.getItem()){
				return true;
			}
		}
		return false;
	}
	
}
