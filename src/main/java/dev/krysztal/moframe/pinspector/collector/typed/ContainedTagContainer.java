// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.pinspector.collector.typed;

import dev.krysztal.moframe.pinspector.collector.ContainerRemapper;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;

@AllArgsConstructor()
public final class ContainedTagContainer extends Contained<List<Contained<?>>> {
    protected ContainedTagContainer(NamespacedKey key, PersistentDataContainer container) {
        this.value = ContainerRemapper.of(container).consume();
        this.key = key;
    }

    @Getter
    private final NamespacedKey key;

    @Getter
    private List<Contained<?>> value;

    @Override
    public Component toAdventureComponent() {
        Component header = Component.text()
                .append(Component
                        .text(key.toString(), NamedTextColor.GOLD))
                .append(Component
                        .empty()
                        .appendSpace()
                        .append(Component
                                .text("(TAG_CONTAINER)", NamedTextColor.GRAY)))
                .build();

        if (value.isEmpty()) {
            return header;
        }

        Component childrenBlock = Component.join(
                JoinConfiguration.separator(Component.newline()),
                value.stream()
                        .map(child -> Component.text("  ").append(child.toAdventureComponent()))
                        .toList());

        return Component.join(
                JoinConfiguration.separator(Component.newline()),
                header, childrenBlock);
    }
}
