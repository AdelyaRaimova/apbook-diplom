package APBook.diplom.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;
    private Integer age;

    @Column(length = 10000000)
    private String photo;
    private String email;
    private String password;

    @Column(name = "selected_categories")
    @OneToMany(mappedBy = "category")
    private Set<UserCategory> selectedCategories;

    @OneToMany(mappedBy = "author")
    private List<Project> projects;

    @OneToMany(mappedBy = "user")
    private Set<UserProject> subscriptions;

    @JsonGetter("subscriptions")
    public List<Long> getProjectIds() {
        return subscriptions.stream()
                .map(userProject -> userProject.getProject().getId())
                .collect(Collectors.toList());
    }
}
