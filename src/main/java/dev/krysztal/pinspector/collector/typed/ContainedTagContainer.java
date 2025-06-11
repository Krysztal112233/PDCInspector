// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.collector.typed;

import dev.krysztal.pinspector.collector.ContainerRemapper;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;

public final class ContainedTagContainer extends Contained<List<Contained<?>>> {
    @Getter
    private final NamespacedKey key;

    private final List<Contained<?>> value;

    ContainedTagContainer(final NamespacedKey key, final PersistentDataContainer container) {
        this.value = ContainerRemapper.of(container).consume();
        this.key = key;
    }

    public List<Contained<?>> getValue() {
        return Collections.unmodifiableList(this.value);
    }

    @Override
    public Component toAdventureComponent() {
        final Component header = Component.text()
                .append(Component
                        .text(key.toString(), NamedTextColor.AQUA)
                        .decorate(TextDecoration.BOLD))
                .append(Component
                        .empty()
                        .appendSpace()
                        .append(Component
                                .text("(TAG_CONTAINER)", NamedTextColor.GRAY))
                        .hoverEvent(HoverEvent
                                .showText(Component
                                        .text("This is a composite type")
                                        .decorate(TextDecoration.ITALIC))))
                .build();

        if (value.isEmpty()) {
            return header;
        }

        final Component childrenBlock = Component.join(
                JoinConfiguration.separator(Component.newline()),
                value.stream()
                        .map(child -> Component
                                .text("    ")
                                .append(child.toAdventureComponent()))
                        .toList());

        return Component.join(
                JoinConfiguration.separator(Component.newline()),
                header, childrenBlock);
    }
}
