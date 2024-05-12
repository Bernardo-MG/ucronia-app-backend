/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.association.security.authorization.permission.adapter.inbound;

import java.util.Collection;
import java.util.List;

import com.bernardomg.security.authorization.permission.adapter.inbound.initializer.PermissionRegister;
import com.bernardomg.security.authorization.permission.adapter.inbound.initializer.ResourcePermissionPair;

/**
 * Default permission register. Contains all the initial permission configuration.
 */
public final class AssociationPermissionRegister implements PermissionRegister {

    @Override
    public final Collection<String> getActions() {
        return List.of();
    }

    @Override
    public final Collection<ResourcePermissionPair> getPermissions() {
        // TODO: Use constants
        return List.of(
            // Member
            ResourcePermissionPair.of("MEMBER", "CREATE"), ResourcePermissionPair.of("MEMBER", "READ"),
            ResourcePermissionPair.of("MEMBER", "UPDATE"), ResourcePermissionPair.of("MEMBER", "DELETE"),
            // Fee
            ResourcePermissionPair.of("FEE", "CREATE"), ResourcePermissionPair.of("FEE", "READ"),
            ResourcePermissionPair.of("FEE", "UPDATE"), ResourcePermissionPair.of("FEE", "DELETE"),
            // User fee
            ResourcePermissionPair.of("USER_FEE", "READ"),
            // Transaction
            ResourcePermissionPair.of("TRANSACTION", "CREATE"), ResourcePermissionPair.of("TRANSACTION", "READ"),
            ResourcePermissionPair.of("TRANSACTION", "UPDATE"), ResourcePermissionPair.of("TRANSACTION", "DELETE"),
            // Inventory
            ResourcePermissionPair.of("INVENTORY_DONOR", "CREATE"),
            ResourcePermissionPair.of("INVENTORY_DONOR", "READ"),
            ResourcePermissionPair.of("INVENTORY_DONOR", "UPDATE"),
            ResourcePermissionPair.of("INVENTORY_DONOR", "DELETE"),
            // Library
            ResourcePermissionPair.of("LIBRARY_AUTHOR", "CREATE"), ResourcePermissionPair.of("LIBRARY_AUTHOR", "READ"),
            ResourcePermissionPair.of("LIBRARY_AUTHOR", "UPDATE"),
            ResourcePermissionPair.of("LIBRARY_AUTHOR", "DELETE"),
            ResourcePermissionPair.of("LIBRARY_PUBLISHER", "CREATE"),
            ResourcePermissionPair.of("LIBRARY_PUBLISHER", "READ"),
            ResourcePermissionPair.of("LIBRARY_PUBLISHER", "UPDATE"),
            ResourcePermissionPair.of("LIBRARY_PUBLISHER", "DELETE"),
            ResourcePermissionPair.of("LIBRARY_BOOK", "CREATE"), ResourcePermissionPair.of("LIBRARY_BOOK", "READ"),
            ResourcePermissionPair.of("LIBRARY_BOOK", "UPDATE"), ResourcePermissionPair.of("LIBRARY_BOOK", "DELETE"),
            ResourcePermissionPair.of("LIBRARY_BOOK_TYPE", "CREATE"),
            ResourcePermissionPair.of("LIBRARY_BOOK_TYPE", "READ"),
            ResourcePermissionPair.of("LIBRARY_BOOK_TYPE", "UPDATE"),
            ResourcePermissionPair.of("LIBRARY_BOOK_TYPE", "DELETE"),
            ResourcePermissionPair.of("LIBRARY_GAME_SYSTEM", "CREATE"),
            ResourcePermissionPair.of("LIBRARY_GAME_SYSTEM", "READ"),
            ResourcePermissionPair.of("LIBRARY_GAME_SYSTEM", "UPDATE"),
            ResourcePermissionPair.of("LIBRARY_GAME_SYSTEM", "DELETE"),
            ResourcePermissionPair.of("LIBRARY_LENDING", "CREATE"),
            ResourcePermissionPair.of("LIBRARY_LENDING", "READ"),
            ResourcePermissionPair.of("LIBRARY_LENDING", "UPDATE"),
            ResourcePermissionPair.of("LIBRARY_LENDING", "DELETE"),
            // Configuration
            ResourcePermissionPair.of("ASSOCIATION_CONFIGURATION", "CREATE"),
            ResourcePermissionPair.of("ASSOCIATION_CONFIGURATION", "READ"),
            ResourcePermissionPair.of("ASSOCIATION_CONFIGURATION", "UPDATE"),
            ResourcePermissionPair.of("ASSOCIATION_CONFIGURATION", "DELETE"),
            // Balance
            ResourcePermissionPair.of("BALANCE", "READ"),
            // Views
            ResourcePermissionPair.of("FUNDS", "VIEW"), ResourcePermissionPair.of("MEMBER", "VIEW"),
            ResourcePermissionPair.of("FEE", "VIEW"), ResourcePermissionPair.of("USER_FEE", "VIEW"),
            ResourcePermissionPair.of("LIBRARY", "VIEW"), ResourcePermissionPair.of("LIBRARY_ADMIN", "VIEW"),
            ResourcePermissionPair.of("ASSOCIATION_CONFIGURATION", "VIEW"));
    }

    @Override
    public final Collection<String> getResources() {
        return List.of("MEMBER", "FEE", "USER_FEE", "TRANSACTION", "BALANCE", "ASSOCIATION_CONFIGURATION", "FUNDS",
            "INVENTORY_DONOR", "LIBRARY", "LIBRARY_ADMIN", "LIBRARY_AUTHOR", "LIBRARY_PUBLISHER", "LIBRARY_BOOK",
            "LIBRARY_BOOK_TYPE", "LIBRARY_GAME_SYSTEM", "LIBRARY_LENDING");
    }

}
