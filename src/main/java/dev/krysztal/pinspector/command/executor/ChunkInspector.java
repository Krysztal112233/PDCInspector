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
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import org.bukkit.World;

public enum ChunkInspector implements Inspector {
    INSTANCE;

    @Override
    public List<Tuple2<String, PersistentDataContainerView>> getPersistentDataContainerView(
            final CommandContext<CommandSourceStack> context) {
        final var world = context.getArgument("world", World.class);
        final var tryPos = Try.of(() -> context
                .getArgument("pos", BlockPositionResolver.class)
                .resolve(context.getSource()));

        if (tryPos.isFailure()) {
            return Collections.EMPTY_LIST;
        }

        final var pos = tryPos.get();
        final var chunk = world.getChunkAt(pos.toLocation(world));

        return List.of(
                Tuple.of(MessageFormat.format("CHUNK AT ({0}, {1}, {2})",
                        pos.x(), pos.y(), pos.z()),
                        chunk.getPersistentDataContainer()));
    }
}
