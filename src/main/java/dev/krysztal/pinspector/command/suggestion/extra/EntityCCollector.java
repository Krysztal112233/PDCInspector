// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.command.suggestion.extra;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.Message;
import java.util.List;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Entity;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class EntityCCollector extends CCollectior {

    @Getter
    private final Entity entity;

    protected static final List<Function<Entity, ComponentLike>> LIST = ImmutableList
            .of(EntityCCollector::location,
                    EntityCCollector::nickname,
                    EntityCCollector::uuid);

    private static ComponentLike location(final Entity entity) {
        return CCollectior.kv("Location", entity.getLocation().toString());
    }

    private static ComponentLike nickname(final Entity entity) {
        return CCollectior.kv("Nickname", entity.getName());
    }

    private static ComponentLike uuid(Entity entity) {
        return CCollectior.kv("UUID", entity.getUniqueId().toString());
    }

    @Override
    public Message toMessage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toMessage'");
    }

}
