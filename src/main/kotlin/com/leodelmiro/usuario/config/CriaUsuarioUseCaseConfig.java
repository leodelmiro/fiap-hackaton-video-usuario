package com.leodelmiro.usuario.config;

import com.leodelmiro.usuario.core.dataprovider.CriaUsuarioGateway;
import com.leodelmiro.usuario.core.usecase.impl.CriaUsuarioUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CriaUsuarioUseCaseConfig {

    @Bean
    public CriaUsuarioUseCaseImpl criaUsuarioUseCase(
            CriaUsuarioGateway criaUsuarioGateway
    ) {
        return new CriaUsuarioUseCaseImpl(criaUsuarioGateway);
    }
}
