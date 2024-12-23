
package com.bernardomg.association.person.test.adapter.outbound.rest.controller.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.assertArg;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bernardomg.association.person.adapter.outbound.rest.controller.PersonController;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChange;
import com.bernardomg.association.person.adapter.outbound.rest.model.PersonCreation;
import com.bernardomg.association.person.test.configuration.factory.PersonChanges;
import com.bernardomg.association.person.test.configuration.factory.PersonCreations;
import com.bernardomg.association.person.test.configuration.factory.Persons;
import com.bernardomg.association.person.usecase.service.PersonService;
import com.bernardomg.test.json.JsonUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("PersonController")
class TestPersonController {

    @InjectMocks
    private PersonController controller;

    private MockMvc          mockMvc;

    @Mock
    private PersonService    service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("When creating a person with an active membership, it is sent to the service")
    void testCreate_ActiveMembership_CallsService() throws Exception {
        final PersonCreation personChange;

        // GIVEN
        personChange = PersonCreations.membershipActive();

        // WHEN
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).create(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Persons.membershipActiveNew())));
    }

    @Test
    @DisplayName("When creating a person with an inactive membership, it is sent to the service")
    void testCreate_InactiveMembership_CallsService() throws Exception {
        final PersonCreation personChange;

        // GIVEN
        personChange = PersonCreations.membershipInactive();

        // WHEN
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).create(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Persons.membershipInactiveNew())));
    }

    @Test
    @DisplayName("When creating a person without membership, it is sent to the service")
    void testCreate_NoMembership_CallsService() throws Exception {
        final PersonCreation personChange;

        // GIVEN
        personChange = PersonCreations.noMembership();

        // WHEN
        mockMvc.perform(post("/person").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).create(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Persons.noMembershipNew())));
    }

    @Test
    @DisplayName("When updating a person with an active membership, it is sent to the service")
    void testUpdate_ActiveMembership_CallsService() throws Exception {
        final PersonChange personChange;

        // GIVEN
        personChange = PersonChanges.membershipActive();

        // WHEN
        mockMvc.perform(put("/person/1").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).update(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Persons.membershipActive())));
    }

    @Test
    @DisplayName("When updating a person with an inactive membership, it is sent to the service")
    void testUpdate_InactiveMembership_CallsService() throws Exception {
        final PersonChange personChange;

        // GIVEN
        personChange = PersonChanges.membershipInactive();

        // WHEN
        mockMvc.perform(put("/person/1").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).update(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Persons.membershipInactive())));
    }

    @Test
    @DisplayName("When updating a person without membership, it is sent to the service")
    void testUpdate_NoMembership_CallsService() throws Exception {
        final PersonChange personChange;

        // GIVEN
        personChange = PersonChanges.noMembership();

        // WHEN
        mockMvc.perform(put("/person/1").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).update(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(Persons.noMembership())));
    }

}
