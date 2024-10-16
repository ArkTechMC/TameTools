package com.iafenvoy.tametools.fabric.client;

import com.iafenvoy.tametools.TameToolsClient;
import net.fabricmc.api.ClientModInitializer;

public final class TameToolsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        TameToolsClient.process();
    }
}
