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
import io.vavr.control.Option;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ScopedComponent;
import net.kyori.adventure.text.format.TextDecoration;
import one.util.streamex.StreamEx;

public interface Inspector extends Command<CommandSourceStack> {
    Option<PersistentDataContainerView> getPersistentDataContainerView(CommandContext<CommandSourceStack> context);

    default int run(final CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final var optPDC = this.getPersistentDataContainerView(context);
        if (optPDC.isEmpty()) {
            return -1;
        }

        final var result = StreamEx.of(PDCInspector.of(optPDC.get()).consume())
                .parallel()
                .map(Contained::toAdventureComponent)
                .map(Component::appendNewline)
                .map(Component::asComponent)
                .foldLeft(Component.empty(), ScopedComponent::append);

        final var sender = context.getSource().getSender();

        sender.sendMessage(Component.text(" ==== INSPECTION ==== ")
                .decorate(TextDecoration.UNDERLINED)
                .decorate(TextDecoration.ITALIC));
        sender.sendMessage(result);
        sender.sendMessage(Component
                .text(" ==== INSPECTION ==== ")
                .decorate(TextDecoration.UNDERLINED)
                .decorate(TextDecoration.ITALIC));

        return 0;
    }
}
