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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class ContainedBoolean extends Contained<Byte> {

    public static ContainedBoolean of(final NamespacedKey key, final Boolean value) {
        final var b2 = (byte) (value ? 1 : 0);
        return new ContainedBoolean(key, b2);
    }

    @Getter
    private final NamespacedKey key;

    @Getter
    private final Byte value;

    @Override
    public Component toAdventureComponent() {
        return ComponentUtil.buildTypedComponent(this.getKey(), "Boolean", this.value.toString());
    }

}
