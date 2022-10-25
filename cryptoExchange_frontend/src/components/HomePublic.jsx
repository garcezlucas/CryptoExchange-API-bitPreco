import React from "react";
import { useNavigate } from 'react-router-dom';

import outra from "../images/homee.png"

const Home = () => {

    const history = useNavigate();

    const signin = () => {
        history("/login");
    }

    const register = () => {
        history("/register");
    }

    return (
        <>
        <div className="container-homepublic">
            <div class="jumbotron">
                <div className="container-text">
                    <h1 class="display-4">Venha para o futuro!</h1>
                    <p class="lead"></p>
                    <em class="lead">Não perca tempo, conquiste sua liberdade financeira investindo em Criptomoedas.</em>
                    <hr class="my-4"/>
                    <p>Entre com sua conta ou crie um cadastro</p>
                    <button class="btn-new mr-2" onClick={signin}>Entrar</button>
                    <button class="btn-new mr-2" onClick={register}>Registrar</button>
                </div>
            </div>
            <div>
                <img src={outra} className='coin-icon'  alt="icon"  />
                
            </div>
        </div>
        </>
    );
};

export default Home;