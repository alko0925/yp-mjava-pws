package ru.ya.spring3pw.unit.service.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.*;
import ru.ya.spring3pw.repository.PostRepository;

@Configuration
@Profile("test")
@ComponentScan("ru.ya.spring3pw.service")
public class TestConfiguration {

    @Bean
    @Primary
    public PostRepository mockPostRepository() {
        return Mockito.mock(PostRepository.class);
    }
}
