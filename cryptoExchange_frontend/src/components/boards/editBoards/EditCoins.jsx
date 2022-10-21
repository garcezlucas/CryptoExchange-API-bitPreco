import React, { useState, useEffect } from "react";
import CoinDataService from "../../../services/Coin.service";
import { useNavigate, useLocation } from 'react-router-dom';

const EditCoins = () => {
    const initialCoinstate = {
        id: null,
        market: "",
        buy: 0,
        sell: 0,
        timestamp: "",
    };
    const [currentCoin, setCurrentCoin] = useState(initialCoinstate);
    const [message, setMessage] = useState("");

    const history = useNavigate();
    const params = useLocation();


    const getCoin = id => {
        CoinDataService.getCoinById(id)
        .then(response => {
            setCurrentCoin(response.data);
            console.log(response.data);
        })
        .catch(e => {
            console.log(e);
        });
    };

    useEffect(() => {
        getCoin(
            console.log(params.id)
    )}, [console.log(params.id)]);
    

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentCoin({ ...currentCoin, [name]: value });
    };

    const updateCoin = () => {
        CoinDataService.updateCoinById(currentCoin.id, currentCoin)
        .then(response => {
            console.log(response.data);
            setMessage("A transação foi atualizada com sucesso!");
        })
        .catch(e => {
            console.log(e);
        });
    };

    const deleteCoin = () => {
        CoinDataService.deleteCoinById(currentCoin.id)
        .then(response => {
            console.log(response.data);
            history("/admincoins");
        })
        .catch(e => {
            console.log(e);
        });
    };

    return (
        <div>
        {currentCoin ? (
            <div className="col-md-12">
                <div className="card card-container">
                    <h4>Coin</h4>
                    <form>
                        <div className="form-group">
                        <label htmlFor="market">CriptoMoeda</label>
                        <input
                            type="text"
                            className="form-control"
                            name="market"
                            value={currentCoin.market}
                            onChange={handleInputChange}
                        />
                        </div>
                        <div className="form-group">
                        <label htmlFor="exchange">Preçode Compra</label>
                        <input
                            type="number"
                            className="form-control"
                            name="buy"
                            value={currentCoin.buy}
                            onChange={handleInputChange}
                        />
                        </div>
                        
                        <div className="form-group">
                        <label htmlFor="value">Preço de Venda</label>
                        <input
                            type="number"
                            className="form-control"
                            name="sell"
                            value={currentCoin.sell}
                            onChange={handleInputChange}
                        />
                        </div>
                        
                        <div className="form-group">
                        <label htmlFor="amount">Hora da ultima atualização</label>
                        <input
                            className="form-control"
                            type="text"
                            name="timestamp"
                            value={currentCoin.timestamp}
                            onChange={handleInputChange}
                        />
                        </div>

                    </form>


                    <button className="badge badge-danger mr-2" onClick={deleteCoin}>
                        Deletar
                    </button>

                    <button
                        type="submit"
                        className="badge badge-success"
                        onClick={updateCoin}
                    >
                        Atualizar
                    </button>
                    <p>{message}</p>
                </div>
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

export default EditCoins;