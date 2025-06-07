package com.extension.project.boundlessbooks;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class BoundlessBooksApplicationTest {

    @Test
    void main_InvokesSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mockedSpringApplication = mockStatic(SpringApplication.class)) {
            String[] args = {};

            BoundlessBooksApplication.main(args);

            mockedSpringApplication.verify(() ->
                    SpringApplication.run(BoundlessBooksApplication.class, args),
                    times(1)
            );
        }
    }
}