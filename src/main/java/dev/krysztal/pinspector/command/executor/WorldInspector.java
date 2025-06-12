// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command.executor;

import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.vavr.control.Option;
import org.bukkit.Bukkit;
import org.bukkit.World;

public enum WorldInspector implements Inspector {
    INSTANCE;

    @Override
    public Option<PersistentDataContainerView> getPersistentDataContainerView(
            CommandContext<CommandSourceStack> context) {
        var playerName = context.getArgument("name", String.class);

        return Option.of(Bukkit.getWorld(playerName)).map(World::getPersistentDataContainer);
    }

}
