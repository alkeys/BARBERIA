import { useState } from "react";
import { authenticate } from "../services/Authenticate";


export const Login = () => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = () => {
       const Response=authenticate(username, password).then((response) => {
           console.log(response.data);
       });
       console.log(Response);
    };

    return (
        <div>
            <h1>Login</h1>
            <div>
                <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} />
                <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />
                <button onClick={handleSubmit}>Login</button>
            </div>
        </div>
    );
}