package com.iafenvoy.tametools.fabric;

import net.fabricmc.api.ModInitializer;

import com.iafenvoy.tametools.TameTools;

public final class TameToolsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        TameTools.init();
    }
}
