package com.prc.vsis9;

import com.prc.vsis9.data.Ingredient;
import com.prc.vsis9.data.Ingredient.Type;
import com.prc.vsis9.repository.IngredientRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class Vsis9Application implements WebMvcConfigurer{

    public static void main(String[] args) {
        SpringApplication.run(Vsis9Application.class, args);
    }

    @Bean
    public ApplicationRunner dataLoader(IngredientRepository repo) {
        return args -> {
            repo.save(new Ingredient("F", "Мучная лепешка", Type.WRAP));
            repo.save(new Ingredient("C", "Кукурузная лепешка", Type.WRAP));
            repo.save(new Ingredient("H", "Ветчина", Type.PROTEIN));
            repo.save(new Ingredient("B", "Буженина", Type.PROTEIN));
            repo.save(new Ingredient("T", "Помидоры", Type.VEGGIES));
            repo.save(new Ingredient("L", "Салат-латук", Type.VEGGIES));
            repo.save(new Ingredient("SC", "Колбасный сыр", Type.CHEESE));
            repo.save(new Ingredient("PC", "Плавленый сыр", Type.CHEESE));
            repo.save(new Ingredient("M", "Майонез", Type.SAUCE));
            repo.save(new Ingredient("A", "Аджика", Type.SAUCE));
        };
    }
}
