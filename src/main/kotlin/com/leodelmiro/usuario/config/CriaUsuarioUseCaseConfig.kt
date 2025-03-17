package com.leodelmiro.usuario.config

import com.leodelmiro.usuario.core.dataprovider.CriaUsuarioGateway
import com.leodelmiro.usuario.core.usecase.impl.CriaUsuarioUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CriaUsuarioUseCaseConfig {
    @Bean
    fun criaUsuarioUseCase(
        criaUsuarioGateway: CriaUsuarioGateway
    ): CriaUsuarioUseCaseImpl {
        return CriaUsuarioUseCaseImpl(criaUsuarioGateway)
    }
}
