package acc.spring.springtdd;

import acc.spring.springtdd.model.Dog;
import acc.spring.springtdd.repository.IDogRepository;
import acc.spring.springtdd.service.IDogService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class SpringTddApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SpringTddApplication.class, args);
    }

	@Bean
	CommandLineRunner run(IDogService dogService){
		return args -> {
            dogService.saveDog(Dog.builder()
                    .age(5)
                    .name("Manasitas")
                    .breed("Teckel")
                    .build()
            );
            dogService.saveDog(Dog.builder()
                    .age(7)
                    .name("Cocochitas")
                    .breed("Schanuzer")
                    .build()
            );
		};
	}
}
