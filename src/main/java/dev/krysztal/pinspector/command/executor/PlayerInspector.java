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
import io.vavr.control.Option;
import java.text.MessageFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ScopedComponent;
import net.kyori.adventure.text.format.TextDecoration;
import one.util.streamex.StreamEx;
import org.bukkit.Bukkit;

public enum PlayerInspector implements Command<CommandSourceStack> {
    INSTANCE;

    @Override
    public int run(final CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        var playerName = context.getArgument("name", String.class);
        final var optPlayer = Option.of(Bukkit.getPlayer(playerName));
        if (!optPlayer.isDefined()) {
            return -1;
        }

        final var player = optPlayer.get();

        final var result = StreamEx.of(PDCInspector.of(player).consume())
                .parallel()
                .map(Contained::toAdventureComponent)
                .map(Component::appendNewline)
                .map(Component::asComponent)
                .foldLeft(Component.empty(), ScopedComponent::append);

        player.sendMessage(Component
                .text(MessageFormat.format(" ==== INSPECTION OF PLAYER: {0} START", playerName))
                .decorate(TextDecoration.UNDERLINED)
                .decorate(TextDecoration.ITALIC));
        player.sendMessage(result);
        player.sendMessage(Component
                .text(MessageFormat.format(" ==== INSPECTION OF PLAYER: {0} END", playerName))
                .decorate(TextDecoration.UNDERLINED)
                .decorate(TextDecoration.ITALIC));

        return 0;
    }

}
