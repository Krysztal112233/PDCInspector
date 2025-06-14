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
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public enum ItemStackInspector implements Inspector {
    INSTANCE;

    @SuppressWarnings("unchecked")
    @Override
    public List<Tuple2<String, PersistentDataContainerView>> getPersistentDataContainerView(
            final CommandContext<CommandSourceStack> context) {
        final var targetResolver = context.getArgument("arg", PlayerSelectorArgumentResolver.class);
        final var tryTarget = Try.of(() -> targetResolver.resolve(context.getSource()));
        if (tryTarget.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        final var slot = context.getArgument("slot", Integer.class);
        final var player = tryTarget.get().getFirst();

        final var optItem = Option.of(player.getInventory().getItem(slot));
        if (optItem.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return List.of(Tuple.of(MessageFormat.format("ITEM AT {0} OF {1}", slot, player.getName()),
                optItem.get().getPersistentDataContainer()));
    }
}
