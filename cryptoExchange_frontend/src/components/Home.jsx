import React from "react";
import { useNavigate } from 'react-router-dom';


import imageHome from "../images/home.jpg"

const Home = () => {

    const history = useNavigate();

    const buy = () => {
        history("/buy");
    }

    return (
        <>
        <div className="container-home">
            <div class="jumbotron">
                <div className="card-text">
                    <h1 class="display-4">Bem vindo ao LibertyExchange!</h1>
                    <p class="lead"></p>
                    <hrb class="my-4"/>
                    <em>Não perca tempo, conquiste sua liberdade financeira investindo em Criptomoedas.</em>
                    <hr class="my-4"/>
                    <button class="btn-new mr-2" onClick={buy}>Investir Agora</button>
                </div>
            </div>
            <div>
                <img src={imageHome} alt="icon" className='coin-home' />
                
            </div>
        </div>
        </>
    );
};

export default Home;