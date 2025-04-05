package com.leodelmiro.usuario.entrypoint.api

import com.leodelmiro.usuario.core.usecase.BuscaEmailUsuarioUseCase
import com.leodelmiro.usuario.core.usecase.CriaUsuarioUseCase
import com.leodelmiro.usuario.core.usecase.EntrarUsuarioUseCase
import com.leodelmiro.usuario.entrypoint.api.request.CriaUsuarioRequest
import com.leodelmiro.usuario.entrypoint.api.request.EntrarUsuarioRequest
import com.leodelmiro.usuario.entrypoint.api.response.BuscaEmailUsuarioResponse
import com.leodelmiro.usuario.entrypoint.api.response.CriaUsuarioResponse
import com.leodelmiro.usuario.entrypoint.api.response.TokenResponse
import com.leodelmiro.usuario.entrypoint.controller.UsuarioController
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder


@Tag(name = "Usuário", description = "Endpoints relacionados ao Usuário")
@RestController
@RequestMapping("/api/v1/usuarios")
class UsuarioApi(
    @field:Autowired private val criaUsuarioUseCase: CriaUsuarioUseCase,
    @field:Autowired private val buscaEmailUsuarioUseCase: BuscaEmailUsuarioUseCase,
    @field:Autowired private val entrarUsuarioUseCase: EntrarUsuarioUseCase

    ) {

    @Operation(
        summary = "Cria Usuário",
        description = "Criação de um novo usuário"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "201", description = "Usuário criado com sucesso")])
    @PostMapping
    fun cria(
        @RequestBody @Valid request: CriaUsuarioRequest
    ): ResponseEntity<CriaUsuarioResponse> {
        val response: CriaUsuarioResponse =
            UsuarioController.criar(request, criaUsuarioUseCase)
        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(response.id).toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @Operation(
        summary = "Buscar Email Usuário",
        description = "Busca email de um usuário"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")])
    @GetMapping("/{usuario}")
    fun buscarEmail(
        @PathVariable usuario: String
    ): ResponseEntity<BuscaEmailUsuarioResponse> {
        val email = buscaEmailUsuarioUseCase.executar(usuario)
        return ResponseEntity.ok().body(BuscaEmailUsuarioResponse(usuario, email))
    }

    @Operation(
        summary = "Entrar com Usuário",
        description = "Entrar com usuário e senha"
    )
    @ApiResponses(value = [ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")])
    @PostMapping("/entrar")
    fun entrar(
        @RequestBody @Valid request: EntrarUsuarioRequest
    ): ResponseEntity<TokenResponse> {
        val token = UsuarioController.entrar(request, entrarUsuarioUseCase)
        return ResponseEntity.ok().body(token)
    }
}