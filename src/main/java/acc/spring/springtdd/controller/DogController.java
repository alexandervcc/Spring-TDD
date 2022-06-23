package acc.spring.springtdd.controller;

import acc.spring.springtdd.model.Dog;
import acc.spring.springtdd.service.IDogService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/dog")
public class DogController {
    private IDogService dogService;

    @GetMapping("/dog/{idDog}")
    public ResponseEntity<Dog> getDogById(@PathVariable("idDog") Long idDog){
        System.out.println("ID: "+idDog);
        Dog dog = this.dogService.findById(idDog);
        if(dog==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(dog);
    }

    @GetMapping("/dogs")
    public ResponseEntity<?> getDogs(){
        List<Dog> dogs = this.dogService.findAll();
        if(dogs==null){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(dogs);
    }

    @PostMapping("/new")
    public ResponseEntity<?> postDog(@RequestBody Dog newDog){
        Dog dog = this.dogService.saveDog(newDog);
        return ResponseEntity.status(HttpStatus.CREATED).body(dog);
    }

    @PutMapping("/{idDog}")
    public ResponseEntity<?> postDog(@RequestBody Dog newDog, @PathVariable("idDog") Long idDog){
        Dog dog;
        try {
            Dog updDog = dogService.findById(idDog);
            updDog.setName(newDog.getName());
            updDog.setAge(newDog.getAge());
            dog = dogService.saveDog(updDog);
            return  ResponseEntity.status(HttpStatus.OK).body(dog);
        }catch (Exception e){
            dog = dogService.saveDog(newDog);
            return  ResponseEntity.status(HttpStatus.CREATED).body(dog);
        }
    }

    @DeleteMapping("/{idDog}")
    public ResponseEntity<?> deleteDog(@PathVariable("idDog") Long idDog){
        try {
            dogService.deleteDog(idDog);
            return  ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
