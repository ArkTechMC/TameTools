package com.iafenvoy.tametools;

import com.iafenvoy.tametools.item.StoreStoneItem;
import com.iafenvoy.tametools.registry.TTItems;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.util.Identifier;

public class TameToolsClient {
    public static void process() {
        ItemPropertiesRegistry.register(TTItems.STORE_STONE.get(), new Identifier(TameTools.MOD_ID,"stored"), (stack, world, entity, seed) -> stack.getOrCreateNbt().contains(StoreStoneItem.SAVE_ENTITY_KEY) ? 1 : 0);
    }
}
