package com.iafenvoy.tametools.util;

import com.iafenvoy.tameable.data.EntityTameData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class TameUtil {
    public static boolean hasOwner(MobEntity mob) {
        return (mob instanceof TameableEntity tameable ? tameable.getOwnerUuid() : EntityTameData.get(mob).getOwner()) != null;
    }

    public static boolean isOwner(MobEntity mob, PlayerEntity player) {
        return Objects.equals(mob instanceof TameableEntity tameable ? tameable.getOwnerUuid() : EntityTameData.get(mob).getOwner(), player.getUuid());
    }

    public static void setOwner(MobEntity mob, @Nullable UUID owner) {
        if (mob instanceof TameableEntity tameable) {
            tameable.setTamed(owner != null);
            tameable.setOwnerUuid(owner);
        } else EntityTameData.get(mob).setOwner(owner);
    }
}
