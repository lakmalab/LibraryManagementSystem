package service.custom.impl;

import com.google.inject.Inject;
import dto.BorrowRecordDto;
import entity.BorrowRecordEntity;
import org.modelmapper.ModelMapper;
import repository.custom.BorrowRecordRepository;
import service.custom.BorrowRecordService;

import java.util.ArrayList;
import java.util.List;

public class BorrowRecordServiceImpl implements BorrowRecordService {

    @Inject
    BorrowRecordRepository repository;
    @Override
    public BorrowRecordDto searchById(String id) {
        return null;
    }

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
}
