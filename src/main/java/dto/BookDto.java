package dto;

import enums.Genre;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long bookID;
    private Long  isbn;
    private String title;
    private String author;
    private Genre genre;
    private String cover;
    private String description;
    private Boolean available;

    @Override
    public String toString() {
        return ("" + title);
    }
}
