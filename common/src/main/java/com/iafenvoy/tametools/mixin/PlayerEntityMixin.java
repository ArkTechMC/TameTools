package com.iafenvoy.tametools.mixin;

import com.iafenvoy.tameable.data.EntityTameData;
import com.iafenvoy.tametools.registry.TTGameRules;
import com.iafenvoy.tametools.util.TameUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "interact", at = @At("TAIL"))
    private void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!entity.getEntityWorld().getGameRules().getBoolean(TTGameRules.ENABLE_LEASH_TRANSFER)) return;
        if (!(entity.isPlayer() && hand.equals(Hand.MAIN_HAND))) return;
        PlayerEntity target = (PlayerEntity) entity;
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!player.isSneaking()) return;
        World world = player.getWorld();
        world.getEntitiesByClass(MobEntity.class, Box.of(player.getPos(), 12, 12, 12), mob -> mob.isLeashed() && mob.getHoldingEntity() == player && Objects.equals(EntityTameData.get(mob).getOwner(), player.getUuid())).forEach(mob -> {
            mob.detachLeash(true, false);
            mob.attachLeash(target, true);
            world.sendEntityStatus(mob, (byte) 7);
            TameUtil.setOwner(mob, target.getUuid());
            player.sendMessage(Text.translatable("action.tame_tools.transfer.from", target.getDisplayName(), mob.getDisplayName()));
            target.sendMessage(Text.translatable("action.tame_tools.transfer.to", player.getDisplayName(), mob.getDisplayName()));
        });
    }
}
