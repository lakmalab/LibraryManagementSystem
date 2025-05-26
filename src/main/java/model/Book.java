package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long bookID;
    private Long  ISBN;
    private String Title;
    private String Author;
    private String Genre;
    private Boolean Availability;
}
