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
import io.papermc.paper.command.brigadier.CommandSourceStack;
import java.util.concurrent.CompletableFuture;
import one.util.streamex.StreamEx;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public enum PlayerSuggestion implements SuggestionProvider<CommandSourceStack> {
    INSTANCE;

    @Override
    public CompletableFuture<Suggestions> getSuggestions(
            final CommandContext<CommandSourceStack> context,
            final SuggestionsBuilder builder) throws CommandSyntaxException {

        return StreamEx.of(Bukkit.getOnlinePlayers())
                .parallel()
                .map(Player::getName)
                .foldLeft(builder, SuggestionsBuilder::suggest)
                .buildFuture();
    }
}
