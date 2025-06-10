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
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;

public class ChunkSuggestion implements SuggestionProvider {

    @Override
    public CompletableFuture getSuggestions(CommandContext context, SuggestionsBuilder builder)
            throws CommandSyntaxException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSuggestions'");
    }

}
