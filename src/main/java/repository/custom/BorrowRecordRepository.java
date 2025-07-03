package repository.custom;

import dto.BorrowRecordDto;
import entity.BorrowRecordEntity;

import java.util.List;

public interface BorrowRecordRepository {
    BorrowRecordEntity searchById(String id);

    Boolean add(BorrowRecordEntity entity);

    List<BorrowRecordEntity> getAll();

    Boolean update(BorrowRecordEntity entity);

    Boolean deleteById(Long recordID);
}
