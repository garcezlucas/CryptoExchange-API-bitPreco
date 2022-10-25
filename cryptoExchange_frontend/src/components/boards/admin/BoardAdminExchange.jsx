import React, { useState, useEffect, useMemo, useRef } from "react";
import ExchangeDataService from "../../../services/Exchange.service";
import { useTable } from "react-table";
import { useNavigate } from 'react-router-dom';

const BoardAdmin = props => {
    const [exchanges, setExchanges] = useState([]);
    const [searchExchange, setSearchExchange] = useState("");
    const exchangesRef = useRef();

    const history = useNavigate();

    exchangesRef.current = exchanges;

    useEffect(() => {
        retrieveExchanges();
    }, []);

    const onChangeSearchExchange = (e) => {
        const searchExchange = e.target.value;
        setSearchExchange(searchExchange);
    };

    const retrieveExchanges = () => {
        ExchangeDataService.getAllExchange()
            .then((response) => {
                setExchanges(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const refreshList = () => {
        retrieveExchanges();
    };

    const removeAllExchanges = () => {
        ExchangeDataService.deleteAll()
            .then((response) => {
                console.log(response.data);
                refreshList();
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const findByExchange = () => {
        ExchangeDataService.getExchangeByExchange(searchExchange)
            .then((response) => {
                setExchanges(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const openExchange = (rowIndex) => {
        const id = exchangesRef.current[rowIndex].id;
        history("/editexchanges/" + id);

    };

    const deleteExchange = (rowIndex) => {         
        const id = exchangesRef.current[rowIndex].id;

        ExchangeDataService.deleteExchangeById(id)
            .then((response) => {

                let newExchanges = [...exchangesRef.current];
                newExchanges.splice(rowIndex, 1);

                setExchanges(newExchanges);
                refreshList();
            })
            .catch((e) => {
                console.log(e);
            });
            
    };

    const columns = useMemo(
        () => [
            {
                Header: "Usuário",
                accessor: "user.username",
            },
            {
                Header: "CriptoMoeda",
                accessor: "market",
            },
            {
                Header: "Tipo de Transação",
                accessor: "exchange",
            },
            {
                Header: "Valor",
                accessor: "value",
            },
            {
                Header: "Quantidade",
                accessor: "amount",
            },
            {
                Header: "Data",
                accessor: "date",
            },
            {
                Header: "",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return (
                        <div>
                            <span onClick={() => openExchange(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteExchange(rowIdx)}>
                                <i className="fas fa-trash action mr-2"></i>
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
        data: exchanges,
    });

    return (
        <div className="list row">
            <div className="col-md-8">
                <div className="input-group mb-3">
                    <input
                        type="text"
                        className="search-bar"
                        placeholder="Procurar por Transações"
                        value={searchExchange}
                        onChange={onChangeSearchExchange}
                    />
                    <div className="input-group-append">
                        <button
                        className="search-button"
                        data-toggle="button"
                        type="button"
                        onClick={findByExchange}
                        >
                        Procurar
                        </button>
                    </div>
                </div>
            </div>
            <div className="table-bdr">
                <div className="table-responsive">
                    <table
                        className= "table table-striped table-hover"
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
                    className="btn-delete mr-2" 
                    data-toggle="button"
                    onClick={removeAllExchanges}
                >
                    Deletar Transações
                </button>
                <button 
                    className="btn-all mr-2" 
                    data-toggle="button"
                    onClick={retrieveExchanges}
                >
                    Todas Transações
                </button>
            </div>
        </div>
    );
};

export default BoardAdmin;