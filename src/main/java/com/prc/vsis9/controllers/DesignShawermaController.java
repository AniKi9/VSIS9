package com.prc.vsis9.controllers;

import com.prc.vsis9.data.*;
import com.prc.vsis9.repository.IngredientRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.prc.vsis9.data.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("shawermaOrder")
public class DesignShawermaController {
    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignShawermaController(
            IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

    }
    @ModelAttribute(name = "shawermaOrder")
    public ShawermaOrder order() {
        return new ShawermaOrder();
    }
    @ModelAttribute(name = "shawerma")
    public Shawerma shawerma() {
        return new Shawerma();
    }
    @GetMapping
    public String showDesignForm() {
        return "design";
    }
    private Iterable<Ingredient> filterByType(
            Iterable<Ingredient> ingredients, Type type
    ) {
        return StreamSupport.stream(ingredients.spliterator(), false)
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    public String processShawerma(@Valid Shawerma shawerma, Errors errors,
                                  @ModelAttribute ShawermaOrder shawermaOrder) {
        if (errors.hasErrors()) {
            return "redirect:/design";
        }
        shawermaOrder.addShawerma(shawerma);
        log.info("Processing shawarma: {}", shawerma);
        return "redirect:/orders/current";
    }

//    @ModelAttribute
//    public void addIngredientsToModel(Model model) {
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("F", "Мучная лепешка", Type.WRAP),
//                new Ingredient("C", "Кукурузная лепешка", Type.WRAP),
//                new Ingredient("H", "Ветчина", Type.PROTEIN),
//                new Ingredient("B", "Буженина", Type.PROTEIN),
//                new Ingredient("T", "Помидоры", Type.VEGGIES),
//                new Ingredient("L", "Салат-латук", Type.VEGGIES),
//                new Ingredient("SC", "Колбасный сыр", Type.CHEESE),
//    new Ingredient("PC", "Плавленый сыр", Type.CHEESE),
// new Ingredient("M", "Майонез", Type.SAUCE),
// new Ingredient("A", "Аджика", Type.SAUCE)
// );
//    Ingredient.Type[] types = Ingredient.Type.values();
// for (Ingredient.Type type : types) {
//        model.addAttribute(type.toString().toLowerCase(),
//                filterByType(ingredients, type));
//    }
//}
//    @ModelAttribute(name = "shawarmaOrder")
//    public Shawarma order() {
//        return new Shawarma();
//    }
//    @ModelAttribute(name = "shawarma")
//    public Shawarma shawarma() {
//        return new Shawarma();
//    }
//    @GetMapping
//    public String showDesignForm() {
//        return "design";
//    }
//    private Iterable<Ingredient> filterByType(
//            List<Ingredient> ingredients, Ingredient.Type type) {
//        return ingredients
//                .stream()
//                .filter(x -> x.getType().equals(type))
//                .collect(Collectors.toList());
//    }
}
