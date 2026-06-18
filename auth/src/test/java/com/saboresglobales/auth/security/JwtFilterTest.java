package com.saboresglobales.auth.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtFilter jwtFilter;

    @Test
    void cuandoNoHayHeader_debeContinuarSinAutenticar() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtUtil, never()).validarToken(any());
    }

    @Test
    void cuandoHeaderNoBeareer_debeContinuarSinAutenticar() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic token123");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtUtil, never()).validarToken(any());
    }

    @Test
    void cuandoTokenValido_debeAutenticarYContinuar() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token.valido.jwt");
        when(jwtUtil.validarToken("token.valido.jwt")).thenReturn(true);
        when(jwtUtil.obtenerCorreo("token.valido.jwt")).thenReturn("juan@test.com");
        when(jwtUtil.obtenerRol("token.valido.jwt")).thenReturn("CLIENTE");

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtUtil).validarToken("token.valido.jwt");
    }

    @Test
    void cuandoTokenInvalido_debeContinuarSinAutenticar() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Bearer token.invalido");
        when(jwtUtil.validarToken("token.invalido")).thenReturn(false);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}