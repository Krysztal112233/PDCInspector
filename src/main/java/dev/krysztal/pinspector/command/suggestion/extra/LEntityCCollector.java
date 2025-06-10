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
import java.util.List;
import java.util.function.Function;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class LEntityCCollector {
    protected static final List<Function<LivingEntity, ComponentLike>> LIST = ImmutableList
            .<Function<LivingEntity, ComponentLike>>builder()
            .addAll(EntityCCollector.LIST
                    .stream()
                    .<Function<LivingEntity, ComponentLike>>map(f -> entity -> f.apply((Entity) entity)).iterator())
            .add(LEntityCCollector::health)
            .build();

    private static ComponentLike health(LivingEntity entity) {
        final String format = String.format("%.1f/%.1f", entity.getHealth(), entity.getAttribute(Attribute.MAX_HEALTH));

        return CCollectior.kv("Health", format);
    }
}
