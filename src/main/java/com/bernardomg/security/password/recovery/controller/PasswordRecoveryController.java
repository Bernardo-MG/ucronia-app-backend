/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022 the original author or authors.
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

package com.bernardomg.security.password.recovery.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.security.password.recovery.model.PasswordRecoveryStatus;
import com.bernardomg.security.password.recovery.model.request.DtoPasswordRecoveryRequest;
import com.bernardomg.security.password.recovery.model.request.PasswordRecoveryChangeRequest;
import com.bernardomg.security.password.recovery.service.PasswordRecoveryService;
import com.bernardomg.security.token.model.DtoToken;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Handles password recovery requests. All the logic is delegated to a {@link PasswordRecoveryService}.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/password/reset")
@AllArgsConstructor
public class PasswordRecoveryController {

    /**
     * Password recovery service.
     */
    private final PasswordRecoveryService service;

    /**
     * Change password at the end of a password recovery.
     *
     * @param request
     *            password change request
     * @return password change status
     */
    @PostMapping(path = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public PasswordRecoveryStatus changePassword(@Valid @RequestBody final PasswordRecoveryChangeRequest request) {
        return service.changePassword(request.getToken(), request.getPassword());
    }

    /**
     * Start a password recovery.
     *
     * @param request
     *            password recovery request
     * @return password recovery status
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PasswordRecoveryStatus startRecovery(@Valid @RequestBody final DtoPasswordRecoveryRequest request) {
        return service.startPasswordRecovery(request.getEmail());
    }

    /**
     * Validates a token.
     *
     * @param request
     *            token validation request
     * @return token validation status
     */
    @PostMapping(path = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public PasswordRecoveryStatus validateToken(@Valid @RequestBody final DtoToken request) {
        return service.validateToken(request.getToken());
    }

}
