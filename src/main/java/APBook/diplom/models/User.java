package APBook.diplom.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {
    @Id
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
    @OneToMany(mappedBy = "project")
    private List<Project> projects;
}
