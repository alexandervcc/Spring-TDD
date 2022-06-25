package acc.spring.springtdd.controller;

import acc.spring.springtdd.exceptions.exception.DogCustomException;
import acc.spring.springtdd.exceptions.ExceptionResponse;
import acc.spring.springtdd.model.Dog;
import acc.spring.springtdd.service.IDogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/dog")
public class DogController {
    private IDogService dogService;
    private final Logger logger = LoggerFactory.getLogger(DogController.class);

    @Operation(summary = "Get a Dog by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dog Found",
            content = { @Content(mediaType = "application/json",
            schema = @Schema(implementation = Dog.class)) }
        ),
        @ApiResponse(responseCode = "404", description = "Dog not found",
            content ={ @Content(mediaType = "application/json",
            schema = @Schema(implementation = ExceptionResponse.class))}
        )
    })
    @GetMapping("/{idDog}")
    public ResponseEntity<Dog> getDogById(@PathVariable("idDog") Long idDog){
        System.out.println("ID: "+idDog);
        Dog dog = this.dogService.findById(idDog);
        if(dog==null){
            logger.error("Dog doesnt Found.");
            throw new DogCustomException("Dog does not found, for reading.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(dog);
    }

    @Operation(summary = "Get List of Dogs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List retrieved",
            content = { @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = Dog.class))
            )}
        )
    })
    @GetMapping("/dogs")
    public ResponseEntity<?> getDogs(){
        List<Dog> dogs = this.dogService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dogs);
    }

    @Operation(summary = "Create a new Dogs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Dog created",
            content = { @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Dog.class)
            )}
        )
    })
    @PostMapping("/new")
    public ResponseEntity<?> postDog(@RequestBody Dog newDog){
        Dog dog = this.dogService.saveDog(newDog);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Result","OK");
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(dog);
    }

    @Operation(summary = "Update an existing Dog")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dog updated",
            content = { @Content( mediaType = "application/json",
                schema = @Schema(implementation = Dog.class)
            )}
        ),
        @ApiResponse(responseCode = "404", description = "Dog not found",
            content ={ @Content(mediaType = "application/json",
                schema = @Schema(implementation = ExceptionResponse.class))
        })
    })
    @PutMapping("/{idDog}")
    public ResponseEntity<?> postDog(@RequestBody Dog newDog, @PathVariable("idDog") Long idDog){
        try {
            Dog updDog = dogService.findById(idDog);
            updDog.setName(newDog.getName());
            updDog.setAge(newDog.getAge());
            Dog dog = dogService.saveDog(updDog);
            return  ResponseEntity.status(HttpStatus.OK).body(dog);
        }catch (Exception e){
            logger.info("Dog does not found, for update.");
            throw new DogCustomException("Dog does not found, for update.");
        }
    }

    @Operation(summary = "Delete an existing Dog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dog deleted",
                    content = { @Content( mediaType = "application/json",
                            schema = @Schema(implementation = Dog.class)
                    )}
            ),
            @ApiResponse(responseCode = "404", description = "Dog not found",
                    content ={ @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionResponse.class))
                    })
    })
    @DeleteMapping("/{idDog}")
    public ResponseEntity<?> deleteDog(@PathVariable("idDog") Long idDog){
        try {
            dogService.deleteDog(idDog);
            return  ResponseEntity.status(HttpStatus.OK).build();
        }catch (Exception e){
            logger.info("Dog does not found, for deletion.");
            throw new DogCustomException("Dog does not found, for deletion.");
        }
    }

    @Operation(summary = "Get List of Dogs by Breed")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List retrieved",
                    content = { @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Dog.class))
                    )}
            )
    })
    @GetMapping("/dogsBy")
    public ResponseEntity<?> getDogsByBreed(@RequestParam("breed") String dogBreed){
        List<Dog> dogs = this.dogService.getDogsByBreed(dogBreed);
        return ResponseEntity.status(HttpStatus.OK).body(dogs);
    }
}
