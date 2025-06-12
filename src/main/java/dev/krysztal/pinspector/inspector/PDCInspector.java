// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.inspector;

import static org.bukkit.persistence.PersistentDataType.*;

import dev.krysztal.pinspector.inspector.typed.Contained;
import io.papermc.paper.persistence.PersistentDataContainerView;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PDCInspector {
    private static final List<BiFunction<PersistentDataContainerView, NamespacedKey, Try<Contained<?>>>> TRY_FLOW = List
            .of((c, key) -> Try.of(() -> c.get(key, BYTE)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, SHORT)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, INTEGER)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, LONG)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, FLOAT)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, DOUBLE)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, BOOLEAN)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, STRING)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, BYTE_ARRAY)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, INTEGER_ARRAY)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, LONG_ARRAY)).map(value -> Contained.of(key, value)),
                    (c, key) -> Try.of(() -> c.get(key, TAG_CONTAINER)).map(value -> Contained.of(key, value)));

    public static PDCInspector of(final PersistentDataHolder pdh) {
        return new PDCInspector(pdh.getPersistentDataContainer());
    }

    public static PDCInspector of(final ItemStack itemStack) {
        return new PDCInspector(itemStack.getPersistentDataContainer());
    }

    public static PDCInspector of(PersistentDataContainerView pdc) {
        return new PDCInspector(pdc);
    }

    @Getter
    private final PersistentDataContainerView pdc;

    public List<Contained<?>> consume() {
        final var result = new ArrayList<Contained<?>>();
        for (final NamespacedKey key : this.getPdc().getKeys()) {
            result.add(this.matcher(this.getPdc(), key));
        }
        return result;
    }

    public Contained<?> matcher(final PersistentDataContainerView container, final NamespacedKey key) {
        return Stream
                .ofAll(TRY_FLOW).map(f -> f.apply(container, key))
                .flatMap(Try::toOption)
                .headOption()
                .getOrElse(Contained.unsupported(key));
    }
}
