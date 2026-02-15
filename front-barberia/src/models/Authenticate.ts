import type { Usuarios } from "./Usuarios";

/**
 * modelo de autenticacion
{
  "horaExpiracion": "Sun Feb 15 21:47:27 CST 2026",
  "rolUsuario": "ROLE_ADMIN",
  "usuario": {
    "id": 1,
    "role": {
      "id": 1,
      "nombre": "ADMIN",
      "descripcion": "Descripción del rol"
    },
    "nombreCompleto": "Alexander aviles",
    "usuario": "aviles",
    "activo": true,
    "fechaCreacion": "2026-02-15T09:47:15.791136"
  }
} 
*/
export interface Authenticate {
    horaExpiracion: Date;
    rolUsuario: string;
    usuarioObj: Usuarios;
    nombreCompleto: string;
    usuario: string;
    activo: boolean;
    fechaCreacion: Date;
}