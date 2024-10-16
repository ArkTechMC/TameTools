package com.iafenvoy.tametools.item;

import com.iafenvoy.tametools.registry.TTGameRules;
import com.iafenvoy.tametools.util.TameUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReleaseStoneItem extends TTItem {
    public ReleaseStoneItem() {
        super(new Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public ActionResult useOnEntity(ItemStack itemStack, PlayerEntity user, LivingEntity entity, Hand hand) {
        ItemStack stack=user.getStackInHand(hand);
        if (!user.getEntityWorld().getGameRules().getBoolean(TTGameRules.ENABLE_RELEASE_STONE)) {
            user.sendMessage(Text.translatable("item.tame_tools.tools.disabled"));
            return ActionResult.PASS;
        }
        if (entity instanceof MobEntity mob && stack.isOf(this)) {
            if (user.getEntityWorld().isClient) return ActionResult.SUCCESS;
            if (!TameUtil.isOwner(mob, user)) return ActionResult.FAIL;
            TameUtil.setOwner(mob, null);
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.tame_tools.release_stone.tooltip"));
    }
}
