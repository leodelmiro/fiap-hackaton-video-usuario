package com.leodelmiro.usuario.config;

import com.leodelmiro.usuario.core.dataprovider.EntrarUsuarioGateway;
import com.leodelmiro.usuario.core.usecase.impl.EntrarUsuarioUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntrarUsuarioUseCaseConfig {

    @Bean
    public EntrarUsuarioUseCaseImpl entrarUsuarioUseCase(
            EntrarUsuarioGateway entrarUsuarioGateway
    ) {
        return new EntrarUsuarioUseCaseImpl(entrarUsuarioGateway);
    }
}
