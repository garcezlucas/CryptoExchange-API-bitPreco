import React, { useState, useEffect } from "react";
import ExchangeDataService from "../../../services/Exchange.service";
import { useNavigate, useLocation } from 'react-router-dom';

const EditExchanges = () => {
    const initialExchangeState = {
        id: null,
        market: "",
        exchange: "",
        value: 0,
        amount: 0,
        price: 0,
        date: "",
    };
    const [currentExchange, setCurrentExchange] = useState(initialExchangeState);
    const [message, setMessage] = useState("");

    const history = useNavigate();
    const params = useLocation();


    const getExchange = id => {
        ExchangeDataService.getExchangeById(id)
        .then(response => {
            setCurrentExchange(response.data);
            console.log(response.data);
        })
        .catch(e => {
            console.log(e);
        });
    };

    useEffect(() => {
        getExchange(
            console.log(params.id)
    )}, [console.log(params.id)]);
    

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentExchange({ ...currentExchange, [name]: value });
    };

    const updateExchange = () => {
        ExchangeDataService.updateExchangeById(currentExchange.id, currentExchange)
        .then(response => {
            console.log(response.data);
            setMessage("A transação foi atualizada com sucesso!");
        })
        .catch(e => {
            console.log(e);
        });
    };

    const deleteExchange = () => {
        ExchangeDataService.deleteExchangeById(currentExchange.id)
        .then(response => {
            console.log(response.data);
            history("/adminexchanges");
        })
        .catch(e => {
            console.log(e);
        });
    };

    return (
        <div>
        {currentExchange ? (
            <div className="edit-form">
            <h4>Exchange</h4>
            <form>
                <div className="form-group">
                <label htmlFor="market">CriptoMoeda</label>
                <input
                    type="text"
                    className="form-control"
                    name="market"
                    value={currentExchange.market}
                    onChange={handleInputChange}
                />
                </div>
                <div className="form-group">
                <label htmlFor="exchange">Tipo de Transação</label>
                <input
                    type="text"
                    className="form-control"
                    name="exchange"
                    value={currentExchange.exchange}
                    onChange={handleInputChange}
                />
                </div>
                
                <div className="form-group">
                <label htmlFor="value">Valor da Cripto</label>
                <input
                    type="number"
                    className="form-control"
                    name="value"
                    value={currentExchange.value}
                    onChange={handleInputChange}
                />
                </div>
                
                <div className="form-group">
                <label htmlFor="amount">Valor em Reais</label>
                <input
                    className="form-control"
                    type="number"
                    name="amount"
                    value={currentExchange.amount}
                    onChange={handleInputChange}
                />
                </div>

                <div className="form-group">
                <label htmlFor="price">Quantidade de Cripto</label>
                <input
                    className="form-control"
                    type="number"
                    name="price"
                    value={currentExchange.price}
                    onChange={handleInputChange}
                />
                </div>
                
                <div className="form-group">
                <label htmlFor="date">Data</label>
                <input
                    type="text"
                    className="form-control"
                    name="date"
                    value={currentExchange.date}
                    onChange={handleInputChange}
                />
                </div>

                
            </form>


            <button className="badge badge-danger mr-2" onClick={deleteExchange}>
                Deletar
            </button>

            <button
                type="submit"
                className="badge badge-success"
                onClick={updateExchange}
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

export default EditExchanges;