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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import one.util.streamex.StreamEx;
import org.bukkit.Location;
import org.bukkit.World;

public class EntitySuggestion implements SuggestionProvider<CommandSourceStack> {

    public List<?> collect(World world) {
        return this.collect(new Location(world, 0, 0, 0));
    }

    public List<?> collect(Location location) {
        StreamEx.of(location.getWorld().getEntities()).parallel();

        return null;
    }

    @Override
    public CompletableFuture<Suggestions> getSuggestions(
            CommandContext<CommandSourceStack> context,
            SuggestionsBuilder builder) throws CommandSyntaxException {

        context.getSource().getLocation();

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSuggestions'");
    }
}
