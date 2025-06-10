// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.collector;

import static org.bukkit.persistence.PersistentDataType.*;

import dev.krysztal.pinspector.collector.typed.Contained;
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
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ContainerRemapper {
    private static final List<BiFunction<PersistentDataContainerView, NamespacedKey, Try<Contained<?>>>> TRY_FLOW = List
            .of(f(BYTE),
                    f(SHORT),
                    f(INTEGER),
                    f(LONG),
                    f(FLOAT),
                    f(DOUBLE),
                    f(BOOLEAN),
                    f(STRING),
                    f(BYTE_ARRAY),
                    f(INTEGER_ARRAY),
                    f(LONG_ARRAY),
                    f(TAG_CONTAINER));

    public static ContainerRemapper of(final PersistentDataHolder pdh) {
        return new ContainerRemapper(pdh.getPersistentDataContainer());
    }

    public static ContainerRemapper of(final ItemStack itemStack) {
        return new ContainerRemapper(itemStack.getPersistentDataContainer());
    }

    public static ContainerRemapper of(final PersistentDataContainer pdc) {
        return new ContainerRemapper(pdc);
    }

    private static <P, C> BiFunction<PersistentDataContainerView, NamespacedKey, Try<Contained<?>>> f(
            final PersistentDataType<P, C> type) {
        return (c, key) -> Try.of(() -> c.get(key, LONG_ARRAY)).map(value -> Contained.of(key, value));
    }

    @Getter
    private final PersistentDataContainerView pdc;

    public List<Contained<?>> consume() {
        final var result = new ArrayList();
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
