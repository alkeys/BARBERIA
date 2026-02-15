import { apiClient } from "./ApiClient";



//obtener todos los barberos /barberos/getAll
export const getBarberos = () => {
    return apiClient.get("barberos/getAll");
}

//obtener un barbero por id /barberos/getById/{id}
export const getBarberoById = (id: number) => {
    return apiClient.get(`barberos/getById/${id}`);
}


