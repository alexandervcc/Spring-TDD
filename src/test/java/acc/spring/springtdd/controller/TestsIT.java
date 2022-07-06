package acc.spring.springtdd.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import acc.spring.springtdd.model.Dog;

@SpringBootTest
public class TestsIT {
    // TestRestTemplate
    @Test
    @Order(1)
    public void getAllDog() throws JSONException {
        TestRestTemplate restTemplate = new TestRestTemplate();
        // make an API call
        String expected = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"Manasitas\",\n" +
                "        \"age\": 5,\n" +
                "        \"breed\": \"Teckel\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"Cocochitas\",\n" +
                "        \"age\": 7,\n" +
                "        \"breed\": \"Schanuzer\"\n" +
                "    }\n" +
                "]";

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:8080/api/v1/dog/dogs",
                String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(2)
    public void getAllDogByBreed() throws JSONException {
        TestRestTemplate restTemplate = new TestRestTemplate();
        // make an API call
        String expected = "[{\"id\": 1,\"name\": \"Manasitas\", \"age\": 5,\"breed\": \"Teckel\"  }  ]";

        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://localhost:8080/api/v1/dog/dogsBy?breed=Teckel",
                String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);
    }

    @Test
    @Order(3)
    public void addNewNonExistingDog() {
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity request = new HttpEntity<Dog>(createExampleDog(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8080/api/v1/dog/new",
                request,
                String.class);
        assertEquals(201, response.getStatusCodeValue());
    }

    private Dog createExampleDog() {
        return new Dog(null, "Mijotron", 8, "Terrier");
    }
}
