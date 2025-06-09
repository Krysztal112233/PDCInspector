// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.moframe.pinspector.collector.typed;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;

import dev.krysztal.moframe.pinspector.util.ComponentUtil;
import io.vavr.collection.Stream;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class ContainedIntegerArray extends Contained<int[]> {

    @Getter
    private final NamespacedKey key;

    private final int[] value;

    @Override
    public Component toAdventureComponent() {
        var list = Stream.ofAll(this.getValue()).toJavaList();
        return ComponentUtil.buildTypedArrayComponent(this.getKey(), "Integer[]", list, 4);
    }

    public int[] getValue() {
        return this.value.clone();
    }
}
