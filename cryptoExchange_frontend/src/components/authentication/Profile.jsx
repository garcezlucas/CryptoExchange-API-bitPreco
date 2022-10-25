import React from "react";
import AuthService from "../../services/Auth.service";

const Profile = () => {
    const currentUser = AuthService.getCurrentUser();

    return (
        <div className="container-profile">
            <header 
                className="jumbotron"
                >
                <h3 className="title-profile">
                    <strong>{currentUser.username}</strong> Perfil
                </h3>
            </header>
            <p>
                <strong>Token: </strong> 
                {currentUser.accessToken.value.substr(currentUser.accessToken)}
            </p>
            <p>
                <strong>Refresh Token: </strong>
                {currentUser.refreshToken.substr(currentUser.refreshToken)}
            </p>
            <p>
                <strong>Id:</strong> {currentUser.id}
            </p>
            <p>
                <strong>Email:</strong> {currentUser.email}
            </p>
            <strong>Permissões:</strong>
            <ul>
                {currentUser.roles &&
                    currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
            </ul>
        </div>
    );
};

export default Profile;