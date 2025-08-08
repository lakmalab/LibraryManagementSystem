package service.custom.impl;

import com.google.inject.Inject;
import dto.BookDto;
import dto.BorrowRecordDto;
import entity.BookEntity;
import entity.BorrowRecordEntity;
import org.modelmapper.ModelMapper;
import repository.custom.BorrowRecordRepository;
import service.custom.BorrowRecordService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BorrowRecordServiceImpl implements BorrowRecordService {

    @Inject
    BorrowRecordRepository repository;


    @Override
    public Boolean add(BorrowRecordDto dto) {
        BorrowRecordEntity entity = new ModelMapper().map(dto, BorrowRecordEntity.class);
        return repository.add(entity);
    }

    @Override
    public List<BorrowRecordDto> getAll() {
        List<BorrowRecordEntity> all = repository.getAll();
        ArrayList<BorrowRecordDto> recordDtoArrayList = new ArrayList<>();

        all.forEach(Entity -> {
            recordDtoArrayList.add(new ModelMapper().map(Entity, BorrowRecordDto.class));
        });

        return recordDtoArrayList;
    }

    @Override
    public Boolean update(BorrowRecordDto dto) {
        BorrowRecordEntity entity = new ModelMapper().map(dto, BorrowRecordEntity.class);
        return repository.update(entity);
    }

    @Override
    public Boolean deleteById(Long recordID) {
        return null;
    }

    @Override
    public List<BorrowRecordDto> getRecordsByBookId(Long bookID) {
        List<BorrowRecordEntity> all = Collections.singletonList(repository.searchById(String.valueOf(bookID)));
        ArrayList<BorrowRecordDto> BorrowRecordDtoList = new ArrayList<>();

        all.forEach(BorrowRecordEntity -> {
            BorrowRecordDtoList.add(new ModelMapper().map(BorrowRecordEntity, BorrowRecordDto.class));
        });
        return List.of();
    }
}
