package net.tiffit.defier.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.tiffit.defier.DefierRecipe;
import net.tiffit.defier.DefierRecipeRegistry;
import net.tiffit.tiffitlib.TiffitLib;

public class PatternItem extends DefierItem {
	
	public PatternItem() {
		super("pattern");
		setMaxStackSize(1);
		this.addPropertyOverride(new ResourceLocation("defieritem"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("defieritem"))return 0f;
                return 1f;
            }
        });
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		 if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("defieritem")){
			 tooltip.add("Blank Pattern");
		 }else{
			 ItemStack stored = new ItemStack(stack.getTagCompound().getCompoundTag("defieritem"));
			 DefierRecipe recipe = DefierRecipeRegistry.findRecipeForStack(stored);
			 if(recipe == null){
				 tooltip.add(TextFormatting.RED + "Unknown Recipe");
			 }else{
				 stored.clearCustomName();
				 tooltip.add("Item: " + stored.getDisplayName());
				 tooltip.add("Energy Cost: " + TiffitLib.LARGE_NUMBER.format(recipe.getCost()) + "RF");
			 }
		 }
	}
	
}
