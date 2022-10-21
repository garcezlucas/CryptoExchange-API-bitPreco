import React, { Component } from 'react';
import CoinDataService from "../services/Coin.service";
import { withRouter } from "../common/with-router";

class EditCoin extends Component {


    constructor(props) {

        super(props);
        this.onChangeMarket = this.onChangeMarket.bind(this);
        this.onChangeBuy = this.onChangeBuy.bind(this);
        this.onChangeSell = this.onChangeSell.bind(this);
        this.onChangeTimestamp = this.onChangeTimestamp.bind(this);

        this.getCoin = this.getCoin.bind(this);
        this.updateCoin = this.updateCoin.bind(this);
        this.deleteCoin = this.deleteCoin.bind(this);

        this.state = {
            currentCoin: {
                id: null,
                market: "",
                buy: 0,
                sell: 0,
                timestamp: "",
                    },
            message: ""
        };
    }

    componentDidMount() {
        this.getCoin(this.props.router.params.id);
    }

    onChangeMarket(e) {

        const market = e.target.value;

        this.setState(prevState => ({
            currentCoin: {
                ...prevState.currentCoin,
                market: market
            }
        }));
    };

    onChangeBuy(e) {

        const buy = e.target.value;

        this.setState(prevState => ({
            currentCoin: {
                ...prevState.currentCoin,
                buy: buy
            }
        }));
    };

    onChangeSell(e) {

        const sell = e.target.value;

        this.setState(prevState => ({
            currentCoin: {
                ...prevState.currentCoin,
                sell: sell
            }
        }));
    };

    onChangeTimestamp(e) {

        const timestamp = e.target.value;

        this.setState(prevState => ({
            currentCoin: {
                ...prevState.currentCoin,
                timestamp: timestamp
            }
        }));
    };

    getCoin(id) {

        CoinDataService.getCoinById(id)
            .then(
                response => {
                    this.setState({
                        currentCoin: response.data
                    });
                    console.log(response.data)
                }
            )
            .catch(e => {
                console.log(e);
            });
    }

    updateCoin() {

        CoinDataService.updateCoinById(
            this.state.currentCoin.id,
            this.state.currentCoin
        ).then(response => {
            console.log(response.data);
            this.setState({
                message: "Item atualizado com sucesso"
            })
            this.props.router.navigate("/admincoins")
        })
            .catch(e => {
                console.log(e);
            });

    }

    deleteCoin() {
        CoinDataService.deleteCoinById(this.state.currentCoin.id)
            .then(
                response => {
                    console.log(response.data);
                    this.props.router.navigate("/admincoins");
                }
            )
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        const { currentCoin } = this.state;

        // this.state.currentItem.id,
        // this.state.currentItem.name,
        // this.state.currentItem.username,
        // this.state.currentItem.price,
        // this.state.currentItem.status,
        // this.state.currentItem.description

        return (
            <div>
                {
                    currentCoin ? (
                        <div className="col-md-12">
                            <div className="card card-container">
                                <h4>Coin</h4>
                                <form >

                                    <div className='form-group'>
                                        <label htmlFor="name">Criptomoeda</label>
                                        <input
                                            type="text"
                                            className='form-control'
                                            id='market'
                                            value={currentCoin.market}
                                            onChange={this.onChangeMarket}
                                        />
                                    </div>
                                    <div className='form-group'>
                                        <label htmlFor="username">Preço de Venda</label>
                                        <input
                                            type="number"
                                            className='form-control'
                                            id='buy'
                                            value={currentCoin.buy}
                                            onChange={this.onChangeBuy}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="value">Preço de Compra</label>
                                        <input
                                            type="number"
                                            className="form-control"
                                            name="sell"
                                            value={currentCoin.sell}
                                            onChange={this.onChangeSell}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="amount">Horário da ultima atualização</label>
                                        <input
                                            className="form-control"
                                            type="text"
                                            name="timestamp"
                                            value={currentCoin.timestamp}
                                            onChange={this.onChangeTimestamp}
                                        />
                                    </div>

                                </form>
                                <button
                                    className="btn badge-pill badge-danger mr-2"
                                    onClick={this.deleteCoin}
                                >
                                    Deletar
                                </button>
                                <button
                                    type="submit"
                                    className="btn badge-pill badge-success"
                                    onClick={this.updateCoin}
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

export default withRouter(EditCoin);