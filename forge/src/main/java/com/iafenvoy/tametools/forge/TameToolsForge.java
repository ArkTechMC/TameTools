package com.iafenvoy.tametools.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import com.iafenvoy.tametools.TameTools;

@Mod(TameTools.MOD_ID)
public final class TameToolsForge {
    public TameToolsForge() {
        EventBuses.registerModEventBus(TameTools.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        TameTools.init();
    }
}
