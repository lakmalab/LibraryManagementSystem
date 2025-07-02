package dto;

import entity.BookEntity;
import entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowRecordDto {
    private int recordId;
    private UserEntity user;
    private BookEntity book;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private double fine;
}
