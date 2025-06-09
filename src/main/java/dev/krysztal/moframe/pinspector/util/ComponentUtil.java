// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.pinspector.util;

import io.vavr.collection.Stream;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.BiFunction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;

public final class ComponentUtil {

    private static Component namespacedKey(final NamespacedKey key) {
        return Component.empty()
                .append(Component
                        .text()
                        .content(MessageFormat.format("[{}]", key.asMinimalString()))
                        .color(NamedTextColor.AQUA)
                        .decorate(TextDecoration.BOLD));
    }

    public static Component buildTypedComponent(
            final NamespacedKey key,
            final String type,
            final String content) {

        return namespacedKey(key)
                .appendSpace()
                .append(Component
                        .text()
                        .content(content)
                        .color(NamedTextColor.GREEN)
                        .hoverEvent(HoverEvent
                                .showText(Component
                                        .text(type, NamedTextColor.YELLOW)
                                        .decorate(TextDecoration.ITALIC))));
    }

    public static <T> Component buildTypedArrayComponent(
            final NamespacedKey key,
            final String type,
            final List<T> array,
            final int length) {

        final BiFunction<Integer, Object, Component> mapping = (index, value) -> {
            return Component.empty()
                    .append(Component
                            .text()
                            .content(MessageFormat.format("[{}] {}", index, value))
                            .hoverEvent(HoverEvent
                                    .showText(Component
                                            .text(type, NamedTextColor.YELLOW)
                                            .decorate(TextDecoration.ITALIC))));
        };

        final var result = Stream.ofAll(array)
                .zipWithIndex()
                .map(withIndex -> mapping.apply(withIndex._2, withIndex._1).appendSpace());

        if (array.size() <= length * 2) {
            return namespacedKey(key).append(result.fold(Component.empty(), (a, b) -> a.append(b)));
        }

        final var left = result.takeRight(length).fold(Component.empty(), (a, b) -> a.append(b));
        final var right = result.take(length).fold(Component.empty(), (a, b) -> a.append(b));
        final var between = result.drop(length).dropRight(length).fold(Component.empty(), (a, b) -> a.append(b));

        return namespacedKey(key).append (Component.empty()
                .append(left)
                .appendSpace()
                .append(Component
                        .text("...")
                        .hoverEvent(HoverEvent.showText(between)))
                .appendSpace()
                .append(right));
    }
}
