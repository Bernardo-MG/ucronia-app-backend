/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martinez Garrido
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

package com.bernardomg.association.key.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.key.domain.model.KeyAssignment;
import com.bernardomg.association.key.domain.model.KeyAssignment.AssignedProfile;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class KeyAssignments {

    public static final KeyAssignment assigned() {
        return new KeyAssignment(KeyConstants.NUMBER, true, KeyConstants.DESCRIPTION,
            Optional.of(new AssignedProfile(ProfileConstants.NUMBER, ProfileConstants.FULL_NAME)));
    }

    public static final KeyAssignment reassigned() {
        return new KeyAssignment(KeyConstants.NUMBER, true, KeyConstants.DESCRIPTION, Optional
            .of(new AssignedProfile(ProfileConstants.ALTERNATIVE_NUMBER, ProfileConstants.ALTERNATIVE_FULL_NAME)));
    }

    public static final KeyAssignment unassigned() {
        return new KeyAssignment(KeyConstants.NUMBER, true, KeyConstants.DESCRIPTION, Optional.empty());
    }

    public static final KeyAssignment unassignedAlternative() {
        return new KeyAssignment(KeyConstants.ALTERNATIVE_NUMBER, true, KeyConstants.DESCRIPTION, Optional.empty());
    }

    private KeyAssignments() {
        super();
    }

}
