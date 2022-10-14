import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

// const getPublicContent = () => {
//     return axios.get(API_URL + "users/");
// };

const getUserBoard = () => {
    return axios.get(API_URL + "user");
};

const getPremiumBoard = () => {
    return axios.get(API_URL + "premium");
};

const getAdminBoard = () => {
    return axios.get(API_URL + "admin");
};

const UserService = {
    // getPublicContent,
    getUserBoard,
    getPremiumBoard,
    getAdminBoard,
}

export default UserService;

