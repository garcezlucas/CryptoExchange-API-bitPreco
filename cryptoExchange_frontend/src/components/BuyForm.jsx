import { useState, useRef, useEffect, refresh } from "react";
import Form from "react-validation/build/form";
import CheckButton from "react-validation/build/button";
import { useNavigate } from "react-router-dom";

import ExchangeService from "../services/Exchange.service";
import CoinDataService  from "../services/Coin.service";

const required = (value) => {
    if (!value) {
        return (
            <div className="invalid-feedback d-block">
                Campo não pode estar em branco!
            </div>
        );
    }
};

const InputBase = ({label, ...props}) => (
    <>
        <input type="number" {...props} placeholder={label}/>
    </>
)

const BuyForm = () => {


    const current = new Date();
    const date = `${current.getDate()}/${current.getMonth()+1}/${current.getFullYear()}`;

    const form = useRef();
    const checkBtn = useRef();
    

    const [market, setMarket] = useState("");
    const [exchange, setExchange] = useState("");
    const [value, setValue] = useState("");
    const [amount, setAmount] = useState("");
    const [coin, setCoin] = useState("");
    const [price, setPrice] = useState(0);
    const [total, setTotal] = useState("");
    const [rate, setRate] = useState("");
    
    
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    const navigate = useNavigate();

    


    useEffect(() => {
        if (exchange !== "venda") {
            setRate(coin.buy)
            
        } else {
            setRate(coin.sell)

        }
    });

    useEffect(() => {
        setTotal({
            ...total,
            converted: Number(amount / rate).toFixed(8)
        })
    },[]);

    useEffect(() => {
        if (exchange !== "venda") {
            setValue(coin.buy)
            
        } else {
            setValue(coin.sell)
        }
    });

    const onChangeMarket = (e) => {
        const market = e.target.value;
        setMarket(market);
        localStorage.setItem("Market",JSON.stringify(market));

        CoinDataService.getCoin( e.target.value);

        getInfoCoin(e.target.value)
    };

    const getInfoCoin = () => {
        CoinDataService.getInfosCoin(JSON.parse(localStorage.getItem("Market")))
        .then(() => {
            setCoin(JSON.parse(localStorage.getItem("Coin")));
            
        })
        .catch((e) => {
            console.log(e);
        });
    }

    const onChangeExchange = (e) => {
        const exchange = e.target.value;
        setExchange(exchange);
    };

    const onChangeAmount = (e) => {
        const amount = e.target.value;
        setAmount(amount);
    };

    const handleChange = ({target: {value}}) => {
        const val = Number(value.trim())
        const converted = (val/rate).toFixed(8)
        const price = converted
        setPrice(price)
        setTotal(
            {
                converted:converted
            }
        )
    }

    const handleExchange = (e) => {
        e.preventDefault();

        setMessage("");
        setSuccessful(false);

        form.current.validateAll();

        if (checkBtn.current.context._errors.length === 0) {
            ExchangeService.exchangeInsert(market, exchange, value, amount, price, date ).then(
                () => {
                    navigate("/buy");
                    window.location.reload();
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
                                    <select
                                        type="text"
                                        className="form-control"
                                        name="exchange_market"
                                        value={market}
                                        onChange={onChangeMarket}
                                        validations={[required]}
                                    >
                                        <option select = "true" value = "Default" hidden> Selecione a criptomoeda </option>
                                        <option select = "true" value = "btc"> BTC </option>
                                        <option select = "true" value = "eth"> ETH </option>
                                        <option select = "true" value = "ada"> ADA </option>



                                    </select>

                                </div>

                                <div className="form-group">
                                    <label>Valor da criptomoeda</label>
                                    <input
                                        type="number"
                                        className="form-control"
                                        name="value"
                                        value={value}
                                        placeholder = "R$"
                                        disabled label = "true"
                                    />
                                </div>
                                

                                <div className="form-group">
                                    <label>Tipo de transação</label>
                                    <select                                    
                                        type="text"
                                        className="form-control"
                                        name="exchange"
                                        value={exchange}
                                        onChange={onChangeExchange}
                                        validations={[required]}
                                    >
                                        <option select = "true" value = "Default" hidden >Selecione a Transação</option>
                                        <option select = "true" value = "compra">Comprar</option>
                                        <option select = "true" value = "venda">Vender</option>
                                    </select>
                                    
                                </div>

                                <div className="form-group">
                                    <label>Valor Transação</label>
                                    <InputBase
                                    type="number"
                                    className="brl-input"
                                    name="amount" 
                                    label="BRL"
                                    value ={amount}
                                    onChange={onChangeAmount}
                                    onClick={handleChange}
                                    validations={[required]}
                                    />
                                </div>

                                <div className="form-group">
                                    <label>Quantidade de criptomoedas</label>
                                    <input 
                                        type="number"
                                        className="form-control"
                                        name="price"
                                        value={total.converted}                             
                                        disabled label ="true"
                                    />
                                </div>

                                <div className="form-group">
                                    <label>Data da transação</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="date"
                                        value={date}
                                        disabled label = "true"
                                    />
                                </div>

                                

                                <div className="form-group">
                                    <button className="btn btn-primary btn-block" ref={checkBtn}>Realizar transação</button>
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
}

export default BuyForm;
