import type { Authenticate } from "../models/Authenticate";
import { apiClient } from "./ApiClient";


export const authenticate = (username: string, password: string) => {
    return apiClient.post<Authenticate>("authenticate", { usuario: username, password });
}

///api/logout
export const logout = () => {
    return apiClient.post("logout");
}

//api/refresh-token
export const refreshToken = () => {
    return apiClient.post("refresh-token");
}

//api/validate-token
export const validateToken = () => {
    return apiClient.post("validate-token");
}
