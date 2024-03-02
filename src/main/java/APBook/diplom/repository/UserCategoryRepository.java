package APBook.diplom.repository;

import APBook.diplom.models.Category;
import APBook.diplom.models.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
    List<UserCategory> findUserCategoriesByUserId(Long id);
    UserCategory findUserCategoryByUserIdAndCategoryId(Long userId, Long categoryId);
}
