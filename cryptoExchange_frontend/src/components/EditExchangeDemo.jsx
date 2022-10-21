import React, { Component } from 'react';
import ExchangeDataService from "../services/Exchange.service";
import { withRouter } from "../common/with-router";

class EditExchange extends Component {


    constructor(props) {

        super(props);
        this.onChangeMarket = this.onChangeMarket.bind(this);
        this.onChangeExchange = this.onChangeExchange.bind(this);
        this.onChangeValue = this.onChangeValue.bind(this);
        this.onChangeAmount = this.onChangeAmount.bind(this);
        this.onChangePrice = this.onChangePrice.bind(this);
        this.onChangeDate = this.onChangeDate.bind(this);       

        this.getExchange = this.getExchange.bind(this);
        this.updateExchange = this.updateExchange.bind(this);
        this.deleteExchange = this.deleteExchange.bind(this);

        this.state = {
            currentExchange: {
                id: null,
                market: "",
                exchange: "",
                value: 0,
                amount: 0,
                price: 0,
                date: "",
                status: false
            },
            message: ""
        };
    }

    componentDidMount() {
        this.getExchange(this.props.router.params.id);
    }

    onChangeMarket(e) {

        const market = e.target.value;

        this.setState(prevState => ({
            currentExchange: {
                ...prevState.currentExchange,
                market: market
            }
        }));
    };

    onChangeExchange(e) {

        const exchange = e.target.value;

        this.setState(prevState => ({
            currentExchange: {
                ...prevState.currentExchange,
                exchange: exchange
            }
        }));
    };

    onChangeValue(e) {

        const value = e.target.value;

        this.setState(prevState => ({
            currentExchange: {
                ...prevState.currentExchange,
                value: value
            }
        }));
    };

    onChangeAmount(e) {

        const amount = e.target.value;

        this.setState(prevState => ({
            currentExchange: {
                ...prevState.currentExchange,
                amount: amount
            }
        }));
    };

    onChangePrice(e) {

        const price = e.target.value;

        this.setState(prevState => ({
            currentExchange: {
                ...prevState.currentExchange,
                price: price
            }
        }));
    };

    onChangeDate(e) {

        const date = e.target.value;

        this.setState(prevState => ({
            currentExchange: {
                ...prevState.currentExchange,
                date: date
            }
        }));
    };

    getExchange(id) {

        ExchangeDataService.getExchangeById(id)
            .then(
                response => {
                    this.setState({
                        currentExchange: response.data
                    });
                    console.log(response.data)
                }
            )
            .catch(e => {
                console.log(e);
            });
    }

    updateExchange() {

        ExchangeDataService.updateExchangeById(
            this.state.currentExchange.id,
            this.state.currentExchange
        ).then(response => {
            console.log(response.data);
            this.setState({
                message: "Item atualizado com sucesso"
            })
            this.props.router.navigate("/adminexchanges")
        })
            .catch(e => {
                console.log(e);
            });

    }

    deleteExchange() {
        ExchangeDataService.deleteExchangeById(this.state.currentExchange.id)
            .then(
                response => {
                    console.log(response.data);
                    this.props.router.navigate("/adminexchanges");
                }
            )
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentExchange } = this.state;

        // this.state.currentItem.id,
        // this.state.currentItem.name,
        // this.state.currentItem.username,
        // this.state.currentItem.price,
        // this.state.currentItem.status,
        // this.state.currentItem.description

        return (
            <div>
                {
                    currentExchange ? (
                        <div className="col-md-12">
                            <div className="card card-container">
                                <h4>Exchange</h4>
                                <form >

                                    <div className='form-group'>
                                        <label htmlFor="name">Criptomoeda</label>
                                        <input
                                            type="text"
                                            className='form-control'
                                            id='market'
                                            value={currentExchange.market}
                                            onChange={this.onChangeMarket}
                                        />
                                    </div>
                                    <div className='form-group'>
                                        <label htmlFor="username">Tipo de Transação</label>
                                        <input
                                            type="text"
                                            className='form-control'
                                            id='exchange'
                                            value={currentExchange.exchange}
                                            onChange={this.onChangeExchange}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="value">Valor da Cripto</label>
                                        <input
                                            type="number"
                                            className="form-control"
                                            name="value"
                                            value={currentExchange.value}
                                            onChange={this.onChangeValue}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="amount">Valor em Reais</label>
                                        <input
                                            className="form-control"
                                            type="number"
                                            name="amount"
                                            value={currentExchange.amount}
                                            onChange={this.onChangeAmount}
                                        />
                                    </div>
                                    <div className='form-group'>
                                        <label htmlFor="price">Quantidade de Cripto</label>
                                        <input
                                            type="number"
                                            className='form-control'
                                            id='price'
                                            value={currentExchange.price}
                                            onChange={this.onChangePrice}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="date">Data</label>
                                        <input
                                            type="text"
                                            className="form-control"
                                            name="date"
                                            value={currentExchange.date}
                                            onChange={this.onChangeDate}
                                        />
                                    </div>
                                    

                                </form>
                                <button
                                    className="btn badge-pill badge-danger mr-2"
                                    onClick={this.deleteExchange}
                                >
                                    Deletar
                                </button>
                                <button
                                    type="submit"
                                    className="btn badge-pill badge-success"
                                    onClick={this.updateExchange}
                                >
                                    Atualizar
                                </button>
                                <p>{this.state.message}</p>
                            </div>
                        </div>
                    ):(
                        <div>
                            <br />
                            <p>Click on an item</p>
                        </div>
                    )
                }
            </div>
        )
    }
}

export default withRouter(EditExchange);