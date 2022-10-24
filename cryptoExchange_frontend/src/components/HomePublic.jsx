import React from "react";

import outra from "../images/homee.png"

const Home = () => {


    return (
        <>
        <div className="container-homepublic">
            <div className="container-text">
                <div class="jumbotron">
                    <h1 class="display-4">Venha para o futuro!</h1>
                    <p class="lead">Não perca tempo, conquiste sua liberdade financeira investindo em Criptomoedas.</p>
                    <hr class="my-4"/>
                    <p>Entre com sua conta ou crie um cadastro</p>
                    <a class="btn badge-pill badge-primary mr-2" href="/login" role="button">Entrar</a>
                    <a class="btn badge-pill badge-primary mr-2" href="/register" role="button">Cadastrar</a>
                </div>
            </div>
            <div>
                <img src={outra} alt="icon" className='coin-icon' />
                
            </div>
        </div>
        </>
    );
};

export default Home;