package springrestapi.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usr")
@Builder
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String locale;
    private String userPic;
    private String gender;
    private LocalDateTime lastVisit;
}
