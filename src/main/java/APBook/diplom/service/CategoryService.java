package APBook.diplom.service;

import APBook.diplom.models.Category;
import APBook.diplom.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> add(){
        if(!categoryRepository.findAll().isEmpty()){
            return categoryRepository.findAll();
        }
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Здравоохранение и ЗОЖ"));
        categories.add(new Category(2L, "ЧС"));
        categories.add(new Category(3L, "Дети и молодежь"));
        categories.add(new Category(4L, "Ветераны и Историческая память"));
        categories.add(new Category(5L, "Спорт и события"));
        categories.add(new Category(6L, "Животные"));
        categories.add(new Category(7L, "Старшое поколение"));
        categories.add(new Category(8L, "Люди с ОВЗ"));
        categories.add(new Category(9L, "Экология"));
        categories.add(new Category(10L, "Культура и искусство"));
        categories.add(new Category(11L, "Поиск пропавших"));
        categories.add(new Category(12L, "Образование"));
        categories.add(new Category(13L, "Интеллектуальная помощь"));
        categories.add(new Category(14L, "Другое"));
        return categoryRepository.saveAll(categories);
    }
}
