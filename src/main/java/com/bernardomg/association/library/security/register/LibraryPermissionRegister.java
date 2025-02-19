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

package com.bernardomg.association.library.security.register;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bernardomg.security.permission.initializer.usecase.PermissionRegister;
import com.bernardomg.security.permission.initializer.usecase.ResourcePermissionPair;

/**
 * Library permission register.
 */
@Component
public final class LibraryPermissionRegister implements PermissionRegister {

    @Override
    public final Collection<String> getActions() {
        return List.of();
    }

    @Override
    public final Collection<ResourcePermissionPair> getPermissions() {
        // TODO: Use constants
        return List.of(
            // Inventory
            new ResourcePermissionPair("INVENTORY_DONOR", "CREATE"),
            new ResourcePermissionPair("INVENTORY_DONOR", "READ"),
            new ResourcePermissionPair("INVENTORY_DONOR", "UPDATE"),
            new ResourcePermissionPair("INVENTORY_DONOR", "DELETE"),
            // Library
            new ResourcePermissionPair("LIBRARY_AUTHOR", "CREATE"),
            new ResourcePermissionPair("LIBRARY_AUTHOR", "READ"),
            new ResourcePermissionPair("LIBRARY_AUTHOR", "UPDATE"),
            new ResourcePermissionPair("LIBRARY_AUTHOR", "DELETE"),
            new ResourcePermissionPair("LIBRARY_PUBLISHER", "CREATE"),
            new ResourcePermissionPair("LIBRARY_PUBLISHER", "READ"),
            new ResourcePermissionPair("LIBRARY_PUBLISHER", "UPDATE"),
            new ResourcePermissionPair("LIBRARY_PUBLISHER", "DELETE"),
            new ResourcePermissionPair("LIBRARY_BOOK", "CREATE"), new ResourcePermissionPair("LIBRARY_BOOK", "READ"),
            new ResourcePermissionPair("LIBRARY_BOOK", "UPDATE"), new ResourcePermissionPair("LIBRARY_BOOK", "DELETE"),
            new ResourcePermissionPair("LIBRARY_BOOK_TYPE", "CREATE"),
            new ResourcePermissionPair("LIBRARY_BOOK_TYPE", "READ"),
            new ResourcePermissionPair("LIBRARY_BOOK_TYPE", "UPDATE"),
            new ResourcePermissionPair("LIBRARY_BOOK_TYPE", "DELETE"),
            new ResourcePermissionPair("LIBRARY_GAME_SYSTEM", "CREATE"),
            new ResourcePermissionPair("LIBRARY_GAME_SYSTEM", "READ"),
            new ResourcePermissionPair("LIBRARY_GAME_SYSTEM", "UPDATE"),
            new ResourcePermissionPair("LIBRARY_GAME_SYSTEM", "DELETE"),
            new ResourcePermissionPair("LIBRARY_LENDING", "CREATE"),
            new ResourcePermissionPair("LIBRARY_LENDING", "READ"),
            new ResourcePermissionPair("LIBRARY_LENDING", "UPDATE"),
            new ResourcePermissionPair("LIBRARY_LENDING", "DELETE"),
            // Views
            new ResourcePermissionPair("LIBRARY", "VIEW"), new ResourcePermissionPair("LIBRARY_AUTHOR", "VIEW"),
            new ResourcePermissionPair("LIBRARY_PUBLISHER", "VIEW"), new ResourcePermissionPair("LIBRARY_BOOK", "VIEW"),
            new ResourcePermissionPair("LIBRARY_BOOK_TYPE", "VIEW"),
            new ResourcePermissionPair("LIBRARY_GAME_SYSTEM", "VIEW"),
            new ResourcePermissionPair("LIBRARY_LENDING", "VIEW"), new ResourcePermissionPair("LIBRARY_ADMIN", "VIEW"),
            new ResourcePermissionPair("LIBRARY_LENDING", "VIEW"));
    }

    @Override
    public final Collection<String> getResources() {
        return List.of("INVENTORY_DONOR", "LIBRARY", "LIBRARY_ADMIN", "LIBRARY_AUTHOR", "LIBRARY_PUBLISHER",
            "LIBRARY_BOOK", "LIBRARY_BOOK_TYPE", "LIBRARY_GAME_SYSTEM", "LIBRARY_LENDING");
    }

}
