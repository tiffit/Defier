package net.tiffit.defier.block;

import java.util.List;

import codechicken.lib.render.item.IItemRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.tiffit.defier.Defier;
import net.tiffit.defier.DefierItems;
import net.tiffit.defier.client.render.RenderEnergyProviderItem;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity;
import net.tiffit.defier.tileentity.EnergyProviderTileEntity.ProviderColor;
import net.tiffit.tiffitlib.RegistryHelper.ISpecialItemRender;

public class EnergyProviderBlock extends Block implements ITileEntityProvider, ISpecialItemRender{

	protected static final AxisAlignedBB BASE_AABB = new AxisAlignedBB(0D, 0D, 0D, 1D, 2 / 16D, 1D);
	protected static final AxisAlignedBB POLE_AABB = new AxisAlignedBB(7 / 16D, 0 / 16D, 7 / 16D, 9 / 16D, 12 / 16D, 9 / 16D);

	public EnergyProviderBlock() {
		super(Material.IRON);
		setUnlocalizedName(Defier.MODID + ".energyprovider");
		setRegistryName("energyprovider");
		setHardness(50.0F);
		setResistance(3000.0F);
		setSoundType(SoundType.METAL);
		setHarvestLevel("pickaxe", 2);
		setCreativeTab(Defier.CTAB);
	}

	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
		AxisAlignedBB basebb = BASE_AABB.offset(pos);

		if (entityBox.intersects(basebb)) {
			collidingBoxes.add(basebb);
		}
		AxisAlignedBB polebb = POLE_AABB.offset(pos);

		if (entityBox.intersects(polebb)) {
			collidingBoxes.add(polebb);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new EnergyProviderTileEntity();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		ItemStack current = player.inventory.getCurrentItem();
		EnergyProviderTileEntity te = (EnergyProviderTileEntity) world.getTileEntity(pos);
		if (!current.isEmpty()) {
			if (te.getSpeedUpgrades() < 8 && current.getItem() == DefierItems.speedstar) {
				te.addSpeedUpgrade();
				current.setCount(current.getCount() - 1);
				player.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1f, te.getSpeedUpgrades()/8f);
			}
			if (te.getStorageUpgrades() < 4 && current.getItem() == DefierItems.energystar && te.getStorageUpgrades() == current.getMetadata()) {
				te.addStorageUpgrade();
				current.setCount(current.getCount() - 1);
				player.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 1f, te.getStorageUpgrades()/4f);
			}
			ProviderColor color = ProviderColor.getDye(current);
			if (color != null) {
				te.setColor(color);
				player.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CLOTH_BREAK, SoundCategory.BLOCKS, 1f, 1f);
			}
		}
		return true;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		EnergyProviderTileEntity te = (EnergyProviderTileEntity) worldIn.getTileEntity(pos);
		ItemStack upgrade = new ItemStack(DefierItems.speedstar, te.getSpeedUpgrades());
		if (!upgrade.isEmpty())
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), upgrade);
		for (int i = 0; i < te.getStorageUpgrades(); i++) {
			InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(DefierItems.energystar, 1, i));
		}
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public IItemRenderer getItemRender() {
		return new RenderEnergyProviderItem();
	}

}
