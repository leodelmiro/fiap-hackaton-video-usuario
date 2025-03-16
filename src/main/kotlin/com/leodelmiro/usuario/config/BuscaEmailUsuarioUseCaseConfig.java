package com.leodelmiro.usuario.config;

import com.leodelmiro.usuario.core.dataprovider.BuscaEmailUsuarioGateway;
import com.leodelmiro.usuario.core.dataprovider.CriaUsuarioGateway;
import com.leodelmiro.usuario.core.usecase.impl.BuscaEmailUsuarioUseCaseImpl;
import com.leodelmiro.usuario.core.usecase.impl.CriaUsuarioUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuscaEmailUsuarioUseCaseConfig {

    @Bean
    public BuscaEmailUsuarioUseCaseImpl buscaEmailUsuarioUseCase(
            BuscaEmailUsuarioGateway buscaEmailUsuarioGateway
    ) {
        return new BuscaEmailUsuarioUseCaseImpl(buscaEmailUsuarioGateway);
    }
}
