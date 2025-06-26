
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

import com.bernardomg.association.person.adapter.outbound.rest.controller.ContactMethodController;
import com.bernardomg.association.person.adapter.outbound.rest.model.ContactMethodChange;
import com.bernardomg.association.person.adapter.outbound.rest.model.ContactMethodCreation;
import com.bernardomg.association.person.test.configuration.factory.ContactMethodChanges;
import com.bernardomg.association.person.test.configuration.factory.ContactMethodCreations;
import com.bernardomg.association.person.test.configuration.factory.ContactMethods;
import com.bernardomg.association.person.usecase.service.ContactMethodService;
import com.bernardomg.test.json.JsonUtils;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContactMethodController")
class TestContactMethodController {

    @InjectMocks
    private ContactMethodController controller;

    private MockMvc                 mockMvc;

    @Mock
    private ContactMethodService    service;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .build();
    }

    @Test
    @DisplayName("When creating a contact method, it is sent to the service")
    void testCreate_CallsService() throws Exception {
        final ContactMethodCreation personChange;

        // GIVEN
        personChange = ContactMethodCreations.valid();

        // WHEN
        mockMvc.perform(post("/person/contactMethod").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).create(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(ContactMethods.email())));
    }

    @Test
    @DisplayName("When updating a contact method, it is sent to the service")
    void testUpdate_CallsService() throws Exception {
        final ContactMethodChange personChange;

        // GIVEN
        personChange = ContactMethodChanges.valid();

        // WHEN
        mockMvc.perform(put("/person/contactMethod/1").contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJson(personChange)));

        // THEN
        verify(service).update(assertArg(actualBook -> assertThat(actualBook).usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(ContactMethods.email())));
    }

}
