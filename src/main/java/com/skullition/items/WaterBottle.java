package com.skullition.items;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class WaterBottle extends Item {

    public WaterBottle(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(new TranslatableComponent("tooltip.waterbottle").withStyle(ChatFormatting.BLUE));
        int distance = itemStack.hasTag() ? itemStack.getTag().getInt("distance") : 0;
        components.add(new TranslatableComponent("chargelevel.waterbottle", Integer.toString(distance)).withStyle(ChatFormatting.RED));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        int distance = itemStack.getOrCreateTag().getInt("distance");
        distance++;
        itemStack.getTag().putInt("distance", distance);

        if (level.isClientSide()) {
            player.sendMessage(new TranslatableComponent("increaseamount.waterbottle", Integer.toString(distance)), Util.NIL_UUID);
        }
        return super.use(level, player, interactionHand);
    }
}
