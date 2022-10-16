import { useState, useRef } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import ExchangeService from "../services/Exchange.service";
import InputBase from "./InputBase";

const BuyForm = (props) => {

    const form = useRef();
    const checkBtn = useRef();

    const [market, setMarket] = useState("");
    const [exchange, setExchange] = useState("");
    const [value, setValue] = useState("");
    const [amount, setAmount] = useState("");
    const [date, setDate] = useState("");
    
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    const onChangeMarket = (e) => {
        const market = e.target.value;
        setMarket(market);
    };

    const onChangeExchange = (e) => {
        const exchange = e.target.value;
        setExchange(exchange);
    };

    const onChangeValue = (e) => {
        const value = e.target.value;
        setValue(value);
    };

    const onChangeAmount = (e) => {
        const amount = e.target.value;
        setAmount(amount);
    };    

    const onChangeDate = (e) => {
        const date = e.target.value;
        setDate(date);
    };
    
    const handleExchange = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            ExchangeService.exchangeInsert(market, exchange, value, amount, date ).then(
                (response) => {
                    setMessage(response.data.message);
                    setSuccessful(true);
                },
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
                                <label>CriptoMoeda</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="exchange_market"
                                    value={market}
                                    onChange={onChangeMarket}
                                />
                            </div>

                            <div className="form-group">
                                <label>Tipo de transação</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="exchange"
                                    value={exchange}
                                    onChange={onChangeExchange}
                                />
                            </div>

                            <div className="form-group">
                                <label>Valor Transação</label>
                                {/* entrada de dados do valor em reais */}
                                <InputBase 
                                className="brl-input"
                                name="amount" 
                                label="BRL"
                                value ={amount}
                                onChange={onChangeAmount}
                                />
                            </div>

                            <div className="form-group">
                                <label>Quantidade de moedas</label>
                                {/* resultado da conversão do valor em reais pela taxa de câmbio da criptomoeda  */}
                                <input 
                                    className="form-control"
                                    name="amount"
                                    value={exchange.converted} 
                                    placeholder={market}                                    
                                    disabled label={market}
                                />

                                {/* <Input
                                    type="text"
                                    className="form-control"
                                    name="amount"
                                    value={amount}
                                    onChange={onChangeAmount}
                                /> */}
                            </div>

                            <div className="form-group">
                                <label>Data da transação</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="date"
                                    value={date}
                                    onChange={onChangeDate}
                                />
                            </div>

                            

                            <div className="form-group">
                                <button className="btn btn-primary btn-block">Comprar</button>
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

export default BuyForm;