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

package com.bernardomg.association.inventory.adapter.outbound.rest.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bernardomg.association.inventory.adapter.outbound.rest.model.DonorCreation;
import com.bernardomg.association.inventory.domain.model.Donor;
import com.bernardomg.association.inventory.usecase.service.DonorService;
import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.security.access.RequireResourceAccess;
import com.bernardomg.security.authorization.permission.constant.Actions;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * Donor REST controller.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@RestController
@RequestMapping("/inventory/donor")
@AllArgsConstructor
public class DonorController {

    /**
     * Donor service.
     */
    private final DonorService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @RequireResourceAccess(resource = "INVENTORY_DONOR", action = Actions.CREATE)
    public Donor create(@Valid @RequestBody final DonorCreation creation) {
        final Member member;
        final Donor  donor;
        final long   memberNumber;

        if (creation.getMember() == null) {
            memberNumber = -1;
        } else {
            memberNumber = creation.getMember();
        }
        member = Member.builder()
            .withNumber(memberNumber)
            .build();
        donor = Donor.builder()
            .withName(creation.getName())
            .withMember(member)
            .build();
        return service.create(donor);
    }

    @DeleteMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "INVENTORY_DONOR", action = Actions.DELETE)
    public void delete(@PathVariable("number") final long number) {
        service.delete(number);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "INVENTORY_DONOR", action = Actions.READ)
    public Iterable<Donor> readAll(final Pageable pageable) {
        return service.getAll(pageable);
    }

    @GetMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "INVENTORY_DONOR", action = Actions.READ)
    public Donor readOne(@PathVariable("number") final Long number) {
        return service.getOne(number)
            .orElse(null);
    }

    @PutMapping(path = "/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequireResourceAccess(resource = "INVENTORY_DONOR", action = Actions.UPDATE)
    public Donor update(@PathVariable("number") final long number, @Valid @RequestBody final DonorCreation change) {
        final Member member;
        final Donor  donor;
        final long   memberNumber;

        if (change.getMember() == null) {
            memberNumber = -1;
        } else {
            memberNumber = change.getMember();
        }
        member = Member.builder()
            .withNumber(memberNumber)
            .build();
        donor = Donor.builder()
            .withName(change.getName())
            .withMember(member)
            .build();
        return service.update(donor);
    }

}
