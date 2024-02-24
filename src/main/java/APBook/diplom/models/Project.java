package APBook.diplom.models;
//Assistance projects book

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;
    private String name;
    private String description;
    private String logo;

    @Column(name = "is_online")
    private Boolean isOnline;

    private String address;

    @Column(name = "start_date")
    @Basic
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "end_date")
    @Basic
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Photo> photos;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Post> posts;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "project")
    private Set<UserProject> subscribers;
//    private List<String> Videos;

}
