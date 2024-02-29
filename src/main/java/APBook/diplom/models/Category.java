package APBook.diplom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<UserCategory> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id) && name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
