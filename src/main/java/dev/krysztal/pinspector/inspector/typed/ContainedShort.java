// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.inspector.typed;

import dev.krysztal.pinspector.util.ComponentUtil;
import java.text.MessageFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class ContainedShort extends Contained<Short> {

    @Getter
    private final NamespacedKey key;

    @Getter
    private final Short value;

    @Override
    public Component toAdventureComponent() {
        return ComponentUtil.buildTypedComponent(this.getKey(), "Short", MessageFormat.format("{0}", this.value));
    }
}
