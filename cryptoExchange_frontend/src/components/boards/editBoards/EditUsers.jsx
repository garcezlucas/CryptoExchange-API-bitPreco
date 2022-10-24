import React, { useState, useEffect, useRef} from "react";
import UserDataService from "../../../services/User.service";
import { withRouter } from "../../../common/with-router";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";

const required = (value) => {
    if (!value) {
        return (
            <div className="invalid-feedback d-block">
                Este campo não pode ficar em branco!
            </div>
        );
    }
};

const validEmail = (value) => {
    if (!isEmail(value)) {
        return (
            <div className="invalid-feedback d-block">
                Este não é um email válido.
            </div>
        );
    }
};

const vusername = (value) => {
    if (value.length < 3 || value.length > 20) {
        return (
            <div className="invalid-feedback d-block">
                O nome de usuário deve conter entre 3 e 25 caracteres.
            </div>
        );
    }
};

const vpassword = (value) => {
    if (value.length < 6 ) {
        return (
            <div className="invalid-feedback d-block">
                A senha deve conter entre 6 e 40 caracteres.
            </div>
        );
    }
};

const EditUsers = props => {
    
    const form = useRef();
    const checkBtn = useRef();

    // const currentUsers = UserDataService.roles();

    const initialUserstate = {
        id: null,
        username: "",
        email: "",
        password: "",
        roles: []
        };
    const [currentUser, setCurrentUser] = useState(initialUserstate);
    const [message, setMessage] = useState("");
    const [successful, setSuccessful] = useState(false);


    useEffect(() => {
        getUser(
            props.router.params.id
    )}, [props.router.params.id]);

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

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentUser({ ...currentUser, [name]: value });
    };

    const deleteUser = () => {
        UserDataService.deleteUserById(currentUser.id)
        .then(response => {
            setMessage(response.data.message);
            setSuccessful(true);
        })
        setTimeout(function() {
            window.location.href = "/adminusers";
        }, 500)
        .catch(e => {
            console.log(e);
        });
    };

    const handleUser = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            UserDataService.updateUserById(currentUser.id, currentUser).then(
                (response) => {
                    setMessage(response.data.message);
                    setSuccessful(true);
                },
                setTimeout(function() {
                    window.location.href = "/adminusers";
                }, 500),

                (error) => {
                    const resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();

                    setMessage(resMessage);
                    setSuccessful(false);
                }
            );
        }
    };

    return (
        <div className="col-md-12">
            <div className="card card-container">
                <Form onSubmit={handleUser} ref={form}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="market">Usuário</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="username"
                                    value={currentUser.username}
                                    onChange={handleInputChange}
                                    validations={[required, vusername]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="exchange">Email</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="email"
                                    value={currentUser.email}
                                    onChange={handleInputChange}
                                    validations={[required, validEmail]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="amount">Senha</label>
                                <Input
                                    className="form-group"
                                    type="password"
                                    name="password"
                                    value={currentUser.password}
                                    onChange={handleInputChange}
                                    validations={[required, vpassword]}
                                />
                            </div>
                                
                            <div  className="form-group">
                                <label htmlFor="amount">Permisão</label>
                                <strong>
                                    <p>
                                        { currentUser.roles && 
                                            currentUser.roles.map(roles => <p>{roles.name}</p>)}
                                    </p>
                                </strong>
                            </div>

                            <div>
                                <button 
                                    className="btn badge-pill badge-danger mr-2"
                                    data-toggle="button" 
                                    onClick={deleteUser}
                                >
                                    Deletar
                                </button>

                                <button
                                    className="btn badge-pill badge-success mr-2" 
                                    data-toggle="button"
                                >
                                    Atualizar
                                </button>
                            </div>
                        </div>
                    )}
                    
                    {message && (
                        <div className="form-group">
                            <div
                                className={
                                    successful ? "alert alert-success" : "alert alert-danger"
                                }
                                role="alert"
                            >
                                {message}
                            </div>
                        </div>
                    )}
                    <CheckButton style={{ display: "none" }} ref={checkBtn} />
                </Form>
            </div>
        </div>
    );
};

export default withRouter(EditUsers);