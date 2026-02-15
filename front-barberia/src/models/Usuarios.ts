/**
 * Modelo de Roles
 */
export interface Usuarios {
    id: number;
    role_id: number;
    nombre_completo: string;
    usuario: string;
    password_hash?: string;
    activo: boolean;
    fecha_creacion: string;
}