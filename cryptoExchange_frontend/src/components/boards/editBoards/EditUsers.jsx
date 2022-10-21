import React, { useState, useEffect } from "react";
import UserDataService from "../../../services/User.service";
import { useNavigate, useLocation } from 'react-router-dom';

const EditUsers = () => {
    const initialUserstate = {
        id: null,
        username: "",
        email: "",
        password: "",
    };
    const [currentUser, setCurrentUser] = useState(initialUserstate);
    const [message, setMessage] = useState("");

    const history = useNavigate();
    const params = useLocation();


    const getUser = id => {
        UserDataService.getUserById(id)
        .then(response => {
            setCurrentUser(response.data);
            console.log(response.data);
        })
        .catch(e => {
            console.log(e);
        });
    };

    useEffect(() => {
        getUser(
            console.log(params.id)
    )}, [console.log(params.id)]);
    

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentUser({ ...currentUser, [name]: value });
    };

    const updateUser = () => {
        UserDataService.updateUserById(currentUser.id, currentUser)
        .then(response => {
            console.log(response.data);
            setMessage("A transação foi atualizada com sucesso!");
        })
        .catch(e => {
            console.log(e);
        });
    };

    const deleteUser = () => {
        UserDataService.deleteUserById(currentUser.id)
        .then(response => {
            console.log(response.data);
            history("/adminusers");
        })
        .catch(e => {
            console.log(e);
        });
    };

    return (
        <div>
        {currentUser ? (
            <div className="edit-form">
            <h4>User</h4>
            <form>
                <div className="form-group">
                <label htmlFor="market">Usuário</label>
                <input
                    type="text"
                    className="form-control"
                    name="username"
                    value={currentUser.username}
                    onChange={handleInputChange}
                />
                </div>
                <div className="form-group">
                <label htmlFor="exchange">Email</label>
                <input
                    type="text"
                    className="form-control"
                    name="email"
                    value={currentUser.email}
                    onChange={handleInputChange}
                />
                </div>

                <div className="form-group">
                <label htmlFor="amount">Senha</label>
                <input
                    className="form-control"
                    type="text"
                    name="password"
                    value={currentUser.password}
                    onChange={handleInputChange}
                />
                </div>
                
                <div className="form-group">
                <label htmlFor="value">Roles</label>
                <input
                    type="text"
                    className="form-control"
                    name="roles"
                    value={currentUser.roles}
                    onChange={handleInputChange}
                />
                </div>

            </form>


            <button className="badge badge-danger mr-2" onClick={deleteUser}>
                Deletar
            </button>

            <button
                type="submit"
                className="badge badge-success"
                onClick={updateUser}
            >
                Atualizar
            </button>
            <p>{message}</p>
            </div>
        ) : (
            <div>
            <br />
            <p>Por favor clique em uma transação...</p>
            </div>
        )}
        </div>
    );
};

export default EditUsers;