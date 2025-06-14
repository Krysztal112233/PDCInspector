// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command.suggestion;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.krysztal.pinspector.util.ComponentUtil;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;

public enum InventorySuggestion implements SuggestionProvider<CommandSourceStack> {
    INSTANCE;

    public CompletableFuture<Suggestions> getSuggestions(
            final CommandContext<CommandSourceStack> context,
            final SuggestionsBuilder builder) throws CommandSyntaxException {

        final var optPlayer = Option.of(Bukkit.getPlayer(this.getPlayerName(context.getInput())));
        if (optPlayer.isEmpty()) {
            return builder.buildFuture();
        }

        return Stream.of(optPlayer.get().getInventory().getContents())
                .zipWithIndex()
                .filter(i -> Objects.nonNull(i._1))
                .map(i -> i
                        .map1(item -> Component
                                .text(item.getType().toString())
                                .decorate(TextDecoration.ITALIC)
                                .asComponent())
                        .map1(ComponentUtil::toMessage))
                .foldLeft(builder, (a, b) -> a.suggest(b._2, b._1))
                .buildFuture();
    }

    // Oh pretty dirty.
    private String getPlayerName(final String cmdInput) {
        return cmdInput.split(" ")[2];
    }

}
