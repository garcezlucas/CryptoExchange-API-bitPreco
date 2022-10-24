import React, { useState, useEffect, useMemo, useRef } from "react";
import CoinDataService from "../../../services/Coin.service";
import { useTable } from "react-table";
import { useNavigate } from 'react-router-dom';

const BoardAdmin = (props) => {
    const [coins, setCoins] = useState([]);
    const [searchCoin, setSearchCoin] = useState("");
    const coinsRef = useRef();

    const history = useNavigate();
    


    coinsRef.current = coins;

    useEffect(() => {
        retrieveCoins();
    }, []);

    const onChangeSearchCoin = (e) => {
        const searchCoin = e.target.value;
        setSearchCoin(searchCoin);
    };

    const retrieveCoins = () => {
        CoinDataService.getAllCoins()
            .then((response) => {
                setCoins(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const refreshList = () => {
        retrieveCoins();
    };

    const removeAllCoins = () => {
        CoinDataService.deleteAll()
            .then((response) => {
                console.log(response.data);
                refreshList();
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const findByCoin = () => {
        CoinDataService.findCoinByMarket(searchCoin)
            .then((response) => {
                setCoins(response.data);
                
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const openCoin = (rowIndex) => {
        const id = coinsRef.current[rowIndex].id;
        history("/editcoins/" + id, {
            state:{
                id
            }
        });
    };

    const deleteCoin = (rowIndex) => {         
        const id = coinsRef.current[rowIndex].id;

        CoinDataService.deleteCoinById(id)
            .then((response) => {

                refreshList();

                let newCoins = [...coinsRef.current];
                newCoins.splice(rowIndex, 1);

                setCoins(newCoins);
                refreshList();
            })
            .catch((e) => {
                console.log(e);
            });
            
    };

    const columns = useMemo(
        () => [
            {
                Header: "CriptoMoeda",
                accessor: "market",
            },
            {
                Header: "Preço de Compra",
                accessor: "buy",
            },
            {
                Header: "Preço de Venda",
                accessor: "sell",
            },
            {
                Header: "Hora da ultima atualização",
                accessor: "timestamp",
            },
            {
                Header: "",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return (
                        <div>
                            <span onClick={() => openCoin(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteCoin(rowIdx)}>
                                <i className="fas fa-trash action"></i>
                            </span>
                        </div>
                    );
                },
            },
        ],
        []
    );

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = useTable({
        columns,
        data: coins,
    });

    return (
        <div className="list row">
            <div className="col-md-8">
                <div className="input-group mb-3">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Procurar por criptomoeda"
                        value={searchCoin}
                        onChange={onChangeSearchCoin}
                    />
                    <div className="input-group-append">
                        <button
                        className="btn btn-outline-secondary"
                        data-toggle="button"
                        type="button"
                        onClick={findByCoin}
                        >
                        Procurar
                        </button>
                    </div>
                </div>
            </div>
            <div className="col-md-12 list">
                <div className="tbl-container bdr">
                    <table
                        className="table"
                        class= "table tabletable-striped"
                        {...getTableProps()}
                    >
                        <thead class="table-head">
                            {headerGroups.map((headerGroup) => (
                                <tr {...headerGroup.getHeaderGroupProps()}>
                                    {headerGroup.headers.map((column) => (
                                        <th {...column.getHeaderProps()}>
                                            {column.render("Header")}
                                        </th>
                                    ))}
                                </tr>
                            ))}
                        </thead>
                        <tbody {...getTableBodyProps()}>
                            {rows.map((row, i) => {
                                prepareRow(row);
                                    return (
                                        <tr {...row.getRowProps()}>
                                            {row.cells.map((cell) => {
                                                return (
                                                    <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                                                );
                                            })}
                                        </tr>
                                    );
                            })}
                        </tbody>
                    </table>
                </div>
            </div>

            <div className="col-md-8">
                <button 
                    className="btn badge-pill badge-danger mr-2"  
                    data-toggle="button"
                    onClick={removeAllCoins}
                >
                    Deletar Criptomoedas
                </button>
                <button 
                    className="btn badge-pill badge-info mr-2" 
                    data-toggle="button"
                    onClick={retrieveCoins}
                >
                    Todas Criptomoedas
                </button>
            </div>
        </div>
    );
};

export default BoardAdmin;