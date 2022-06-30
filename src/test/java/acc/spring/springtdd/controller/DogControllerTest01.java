package acc.spring.springtdd.controller;

import acc.spring.springtdd.exceptions.exception.DogCustomException;
import acc.spring.springtdd.model.Dog;
import acc.spring.springtdd.service.IDogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DogControllerTest01 {
    @Autowired
    DogController dogController;
    @MockBean
    IDogService dogServiceMock;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addNewDog(){
        //mock
        when(dogServiceMock.saveDog(createExampleDog()))
                .thenReturn(returnCreatedExampleDog());
        when(dogServiceMock.checkDogAlreadyExists(createExampleDog()))
                .thenReturn(false);

        ResponseEntity<?> response = dogController.postDog(createExampleDog());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Dog dogBody = (Dog) response.getBody();
        assert dogBody != null;
        assertEquals(dogBody.getAge(),createExampleDog().getAge());
        assertEquals(dogBody.getName(),createExampleDog().getName());
        assertEquals(dogBody.getBreed(),createExampleDog().getBreed());
    }


    @Test
    public void addNewDogMockMVC() throws Exception {
        Dog newDog = createExampleDog();
        Dog createdDog = returnCreatedExampleDog();
        when(dogServiceMock.saveDog(newDog)).thenReturn(createdDog);
        when(dogServiceMock.checkDogAlreadyExists(newDog)).thenReturn(false);

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(createExampleDog());

        this.mockMvc
            .perform(
                post("/api/v1/dog/new")             //Make a mock call
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
            )
                .andDo(print())                                //Print Response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(createdDog.getId()))
        ;
    }


    @Test
    void getExistingDogById() {
        Dog createdDog = returnCreatedExampleDog();
        when(dogServiceMock.findById(createdDog.getId()))
                .thenReturn(createdDog);
        ResponseEntity<?> response = dogController.getDogById(createdDog.getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    @Test
    void getNOTExistingDogById() {
        Dog createdDog = returnCreatedExampleDog();
        when(dogServiceMock.findById(createdDog.getId()))
                .thenReturn(null);

        DogCustomException exception= Assertions.assertThrows(
            DogCustomException.class, () ->
                dogController.getDogById(createdDog.getId())
        );

        Assertions.assertEquals(
                "Dog does not found, for reading.",
                exception.getMessage()
        );

    }


    @Test
    void getListOfDogsEmpty(){
        when(dogServiceMock.findAll())
                .thenReturn(new ArrayList<>());
        ResponseEntity<?> response = dogController.getDogs();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody() instanceof ArrayList );
    }

    @Test
    void getListOfDogsNOTEmpty(){
        ArrayList<Dog> listDogs = new ArrayList<>();
        listDogs.add(returnCreatedExampleDog());
        when(dogServiceMock.findAll())
                .thenReturn(listDogs);
        ResponseEntity<?> response = dogController.getDogs();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertTrue(response.getBody() instanceof ArrayList );
        assertTrue( ((ArrayList<?>) response.getBody()).get(0) instanceof Dog);
    }

    @Test
    void getListOfDogsByBreedMockMVC() throws Exception {
        ArrayList<Dog> listDogs = new ArrayList<>();
        Dog newDog = returnCreatedExampleDog();
        listDogs.add(newDog);
        listDogs.add(newDog);

        when(dogServiceMock.getDogsByBreed(newDog.getBreed())).thenReturn(listDogs);

        this.mockMvc.perform(
            get("/api/v1/dog/dogsBy")
            .param("breed",newDog.getBreed())
        )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$.[0].id").value(3))
        ;
    }



    //PRIVATES
    private Dog createExampleDog(){
        return new Dog(null,"Mijotron",8,"Terrier");
    }
    private Dog returnCreatedExampleDog(){
        return new Dog(3L,"Mijotron",8,"Terrier");
    }
}