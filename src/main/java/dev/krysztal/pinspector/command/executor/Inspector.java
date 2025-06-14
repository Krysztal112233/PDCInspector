// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command.executor;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.krysztal.pinspector.inspector.PDCInspector;
import dev.krysztal.pinspector.inspector.typed.Contained;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.vavr.Tuple2;
import java.text.MessageFormat;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.ScopedComponent;
import net.kyori.adventure.text.format.TextDecoration;
import one.util.streamex.StreamEx;

public interface Inspector extends Command<CommandSourceStack> {
    List<Tuple2<String, PersistentDataContainerView>> getPersistentDataContainerView(
            CommandContext<CommandSourceStack> context);

    default int run(final CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final var result = StreamEx.of(this.getPersistentDataContainerView(context))
                .parallel()
                .map(m -> {
                    var inner = StreamEx.of(PDCInspector.of(m._2).consume())
                            .map(Contained::toAdventureComponent)
                            .map(ComponentLike::asComponent)
                            .foldLeft(Component.empty(), ScopedComponent::append);

                    return Component.text(MessageFormat.format(" ==== INSPECTION OF {0} ==== ", m._1))
                            .decorate(TextDecoration.UNDERLINED)
                            .decorate(TextDecoration.ITALIC).appendNewline().appendNewline()
                            .append(inner)
                            .asComponent();
                })
                .foldLeft(Component.empty(), ScopedComponent::append);

        context.getSource().getSender().sendMessage(result);

        return 0;
    }
}
