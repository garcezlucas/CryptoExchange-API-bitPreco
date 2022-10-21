import api from "../api/api";

class UserDataService {

    getAllUsers () {
        return api.get("/auth/users/list/all");
        
    };

    getUserByUsername (username) {
        return api.get(`/auth/users/fins/${username}`);
    };

    getUserById (id) {
        return api.get(`/auth/users/${id}`);
    };

    updateUserById (id, user) {
        return api.put(`/auth/users/update/${id}`, user);
    };

    deleteUserById (id) {
        return api.delete(`/auth/users/delete/${ id }`);
    };

}


export default new UserDataService;


