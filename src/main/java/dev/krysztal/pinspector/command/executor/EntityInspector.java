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
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Try;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import one.util.streamex.StreamEx;

public enum EntityInspector implements Inspector {
    INSTANCE;

    @Override
    public List<Tuple2<String, PersistentDataContainerView>> getPersistentDataContainerView(
            final CommandContext<CommandSourceStack> context) {
        final var entitySelectorArgumentResolver = context.getArgument("args", EntitySelectorArgumentResolver.class);
        final var tryEntities = Try.of(() -> entitySelectorArgumentResolver.resolve(context.getSource()));
        if (tryEntities.isFailure()) {
            return Collections.EMPTY_LIST;
        }

        var entities = tryEntities.get();

        return StreamEx.of(entities).map(e -> {
            var identifier = MessageFormat.format("{0} at ({1}, {2}, {3})",
                    e.getName(),
                    e.getLocation().getX(),
                    e.getLocation().getY(),
                    e.getLocation().getZ());
            return Tuple.of(identifier, (PersistentDataContainerView) e.getPersistentDataContainer());
        }).toList();
    }
}
