import React from "react";

import AlienCoin from "../images/AlienCoin.png"

const Home = () => {


    return (
        <>
        <div className="container-home">
            <div className="container-text">
                <div class="jumbotron">
                    <h1 class="display-4">Venha para o futuro!</h1>
                    <p class="lead"></p>
                    <hr class="my-4"/>
                    <p>Não perca tempo, conquiste sua liberdade financeira investindo em Criptomoedas.</p>
                </div>
            </div>
            <div>
                <img src={AlienCoin} alt="icon" className='coin-icon' />
                
            </div>
        </div>
        </>
    );
};

export default Home;