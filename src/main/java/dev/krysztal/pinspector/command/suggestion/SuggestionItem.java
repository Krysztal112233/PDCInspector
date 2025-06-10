// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command.suggestion;

import com.mojang.brigadier.Message;
import java.util.function.Function;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.LivingEntity;

public record SuggestionItem(String identifier, SuggestionItemType type, Component extra) {
    enum SuggestionItemType {
        Player, LiveingEntity, Entity, Chunk
    }

    public Message toMessage() {
        return null;
    }

    public static SuggestionItem of(LivingEntity entity) {
        Function<LivingEntity, Component> map = (it) -> {
            return Component.empty()
                    .append()
                    .appendNewline();
        };

        return new SuggestionItem(
                entity.getType().getKey().toString(),
                SuggestionItemType.LiveingEntity,
                Component.empty());
    }

}
