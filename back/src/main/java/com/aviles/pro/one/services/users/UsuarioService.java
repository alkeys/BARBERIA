package com.aviles.pro.one.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aviles.pro.one.models.users.Usuario;
import com.aviles.pro.one.repositories.user.UsuarioRepository;
import com.aviles.pro.one.security.CustomUserDetails;
import com.aviles.pro.one.utils.service.ServiceAbstract;
import com.aviles.pro.one.utils.utils.HashPassword;

@Service("usuarioService")
public class UsuarioService extends ServiceAbstract<Usuario, Long> implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected JpaRepository<Usuario, Long> getRepository() {
        return usuarioRepository;
    }

    /***
     * aqui se inyecta el usuario autenticado
     * 
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        return new CustomUserDetails(usuario);
    }

    /***
     * aqui se inyecta el usuario autenticado
     * 
     * @param entity
     * @return
     */
    @Override
    public Usuario save(Usuario entity) {
        entity.setPasswordHash(HashPassword.hashPassword(entity.getPasswordHash()));
        return super.save(entity);
    }

    @Override
    public Usuario update(Long id, Usuario entity) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // si la entidad no es nula, actualiza los campos
        if (entity != null) {

            // si la contraseña esta hasheada no la hashea otra vez
            if (entity.getPasswordHash().startsWith("$2a$")) {
                usuario.setPasswordHash(entity.getPasswordHash());
            } else {
                // si la contraseña no tiene pass no la hashea
                if (!entity.getPasswordHash().equals("")) {
                    usuario.setPasswordHash(HashPassword.hashPassword(entity.getPasswordHash()));
                }
            }

            usuario.setNombreCompleto(entity.getNombreCompleto());
            usuario.setUsuario(entity.getUsuario());
            usuario.setRole(entity.getRole());
            usuario.setActivo(entity.getActivo());
        }
        return super.update(id, usuario);
    }

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}
