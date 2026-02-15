import { apiClient } from "./ApiClient";
import type { Usuarios } from "../models/Usuarios";


///api/usuarios/getAll
export const getAllUsuarios = () => {
    return apiClient.get<Usuarios[]>("usuarios/getAll");
}

///api/usuarios/getById/{id}
export const getUsuarioById = (id: number) => {
    return apiClient.get<Usuarios>("usuarios/getById/" + id);
}


///api/usuarios/activar/{username} 
export const activarUsuario = (username: string) => {
    return apiClient.get("usuarios/activar/" + username);
}

///api/usuarios/desactivar/{username}
export const desactivarUsuario = (username: string) => {
    return apiClient.get("usuarios/desactivar/" + username);
}

////api/usuarios/save
export const saveUsuario = (usuario: Usuarios) => {
    return apiClient.post("usuarios/save", usuario);
}


///api/usuarios/update/{id}
export const updateUsuario = (id: number, usuario: Usuarios) => {
    return apiClient.put("usuarios/update/" + id, usuario);
}