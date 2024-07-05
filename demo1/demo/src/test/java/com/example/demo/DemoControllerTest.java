package com.example.demo;

import com.example.demo.model.Contact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class DemoControllerTest {

    @Mock
    private com.example.demo.ContactService contactService;

    @InjectMocks
    private com.example.demo.ContactController contactController;

    private MockMvc mockMvc;

    @Test
    public void testGetAllContacts() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setFirstName("John");
        contact1.setLastName("Doe");
        contact1.setPhoneNumber("123456789");

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setFirstName("Jane");
        contact2.setLastName("Doe");
        contact2.setPhoneNumber("987654321");

        List<Contact> contacts = Arrays.asList(contact1, contact2);
        when(contactService.getAllContacts()).thenReturn(contacts);

        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Doe"));
    }

    @Test
    public void testGetContactById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        Contact contact = new Contact();
        contact.setId(1L);
        contact.setFirstName("John");
        contact.setLastName("Doe");
        contact.setPhoneNumber("123456789");

        when(contactService.getContactById(1L)).thenReturn(contact);

        mockMvc.perform(get("/api/contacts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testCreateContact() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        Contact newContact = new Contact();
        newContact.setFirstName("John");
        newContact.setLastName("Doe");
        newContact.setPhoneNumber("123456789");

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setFirstName("John");
        contact1.setLastName("Doe");
        contact1.setPhoneNumber("123456789");

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setFirstName("Jane");
        contact2.setLastName("Doe");
        contact2.setPhoneNumber("987654321");

        List<Contact> contacts = Arrays.asList(contact1, contact2);
        when(contactService.saveContact(any(Contact.class))).thenReturn(newContact);
        when(contactService.getAllContacts()).thenReturn(contacts);

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"phoneNumber\":\"123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"))
                .andExpect(jsonPath("$[1].lastName").value("Doe"));
    }

    @Test
    public void testDeleteContact() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();
        doNothing().when(contactService).deleteContact(1L);

        mockMvc.perform(delete("/api/contacts/1"))
                .andExpect(status().isNoContent());
    }
}
