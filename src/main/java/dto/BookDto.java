package dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long bookID;
    private Long  isbn;
    private String title;
    private String author;
    private String genre;
    private Boolean available;
}
