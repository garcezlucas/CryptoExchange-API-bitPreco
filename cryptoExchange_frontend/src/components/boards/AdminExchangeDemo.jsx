import React, { Component } from 'react';
import ExchangeDataService from '../../services/Exchange.service';
import { Link } from 'react-router-dom';

export default class AdminExchange extends Component {

    constructor(props) {

        super(props);

        this.onChangeSearchExchange = this.onChangeSearchExchange.bind(this);
        this.retrieveExchanges = this.retrieveExchanges.bind(this);
        this.refreshList = this.refreshList.bind(this);
        this.searchExchange = this.searchExchange.bind(this);
        this.setActiveExchange = this.setActiveExchange.bind(this);

        this.state = {
            exchanges: [],
            currentExchange: null,
            currentIndex: -1,
            searchExchange: ""
        };
    }

    componentDidMount() {
        this.retrieveExchanges();
    }

    onChangeSearchExchange(e) {

        const searchExchange = e.target.value;

        this.setState({
            searchExchange: searchExchange
        })

    }

    retrieveExchanges() {
        ExchangeDataService.getAllExchange()
            .then(
                response => {
                    this.setState({
                        exchanges: response.data
                    });
                    console.log("DATA: ", response.data);
                }
            ).
            catch(e => {
                console.log(e);
            })
    }

    refreshList() {
        this.retrieveExchanges();
        this.setState({
            currentExchange: null,
            currentIndex: -1
        });
    }

    setActiveExchange(exchange, index) {

        this.setState({
            currentExchange: exchange,
            currentIndex: index
        })

    }

    // removeAllExchanges() {
    //     ExchangeDataService.deleteAll()
    //         .then(
    //             response => {
    //                 this.setState({
    //                     exchanges: response.data
    //                 });
    //                 console.log("DATA: ", response.data);
    //             }
    //         ).
    //         catch(e => {
    //             console.log(e);
    //         })
    // }

    searchExchange() {
        
        this.setState({
            currentIndex: -1,
            currentExchange: null,
        })

        ExchangeDataService.getExchangeByExchange(this.state.searchExchange)
            .then(
                response => {
                    this.setState({
                        exchanges: response.data
                    });
                    console.log(response.data);
                }
            )
            .catch(e => {
                console.log(e)
            });
    }

    render() {

        const { searchExchange, exchanges, currentExchange, currentIndex } = this.state;

        return (
            <div className="list row">
                <div className="col-md-8">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            placeholder='Busca por nome de usuário'
                            className="form-control"
                            value={searchExchange}
                            onChange={this.onChangeSearchExchange}
                        />
                        <div className="input-group-append">
                            <button
                                className="btn btn-outline-secondary"
                                type="button"
                                onClick={this.searchExchange}
                            >
                                Buscar
                            </button>
                        </div>
                    </div>
                </div>
                <div className='col-md-6'>
                    <h4>exchanges list</h4>
                    <ul className="list-group">
                        {
                            exchanges &&
                            exchanges.map((exchange, index) =>( 
                                <li
                                    className={
                                        "list-exchange " +
                                        (index === currentIndex ? "active" : "")
                                    }
                                    onClick={() => this.setActiveExchange(exchange, index)}
                                    key={index}
                                >
                                    {exchanges.name}
                                </li>
                            ))
                        }
                    </ul>
                </div>

                <div className="col-md-6">

                    {
                        currentExchange ? (
                            <div>
                                <h4>Item</h4>
                                <div>
                                    <label htmlFor="name">Usuário</label>
                                    {" "}
                                    {currentExchange.username}
                                </div>
                                <div>
                                    <label htmlFor="name">Market</label>
                                    {" "}
                                    {currentExchange.market}
                                </div>
                                <div>
                                    <label htmlFor="name">Exchange</label>
                                    {" "}
                                    {currentExchange.exchange}
                                </div>
                                <div>
                                    <label htmlFor="name">Valor</label>
                                    {" "}
                                    {currentExchange.value}
                                </div>
                                <div>
                                    <label htmlFor="name">Qunatidade</label>
                                    {" "}
                                    {currentExchange.amount}
                                </div>
                                <div>
                                    <label htmlFor="price">Price</label>
                                    {" "}
                                    {currentExchange.price}
                                </div>
                                <div>
                                    <label htmlFor="name">Data</label>
                                    {" "}
                                    {currentExchange.date}
                                </div>
                                <Link
                                    to={"/editexchanges/" + currentExchange.id}
                                    className="btn btn-warning"
                                >
                                    Editar
                                </Link>
                            </div>

                        ) : (
                            <div>
                                <br />
                                <p>Clique em um item</p>
                            </div>
                        )
                    }

                </div>

            </div>
        )

    }

}