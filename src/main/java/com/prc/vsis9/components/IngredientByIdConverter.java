package com.prc.vsis9.components;

import com.prc.vsis9.data.Ingredient;
import com.prc.vsis9.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
    private IngredientRepository ingredientRepo;
    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }
    @Override
    public Ingredient convert(String id) {
        return ingredientRepo.findById(id).orElse(null);
    }

//    private Map<String, Ingredient> ingredientMap = new HashMap<>();
//    public IngredientByIdConverter() {
//        ingredientMap.put("F",
//                new Ingredient("F", "Мучная лепешка", Type.WRAP));
//        ingredientMap.put("C",
//                new Ingredient("C", "Кукурузная лепешка", Type.WRAP));
//        ingredientMap.put("H",
//                new Ingredient("H", "Ветчина", Type.PROTEIN));
//        ingredientMap.put("B",
//                new Ingredient("B", "Буженина", Type.PROTEIN));
//        ingredientMap.put("T",
//                new Ingredient("T", "Помидоры", Type.VEGGIES));
//        ingredientMap.put("L",
//                new Ingredient("L", "Салат-латук", Type.VEGGIES));
//        ingredientMap.put("SC",
//                new Ingredient("SC", "Колбасный сыр", Type.CHEESE));
//        ingredientMap.put("PC",
//                new Ingredient("PC", "Плавленый сыр", Type.CHEESE));
//        ingredientMap.put("M",
//                new Ingredient("M", "Майонез", Type.SAUCE));
//        ingredientMap.put("A",
//                new Ingredient("A", "Аджика", Type.SAUCE));
//    }
//    @Override
//    public Ingredient convert(String id) {
//        return ingredientMap.get(id);
//    }
}
