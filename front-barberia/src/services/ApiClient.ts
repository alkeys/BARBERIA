import axios from "axios";

/** URL base de la API */
const apiUrl = "http://localhost:8080/api/";

/** Instancia preconfigurada de Axios */
export const apiClient = axios.create({
	baseURL: apiUrl,
	headers: { "Content-Type": "application/json" },
    //para que haga la autenticacion mediante cookies
    withCredentials: true,
});



