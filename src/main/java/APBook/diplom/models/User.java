package APBook.diplom.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
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

    @JsonIgnore
    @Column(name = "selected_categories")
    @OneToMany(mappedBy = "user")
    private Set<UserCategory> selectedCategories;

    @JsonGetter("selectedCategories")
    public List<Long> getCategoryId() {
        return selectedCategories.stream()
                .map(category -> category.getCategory().getId())
                .collect(Collectors.toList());
    }

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<UserProject> subscriptions;

    @JsonGetter("subscriptions")
    public List<Long> getProjectIds() {
        return subscriptions.stream()
                .map(userProject -> userProject.getProject().getId())
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(age, user.age) && Objects.equals(photo, user.photo) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, age, photo, email, password);
    }
}
