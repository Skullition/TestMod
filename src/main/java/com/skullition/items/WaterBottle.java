package com.skullition.items;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class WaterBottle extends PickaxeItem {

    public WaterBottle(Properties properties) {
        super(Tiers.NETHERITE, 1, -2.8F, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(new TranslatableComponent("tooltip.waterbottle").withStyle(ChatFormatting.BLUE));
        int distance = getDistance(itemStack);
        components.add(new TranslatableComponent("chargelevel.waterbottle", Integer.toString(distance)).withStyle(ChatFormatting.RED));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        int distance = itemStack.getOrCreateTag().getInt("distance");
        distance++;
        if (distance >= 4) {
            distance = 0;
        }
        itemStack.getTag().putInt("distance", distance);

        if (level.isClientSide()) {
            player.sendMessage(new TranslatableComponent("increaseamount.waterbottle", Integer.toString(distance)), Util.NIL_UUID);
        }
        return super.use(level, player, interactionHand);
    }

    public int getDistance(ItemStack itemStack) {
        return itemStack.hasTag() ? itemStack.getTag().getInt("distance") : 0;
    }

    @Override
    public boolean mineBlock(ItemStack itemStack, Level level, BlockState blockState, BlockPos blockPos, LivingEntity livingEntity) {
        boolean output = super.mineBlock(itemStack, level, blockState, blockPos, livingEntity);
        if (output) {
            int distance = getDistance(itemStack);
            if (distance > 0) {
                CompoundTag tag = itemStack.getOrCreateTag();
                boolean mining = tag.getBoolean("mining");
                if (!mining) {
                    BlockHitResult hit = trace(level, livingEntity);
                    if (hit.getType() == HitResult.Type.BLOCK) {
                        tag.putBoolean("mining", true);
                        for (int i = 0; i < distance; i++) {
                            BlockPos relative = blockPos.relative(hit.getDirection().getOpposite(), i + 1);
                            if (!tryHarvest(itemStack, livingEntity, relative)) {
                                tag.putBoolean("mining", false);
                                return output;
                            }
                        }
                        tag.putBoolean("mining", false);
                    }
                }
            }
        }
        return output;
    }

    private boolean tryHarvest(ItemStack stack, LivingEntity entityLiving, BlockPos pos) {
        BlockState state = entityLiving.level.getBlockState(pos);
        if (isCorrectToolForDrops(stack, state)) {
            if (entityLiving instanceof ServerPlayer player) {
                return player.gameMode.destroyBlock(pos);
            }
        }
        return false;
    }

    private BlockHitResult trace(Level level, LivingEntity player) {
        double reach = player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue();
        Vec3 eye = player.getEyePosition(1.0f);
        Vec3 view = player.getViewVector(1.0f);
        Vec3 withReach = eye.add(view.x * reach, view.y * reach, view.z * reach);
        return level.clip(new ClipContext(eye, withReach, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }
}
