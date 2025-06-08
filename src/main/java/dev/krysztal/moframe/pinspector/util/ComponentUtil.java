// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.pinspector.util;

import dev.krysztal.moframe.pinspector.collector.typed.Contained;
import java.text.MessageFormat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public final class ComponentUtil {

    public static Component buildTypedContent(
            final Contained<?> deepthContainer,
            final String type,
            final String content) {
        return Component.empty()
                .append(Component
                        .text()
                        .content(MessageFormat.format("[{}]", deepthContainer.getKey().asMinimalString()))
                        .color(NamedTextColor.AQUA)
                        .decorate(TextDecoration.ITALIC))
                .appendSpace()
                .append(Component
                        .text()
                        .content(content)
                        .color(NamedTextColor.GREEN)
                        .hoverEvent(HoverEvent
                                .showText(Component
                                        .text(content, NamedTextColor.YELLOW)
                                        .decorate(TextDecoration.ITALIC))));
    }
}
