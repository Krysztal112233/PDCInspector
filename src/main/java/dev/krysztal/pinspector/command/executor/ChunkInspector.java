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
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public enum ChunkInspector implements Inspector {
    INSTANCE;

    @Override
    public Option<PersistentDataContainerView> getPersistentDataContainerView(
            final CommandContext<CommandSourceStack> context) {
        final var world = context.getArgument("world", String.class);
        final var tryPos = Try
                .of(() -> context.getArgument("pos", BlockPositionResolver.class).resolve(context.getSource()));

        if (tryPos.isFailure()) {
            return Option.none();
        }

        final var pos = tryPos.get();

        return Option.of(Bukkit.getWorld(world))
                .map(w -> w.getChunkAt(pos.toLocation(w)))
                .map(Chunk::getPersistentDataContainer);
    }

}
