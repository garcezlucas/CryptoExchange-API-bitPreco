import api from "../api/api";
import TokenService from "./Token.service";

const register = (username, email, password) => {
    return api.post("/auth/signup", {
        username,
        email,
        password
    });
};

const login = (username, password) => {
    return api
        .post("/auth/signin", {
            username,
            password
        })
        .then((response) => {
            if (response.data.username) {
                // TokenService.setUser(response.data);
                localStorage.setItem("user", JSON.stringify(response.data));
            }

            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem("user");
};

const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
    register,
    login,
    logout,
    getCurrentUser,
};

export default AuthService;