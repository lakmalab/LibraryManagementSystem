package entity;


import enums.Genre;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookID;
    private Long  isbn;
    private String title;
    private String author;
    private Genre genre;
    private String cover;
    private String description;
    private Boolean available;
}
