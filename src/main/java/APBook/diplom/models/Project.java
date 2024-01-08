package APBook.diplom.models;
//Assistance projects book

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;
    private String name;
    private String description;
    private String logo;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Post> posts;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    @OneToMany(mappedBy = "project")
    private Set<UserProject> subscribers;
//    private List<String> Videos;

}
