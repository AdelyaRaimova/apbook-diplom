package APBook.diplom.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserCategory> users;
}
