package APBook.diplom.repository;

import APBook.diplom.models.Category;
import APBook.diplom.models.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findUserCategoriesByUserId(Long id);
}
