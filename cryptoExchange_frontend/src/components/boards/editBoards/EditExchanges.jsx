import React, { useState, useEffect, useRef } from "react";
import ExchangeDataService from "../../../services/Exchange.service";
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

const EditExchanges = props => {

    const date = new Date().toLocaleString();

    const form = useRef();
    const checkBtn = useRef();

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
    const [successful, setSuccessful] = useState(false);

    const history = useNavigate();

    useEffect(() => {
        getExchange(
            props.router.params.id
        )}, [props.router.params.id]);

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

    const handleInputChange = event => {
        const { name, value } = event.target;
        setCurrentExchange({ ...currentExchange, [name]: value });
    };

    const deleteExchange = () => {
        ExchangeDataService.deleteExchangeById(currentExchange.id)
        .then(response => {
            setMessage(response.data.message);
            setSuccessful(true);
        })
        setTimeout(function() {
            window.location.href = "/adminexchanges";
        }, 500)
        .catch(e => {
            console.log(e);
        });
    };

    const handleExchange = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            ExchangeDataService.updateExchangeById(currentExchange.id, currentExchange).then(
                (response) => {
                    setMessage(response.data.message);
                    setSuccessful(true);

                },
                
                setTimeout(function() {
                    window.location.href = "/adminexchanges";
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
                <Form onSubmit={handleExchange} ref={form}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="market">CriptoMoeda</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="market"
                                    value={currentExchange.market}
                                    onChange={handleInputChange}
                                    // validations={required}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="exchange">Tipo de Transação</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="exchange"
                                    value={currentExchange.exchange}
                                    onChange={handleInputChange}
                                    // validations={required}
                                />
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="value">Valor da Cripto</label>
                                <Input
                                    type="number"
                                    className="form-control"
                                    name="value"
                                    value={currentExchange.value}
                                    onChange={handleInputChange}
                                    // validations={required}
                                />
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="amount">Valor em Reais</label>
                                <Input
                                    className="form-control"
                                    type="number"
                                    name="amount"
                                    value={currentExchange.amount}
                                    onChange={handleInputChange}
                                    // validations={required}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="price">Quantidade de Cripto</label>
                                <Input
                                    className="form-control"
                                    type="number"
                                    name="price"
                                    value={currentExchange.price}
                                    onChange={handleInputChange}
                                    // validations={required}
                                />
                            </div>
                            
                            <div className="form-group">
                                <label htmlFor="date">Data</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="date"
                                    value={date}
                                    onChange={handleInputChange}
                                    disabled label = "true"
                                />
                            </div>

                            <div>
                                <button 
                                    className="btn badge-pill badge-danger mr-2" 
                                    data-toggle="button" 
                                    onClick={deleteExchange}
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

export default withRouter(EditExchanges);