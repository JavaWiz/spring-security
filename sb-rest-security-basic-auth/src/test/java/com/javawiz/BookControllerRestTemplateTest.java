package com.javawiz;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javawiz.entity.Book;
import com.javawiz.repository.BookRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    //@WithMockUser is not working with TestRestTemplate
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BookRepository mockRepository;

    @BeforeEach
    public void init() {
        Book book = new Book(1L, "A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41"));
        when(mockRepository.findById(1L)).thenReturn(Optional.of(book));
    }

    @Test
    public void find_login_ok() throws Exception {

        String expected = "{id:1,name:\"A Guide to the Bodhisattva Way of Life\",author:\"Santideva\",price:15.41}";

        ResponseEntity<String> response = restTemplate
                .withBasicAuth("user", "password")
                .getForEntity("/books/1", String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void find_nologin_401() throws Exception {

        ResponseEntity<String> response = restTemplate
                .getForEntity("/books/1", String.class);

        printJSON(response);

        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}