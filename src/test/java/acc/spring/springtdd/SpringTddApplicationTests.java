package acc.spring.springtdd;

import acc.spring.springtdd.controller.DogController;
import acc.spring.springtdd.model.Dog;
import acc.spring.springtdd.service.IDogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SpringTddApplicationTests {
    @Autowired
    IDogService dogService;

    @Test
    void contextLoads() {
    }

    @Test
    void checkDogDataPassOld(){
        String dogDataOld = dogService.getDogData(
                new Dog(null,"Mijotron",8,"Terrier")
        );
        assertEquals(dogDataOld,"Old: Mijotron-Terrier");

        String dogDataYoung = dogService.getDogData(
                new Dog(null,"Mijotron",4,"Terrier")
        );
        assertEquals(dogDataYoung,"Young: Mijotron-Terrier");
    }

    //MockMVC Test -> between Unit & Integracion but NOT Integration



}
