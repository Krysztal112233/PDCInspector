// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.collector.typed;

import java.text.MessageFormat;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.NamespacedKey;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class ContainedUnsupport extends Contained<Void> {
    @Getter
    private final NamespacedKey key;

    @Getter
    @Nullable private final Void value = null;

    @Override
    public Component toAdventureComponent() {
        return Component.empty()
                .append(Component.text()
                        .content(MessageFormat.format("[{}]", this.getKey().asMinimalString()))
                        .color(NamedTextColor.AQUA))
                .appendSpace()
                .append(Component.text()
                        .content("UNSUPPORTED_TYPE")
                        .color(NamedTextColor.RED)
                        .hoverEvent(HoverEvent
                                .showText(Component.text("This type current not supported.", NamedTextColor.RED))));
    }
}
