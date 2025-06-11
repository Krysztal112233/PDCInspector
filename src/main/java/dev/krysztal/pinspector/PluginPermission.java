// Copyright (C) 2025 KrysztalHuang <krysztal.huang@outlook.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
//
// See the file LICENSE for the full license text.
package dev.krysztal.pinspector;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public final class PluginPermission {
    public static final Permission PDC_DEBUGGER;

    static {
        PDC_DEBUGGER = new Permission("pinspector.debugger");

        PDC_DEBUGGER.setDefault(PermissionDefault.OP);
    }
}
