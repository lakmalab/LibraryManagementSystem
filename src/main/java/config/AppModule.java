package config;

import com.google.inject.AbstractModule;
import repository.custom.BookRepository;
import repository.custom.BorrowRecordRepository;
import repository.custom.UserRepository;
import repository.custom.impl.BookRepositoryImpl;
import repository.custom.impl.BorrowRecordRepositoryImpl;
import repository.custom.impl.UserRepositoryImpl;
import service.custom.BookService;
import service.custom.BorrowRecordService;
import service.custom.UserService;
import service.custom.impl.BookServiceImpl;
import service.custom.impl.BorrowRecordServiceImpl;
import service.custom.impl.UserServiceImpl;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(BookService.class).to(BookServiceImpl.class);
        bind(BookRepository.class).to(BookRepositoryImpl.class);
        bind(BorrowRecordService.class).to(BorrowRecordServiceImpl.class);
        bind(BorrowRecordRepository.class).to(BorrowRecordRepositoryImpl.class);
    }
}
