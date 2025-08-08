package service.custom;

import dto.BorrowRecordDto;
import service.SuperService;

import java.util.List;

public interface BorrowRecordService extends SuperService {

    Boolean add(BorrowRecordDto dto);

    List<BorrowRecordDto> getAll();

    Boolean update(BorrowRecordDto dto);

    Boolean deleteById(Long recordID);

    List<BorrowRecordDto> getRecordsByBookId(Long bookID);
}
