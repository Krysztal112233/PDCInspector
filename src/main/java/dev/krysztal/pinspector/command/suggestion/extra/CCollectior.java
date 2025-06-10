// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command.suggestion.extra;

import com.mojang.brigadier.Message;
import java.text.MessageFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Entity;

public abstract sealed class CCollectior permits EntityCCollector {

    abstract public Message toMessage();

    protected static ComponentLike kv(String key, String value) {
        return Component.empty()
                .append(Component.text(MessageFormat.format("{}:", key), NamedTextColor.AQUA))
                .appendSpace()
                .append(Component.text(value, NamedTextColor.GREEN));
    }

    public static CCollectior of(Entity entity) {
        return new EntityCCollector(entity);
    }
}
