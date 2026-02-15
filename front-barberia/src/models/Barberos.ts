/*
 * Modelo de Barberos
 */

export default interface Barberos {
    id: number;
    nombre: string;
    telefono: string;
    activo: boolean;
    disponible: boolean;
    id_usuario: number;
}