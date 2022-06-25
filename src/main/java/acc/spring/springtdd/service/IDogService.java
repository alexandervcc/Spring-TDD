package acc.spring.springtdd.service;

import acc.spring.springtdd.model.Dog;

import java.util.List;

public interface IDogService {
    List<Dog> findAll();
    Dog findById(Long dogId);
    Dog saveDog(Dog newDog);
    void deleteDog(Long deleteDog);
    List<Dog> getDogsByBreed(String dogBreed);
    Boolean checkDogAlreadyExists(Dog newDog);
}
