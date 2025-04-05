package com.leodelmiro.usuario.config

import com.leodelmiro.usuario.core.dataprovider.BuscaEmailUsuarioGateway
import com.leodelmiro.usuario.core.usecase.impl.BuscaEmailUsuarioUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BuscaEmailUsuarioUseCaseConfig {
    @Bean
    fun buscaEmailUsuarioUseCase(
        buscaEmailUsuarioGateway: BuscaEmailUsuarioGateway
    ): BuscaEmailUsuarioUseCaseImpl {
        return BuscaEmailUsuarioUseCaseImpl(buscaEmailUsuarioGateway)
    }
}