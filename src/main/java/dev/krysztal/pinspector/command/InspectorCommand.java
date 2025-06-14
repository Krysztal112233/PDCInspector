// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.krysztal.pinspector.PluginPermission;
import dev.krysztal.pinspector.command.executor.ChunkInspector;
import dev.krysztal.pinspector.command.executor.EntityInspector;
import dev.krysztal.pinspector.command.executor.WorldInspector;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;

public class InspectorCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> createCommand() {
        return Commands
                .literal("pinspect")
                .requires(s -> s.getSender().hasPermission(PluginPermission.PDC_DEBUGGER))
                .then(Commands
                        .literal("world")
                        .then(Commands
                                .argument("world", ArgumentTypes.world())
                                .executes(WorldInspector.INSTANCE)))
                .then(Commands
                        .literal("chunk")
                        .then(Commands
                                .argument("world", ArgumentTypes.world())
                                .then(Commands.argument("pos", ArgumentTypes.blockPosition())
                                        .executes(ChunkInspector.INSTANCE))))
                .then(Commands
                        .literal("entity")
                        .then(Commands
                                .argument("args", ArgumentTypes.entity())
                                .executes(EntityInspector.INSTANCE)));
    }
}
