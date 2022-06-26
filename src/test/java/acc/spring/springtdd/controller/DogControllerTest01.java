package acc.spring.springtdd.controller;

import acc.spring.springtdd.exceptions.exception.DogExistsException;
import acc.spring.springtdd.model.Dog;
import acc.spring.springtdd.service.IDogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class DogControllerTest01 {
    @Autowired
    DogController dogController;
    @MockBean
    IDogService dogServiceMock;

    @Test
    void addNewDog(){
        //mock
        when(dogServiceMock.saveDog(createExampleDog()))
                .thenReturn(returnCreatedExampleDog());
        when(dogServiceMock.checkDogAlreadyExists(createExampleDog()))
                .thenReturn(false);

        ResponseEntity<?> response = dogController.postDog(createExampleDog());
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        Dog dogBody = (Dog) response.getBody();
        assertNotNull(dogBody.getId());
        assertEquals(dogBody.getAge(),createExampleDog().getAge());
        assertEquals(dogBody.getName(),createExampleDog().getName());
        assertEquals(dogBody.getBreed(),createExampleDog().getBreed());
    }

    @Test
    void addDuplicatedDog(){
        //mock
        given(dogServiceMock.saveDog(createExampleDog()))
                .willThrow(new DogExistsException("Dog already exists."));

        then(caughtException()).isInstanceOf(MyException.class);
    }

    private Dog createExampleDog(){
        return new Dog(null,"Mijotron",8,"Terrier");
    }
    private Dog returnCreatedExampleDog(){
        return new Dog(3L,"Mijotron",8,"Terrier");
    }
}