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
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;

@AllArgsConstructor()
public final class ContainedTagContainer extends Contained<Contained<?>> {
    protected ContainedTagContainer(NamespacedKey key, PersistentDataContainer container) {
        this.value = ContainerRemapper.of(container).consume();
        this.key = key;
    }

    @Getter
    private final NamespacedKey key;

    @Getter
    private Contained<?> value;

    @Override
    public Component toAdventureComponent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toAdventureComponent'");
    }
}
