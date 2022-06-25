package acc.spring.springtdd.service;

import acc.spring.springtdd.exceptions.exception.DogCustomException;
import acc.spring.springtdd.model.Dog;
import acc.spring.springtdd.repository.IDogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DogServiceImpl  implements  IDogService{
    private IDogRepository dogRepository;

    @Override
    public List<Dog> findAll() {
        return dogRepository.findAll();
    }

    @Override
    public Dog findById(Long dogId) {
        return dogRepository.findById(dogId).orElse(null);
    }

    @Override
    public Dog saveDog(Dog newDog) {
        if(checkDogAlreadyExists(newDog)){
           throw new DogCustomException("Dog already exists.");
        }
        return this.dogRepository.save(newDog);
    }

    @Override
    public void deleteDog(Long deleteDog) {
        this.dogRepository.deleteById(deleteDog);
    }

    @Override
    public Boolean checkDogAlreadyExists(Dog newDog) {
        Dog oldDog = this.dogRepository
                .findDogByNameOrAge(newDog.getName(),newDog.getAge())
                .orElse(null);
        if(oldDog==null){
            return false;
        }
        return true;
    }
}
