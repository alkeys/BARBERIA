/**
 * Modelo de Citas
 */
export interface Citas {
    id: number;
    cliente_id: number;
    barbero_id: number;
    servicio_id: number;
    fecha: string;
    hora: string;
    estado: string;
    observaciones: string;
}