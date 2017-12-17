package net.tiffit.defier.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.tiffit.defier.Defier;
import net.tiffit.defier.tileentity.PatternMolderTileEntity;

public class PatternMolderBlock extends Block implements ITileEntityProvider {

	public PatternMolderBlock() {
		super(Material.IRON);
		setUnlocalizedName(Defier.MODID + ".patternmolder");
		setRegistryName("patternmolder");
		setHardness(3.0F);
		setResistance(5.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(Defier.CTAB);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new PatternMolderTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof PatternMolderTileEntity)) {
			return false;
		}
		player.openGui(Defier.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		PatternMolderTileEntity te = (PatternMolderTileEntity) worldIn.getTileEntity(pos);
		IItemHandler item = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		ItemStack slot1 = item.getStackInSlot(0);
		if (!slot1.isEmpty())InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), slot1);
		ItemStack slot2 = item.getStackInSlot(1);
		if (!slot2.isEmpty())InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), slot2);
		super.breakBlock(worldIn, pos, state);
	}

}
