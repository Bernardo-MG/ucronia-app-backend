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

package com.bernardomg.association.fee.security.register;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import com.bernardomg.security.permission.initializer.usecase.PermissionRegister;
import com.bernardomg.security.permission.initializer.usecase.ResourcePermissionPair;

/**
 * Fee permission register.
 */
@Component
public final class FeePermissionRegister implements PermissionRegister {

    @Override
    public final Collection<String> getActions() {
        return List.of();
    }

    @Override
    public final Collection<ResourcePermissionPair> getPermissions() {
        // TODO: Use constants
        return List.of(new ResourcePermissionPair("FEE", "CREATE"), new ResourcePermissionPair("FEE", "READ"),
            new ResourcePermissionPair("FEE", "UPDATE"), new ResourcePermissionPair("FEE", "DELETE"),
            // My fees
            new ResourcePermissionPair("MY_FEES", "READ"),
            // Views
            new ResourcePermissionPair("MY_FEES", "VIEW"), new ResourcePermissionPair("FEE", "VIEW"));
    }

    @Override
    public final Collection<String> getResources() {
        return List.of("FEE", "MY_FEES");
    }

}
