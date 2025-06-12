// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector.inspector.typed;

import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;

public abstract sealed class Contained<T> permits
        ContainedBoolean,
        ContainedByte,
        ContainedByteArray,
        ContainedDouble,
        ContainedFloat,
        ContainedInteger,
        ContainedIntegerArray,
        ContainedLong,
        ContainedLongArray,
        ContainedShort,
        ContainedString,
        ContainedTagContainer,
        ContainedUnsupport {
    public static Contained<?> of(final NamespacedKey key, final Boolean value) {
        return ContainedBoolean.of(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final byte[] value) {
        return new ContainedByteArray(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final Byte value) {
        return new ContainedByte(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final Double value) {
        return new ContainedDouble(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final Float value) {
        return new ContainedFloat(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final int[] value) {
        return new ContainedIntegerArray(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final Integer value) {
        return new ContainedInteger(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final long[] value) {
        return new ContainedLongArray(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final Long value) {
        return new ContainedLong(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final Short value) {
        return new ContainedShort(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final String value) {
        return new ContainedString(key, value);
    }

    public static Contained<?> of(final NamespacedKey key, final PersistentDataContainer value) {
        return new ContainedTagContainer(key, value);
    }

    public static Contained<?> unsupported(final NamespacedKey key) {
        return new ContainedUnsupport(key);
    }

    abstract public Component toAdventureComponent();

    abstract public NamespacedKey getKey();

    abstract protected T getValue();
}
