package com.iafenvoy.tametools;

import com.iafenvoy.tameable.event.TamableInteractionCallback;
import com.iafenvoy.tametools.item.TameStaffItem;
import com.iafenvoy.tametools.registry.TTGameRules;
import com.iafenvoy.tametools.registry.TTItemGroups;
import com.iafenvoy.tametools.registry.TTItems;

public final class TameTools {
    public static final String MOD_ID = "tame_tools";

    public static void init() {
        TTGameRules.init();
        TTItems.REGISTRY.register();
        TTItemGroups.REGISTRY.register();
        TamableInteractionCallback.EVENT.register((mob, player, hand) -> player.getStackInHand(hand).isEmpty() || player.getStackInHand(hand).getItem() instanceof TameStaffItem);
    }
}
