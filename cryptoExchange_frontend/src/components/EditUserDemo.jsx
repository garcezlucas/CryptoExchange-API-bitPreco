import React, { Component } from 'react';
import UserDataService from "../services/User.service";
import { withRouter } from "../common/with-router";

class EditUser extends Component {


    constructor(props) {

        super(props);
        this.onChangeUsername = this.onChangeUsername.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);

        this.getUser = this.getUser.bind(this);
        this.updateUser = this.updateUser.bind(this);
        this.deleteUser = this.deleteUser.bind(this);

        this.state = {
            currentUser: {
                id: null,
                username: "",
                email: "",
                password: "",
                roles: "",
            },
            message: ""
        };
    }

    componentDidMount() {
        this.getUser(this.props.router.params.id);
    }

    onChangeUsername(e) {

        const username = e.target.value;

        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                username: username
            }
        }));
    };

    onChangeEmail(e) {

        const email = e.target.value;

        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                email: email
            }
        }));
    };

    onChangePassword(e) {

        const password = e.target.value;

        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                password: password
            }
        }));
    };

    onChangeRoles(e) {

        const roles = e.target.value;

        this.setState(prevState => ({
            currentUser: {
                ...prevState.currentUser,
                roels: roles
            }
        }));
    };
    
    getUser(id) {

        UserDataService.getUserById(id)
            .then(
                response => {
                    this.setState({
                        currentUser: response.data
                    });
                    console.log(response.data)
                }
            )
            .catch(e => {
                console.log(e);
            });
    }

    updateUser() {

        UserDataService.updateUserById(
            this.state.currentUser.id,
            this.state.currentUser
        ).then(response => {
            console.log(response.data);
            this.setState({
                message: "Item atualizado com sucesso"
            })
            this.props.router.navigate("/adminusers")
        })
            .catch(e => {
                console.log(e);
            });

    }

    deleteUser() {
        UserDataService.deleteUserById(this.state.currentUser.id)
            .then(
                response => {
                    console.log(response.data);
                    this.props.router.navigate("/adminusers");
                }
            )
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentUser } = this.state;

        return (
            <div>
                {
                    currentUser ? (
                        <div className="col-md-12">
                            <div className="card card-container">
                                <h4>User</h4>
                                <form>

                                    <div className='form-group'>
                                        <label htmlFor="name">Usuário</label>
                                        <input
                                            type="text"
                                            className='form-control'
                                            id='username'
                                            value={currentUser.username}
                                            onChange={this.onChangeUsername }
                                        />
                                    </div>
                                    <div className='form-group'>
                                        <label htmlFor="username">Email</label>
                                        <input
                                            type="text"
                                            className='form-control'
                                            id='email'
                                            value={currentUser.email}
                                            onChange={this.onChangeEmail}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="value">Senha</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="password"
                                            value={currentUser.password}
                                            onChange={this.onChangePassword}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="amount">Permissões</label>
                                        <input
                                            className="form-control"
                                            type="text"
                                            name="roles"
                                            value={currentUser.roles}
                                            onChange={this.onChangeRoles}
                                        />
                                    </div>

                                </form>
                                <button
                                    className="btn badge-pill badge-danger mr-2"
                                    onClick={this.deleteUser}
                                >
                                    Deletar
                                </button>
                                <button
                                    type="submit"
                                    className="btn badge-pill badge-success"
                                    onClick={this.updateUser}
                                >
                                    Atualizar
                                </button>
                                <p>{this.state.message}</p>
                            </div>
                        </div>
                    ):(
                        <div>
                            <br />
                            <p>Click on an item</p>
                        </div>
                    )
                }
            </div>
        )
    }
}

export default withRouter(EditUser);