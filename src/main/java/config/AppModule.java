package config;

import com.google.inject.AbstractModule;
import repository.custom.BookRepository;
import repository.custom.UserRepository;
import repository.custom.impl.BookRepositoryImpl;
import repository.custom.impl.UserRepositoryImpl;
import service.custom.BookService;
import service.custom.UserService;
import service.custom.impl.BookServiceImpl;
import service.custom.impl.UserServiceImpl;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserService.class).to(UserServiceImpl.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class);
        bind(BookService.class).to(BookServiceImpl.class);
        bind(BookRepository.class).to(BookRepositoryImpl.class);
    }
}
