package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuarios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //System.out.println("El filtro está siendo llamado");  // probando

        //obtener el token del header
        // var token = request.getHeader("Authorization").replace("Bearer", "");
        // System.out.println(token);

        var autHeader = request.getHeader("Authorization");

        if (autHeader != null) {
            System.out.println("Validamos que el token no es null");
            var token = autHeader.replace("Bearer ", "");
            //System.out.println(token);
            //System.out.println(tokenService.getSubject(token));  // Para saber si este usuario tiene sesion
            var nombreUsuario = tokenService.getSubject(token); //extract username
            if (nombreUsuario != null) {
                //token valido
                var usuario = usuarioRepository.findByLogin(nombreUsuario
                );
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); // forzamos un inicio de sesion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);  // permite acseso a los filtros en cadena
    }
}
