import React, { useState, useEffect, useRef } from "react";
import CoinDataService from "../../../services/Coin.service";
import { withRouter } from "../../../common/with-router";
import { useNavigate } from 'react-router-dom';
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

const required = (value) => {
    if (!value) {
        return (
            <div className="invalid-feedback d-block">
                Este campo não pode ficar em branco!
            </div>
        );
    }
};


const EditCoins = props => {

    const date = new Date().toLocaleString();

    const form = useRef();
    const checkBtn = useRef();

    const initialCoinstate = {
        id: null,
        market: "",
        buy: 0,
        sell: 0,
        timestamp: "",
    };

    const [currentCoin, setCurrentCoin] = useState(initialCoinstate);
    const [message, setMessage] = useState("");
    const [successful, setSuccessful] = useState(false);

    const history = useNavigate();

    useEffect(() => {
        getCoin(
            props.router.params.id
    )}, [props.router.params.id]);

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

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentCoin({ ...currentCoin, [name]: value });
    };

    const deleteCoin = () => {
        CoinDataService.deleteCoinById(currentCoin.id)
        .then(response => {
            setMessage(response.data.message);
            setSuccessful(true);
        })
        setTimeout(function() {
            window.location.href = "/admincoins";
        }, 500)
        .catch(e => {
            console.log(e);
        });
    };

    const handleCoin = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        // form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            CoinDataService.updateCoinById(currentCoin.id, currentCoin).then(
                (response) => {
                    setMessage(response.data.message);
                    setSuccessful(true);

                },
                
                setTimeout(function() {
                    window.location.href = "/admincoins";
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
                <Form onSubmit={handleCoin} ref={form}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="market">CriptoMoeda</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="market"
                                    value={currentCoin.market}
                                    onChange={handleInputChange}
                                //     validations={required}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="exchange">Preçode Compra</label>
                                <Input
                                    type="number"
                                    className="form-control"
                                    name="buy"
                                    value={currentCoin.buy}
                                    onChange={handleInputChange}
                                    // validations={required}
                                />
                            </div>
                                
                            <div className="form-group">
                                <label htmlFor="value">Preço de Venda</label>
                                <Input
                                    type="number"
                                    className="form-control"
                                    name="sell"
                                    value={currentCoin.sell}
                                    onChange={handleInputChange}
                                    // validations={required}
                                />
                            </div>
                                
                            <div className="form-group">
                                <label htmlFor="amount">Hora da ultima atualização</label>
                                <Input
                                    className="form-control"
                                    type="text"
                                    name="timestamp"
                                    value={date}
                                    onChange={handleInputChange}
                                    disabled label = "true"
                                />
                            </div>

                            <div>
                                <button 
                                    className="btn badge-pill badge-danger mr-2" 
                                    data-toggle="button" 
                                    onClick={deleteCoin}
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

export default withRouter(EditCoins);