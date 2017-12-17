package net.tiffit.defier.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tiffit.defier.Defier;
import net.tiffit.defier.tileentity.CompressorTileEntity;

public class CompressorBlock extends Block implements ITileEntityProvider {

	public CompressorBlock() {
		super(Material.IRON);
		setUnlocalizedName(Defier.MODID + ".compressor");
		setRegistryName("compressor");
		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(Defier.CTAB);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new CompressorTileEntity();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		TileEntity te = world.getTileEntity(pos);
		if (!(te instanceof CompressorTileEntity)) {
			return false;
		}
		player.openGui(Defier.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
}
