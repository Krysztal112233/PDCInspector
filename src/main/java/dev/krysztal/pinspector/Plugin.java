// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector;

import dev.krysztal.pinspector.command.InspectorCommand;
import dev.krysztal.pinspector.listener.DebugListener;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    @Getter
    private static Plugin pluginInstance = null;

    public NamespacedKey ofNamespacedKey(final String key) {
        return new NamespacedKey(this, key);
    }

    @Override
    public void onLoad() {
        pluginInstance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new DebugListener(), this);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, cmd -> {
            cmd.registrar().register(InspectorCommand.createCommand().build());
        });
    }
}
