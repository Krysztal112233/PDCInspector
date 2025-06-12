// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.krysztal.pinspector.PluginPermission;
import dev.krysztal.pinspector.command.executor.PlayerInspector;
import dev.krysztal.pinspector.command.executor.WorldInspector;
import dev.krysztal.pinspector.command.suggestion.PlayerSuggestion;
import dev.krysztal.pinspector.command.suggestion.WorldSuggestion;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public class InspectorCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands
                .literal("pinspect")
                .requires(s -> s.getSender().hasPermission(PluginPermission.PDC_DEBUGGER))
                // .then(Commands.literal("chunk"))
                .then(Commands
                        .literal("world")
                        .then(Commands
                                .argument("name", StringArgumentType.word())
                                .suggests(WorldSuggestion.INSTANCE)
                                .executes(WorldInspector.INSTANCE)))
                .then(Commands
                        .literal("player")
                        .then(Commands
                                .argument("name", StringArgumentType.word())
                                .suggests(PlayerSuggestion.INSTANCE)
                                .executes(PlayerInspector.INSTANCE)));
    }
}
