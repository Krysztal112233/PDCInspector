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
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.List;
import java.util.stream.Stream;
import org.bukkit.World;

public enum WorldInspector implements Inspector {
    INSTANCE;

    @Override
    public List<Tuple2<String, PersistentDataContainerView>> getPersistentDataContainerView(
            CommandContext<CommandSourceStack> context) {

        var world = context.getArgument("world", World.class);

        return Stream.of(world)
                .map(c -> Tuple.of(c.getName(), ((PersistentDataContainerView) c.getPersistentDataContainer())))
                .toList();
    }

}
