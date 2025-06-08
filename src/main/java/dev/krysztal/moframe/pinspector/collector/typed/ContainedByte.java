// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.pinspector.collector.typed;

import dev.krysztal.moframe.pinspector.util.ComponentUtil;
import java.text.MessageFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class ContainedByte extends Contained<Byte> {

    @Getter
    private final NamespacedKey key;

    @Getter
    private final Byte value;

    @Override
    public Component toAdventureComponent() {
        return ComponentUtil.buildTypedContent(this, "Byte", MessageFormat.format("{}", this.value));
    }
}
