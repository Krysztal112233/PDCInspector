// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.listener;

import dev.krysztal.pinspector.Plugin;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

public class DebugListener implements Listener {

    private static final NamespacedKey DEBUG_NAMESPACED_KEY = Plugin.getPluginInstance().ofNamespacedKey("debug");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void insertToEntity(final EntitySpawnEvent event) {
        this.insert(event.getEntity());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void insertToPlayer(final PlayerJoinEvent event) {
        this.insert(event.getPlayer());
    }

    private void insert(final PersistentDataHolder pdh) {
        pdh.getPersistentDataContainer().set(DEBUG_NAMESPACED_KEY, PersistentDataType.STRING, "Hello, world!");
    }

}
