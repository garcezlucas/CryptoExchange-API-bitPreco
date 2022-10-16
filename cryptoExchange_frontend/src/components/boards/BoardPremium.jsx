// import React, { useState, useEffect } from "react";

// import UserService from "../../services/User.service";
// import EventBus from "../../common/EventBus";

// const BoardPremium = () => {
//     const [content, setContent] = useState("");

//     useEffect(() => {
//         UserService.getPremiumBoard().then(
//             (response) => {
//                 setContent(response.data);
//             },
//             (error) => {
//                 const _content =
//                     (error.response &&
//                         error.response.data &&
//                         error.response.data.message) ||
//                     error.message ||
//                     error.toString();

//                 setContent(_content);

//                 if (error.response && error.response.status === 401) {
//                     EventBus.dispatch("logout");
//                 }
//             }
//         );
//     }, []);

//     return (
//         <div className="container">
//             <header className="jumbotron">
//                 <h3>{content}</h3>
//             </header>
//         </div>
//     );
// };

// export default BoardPremium;




import React, { useState, useEffect, useMemo, useRef } from "react";
import ExchangeDataService from "../../services/Exchange.service";
import { useTable } from "react-table";

const ExchangeList = (props) => {
    const [exchanges, setExchanges] = useState([]);
    const [searchExchange, setSearchExchange] = useState("");
    const exchangesRef = useRef();

    // exchangesRef.current = exchanges;

    useEffect(() => {
        retrieveExchanges();
    }, []);

    const onChangeSearchExchange = (e) => {
        const searchExchange = e.target.value;
        setSearchExchange(searchExchange);
    };

    const retrieveExchanges = () => {
        ExchangeDataService.getExchange()
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
        ExchangeDataService.removeAll()
            .then((response) => {
                console.log(response.data);
                refreshList();
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const findByExchange = () => {
        ExchangeDataService.findByExchange(searchExchange)
            .then((response) => {
                setExchanges(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const openExchange = (rowIndex) => {
        const id = exchangesRef.current[rowIndex].id;

        props.history.push("/exchanges/" + id);
    };

    const deleteExchangeById = (rowIndex) => {
        const id = exchangesRef.current[rowIndex].id;

        ExchangeDataService.remove(id)
            .then((response) => {
                props.history.push("/exchanges/delete");

                let newExchanges = [...exchangesRef.current];
                newExchanges.splice(rowIndex, 1);

                setExchanges(newExchanges);
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
                Header: "Tipo Transação",
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
                Header: "Status",
                accessor: "published",
                Cell: (props) => {
                    return props.value ? "Published" : "Pending";
                },
            },
            {
                Header: "Actions",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return (
                        <div>
                            <span onClick={() => openExchange(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteExchangeById(rowIdx)}>
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
        data: exchanges,
    });

    return (
        <div className="list row">
            <div className="col-md-8">
                <div className="input-group mb-3">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search by title"
                        value={searchExchange}
                        onChange={onChangeSearchExchange}
                    />
                    <div className="input-group-append">
                        <button
                        className="btn btn-outline-secondary"
                        type="button"
                        onClick={findByExchange}
                        >
                        Search
                        </button>
                    </div>
                </div>
            </div>
            <div className="col-md-12 list">
                <table
                    className="table table-striped table-bordered"
                    {...getTableProps()}
                >
                    <thead>
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

            <div className="col-md-8">
                <button className="btn btn-sm btn-danger" onClick={removeAllExchanges}>
                    Remove All
                </button>
            </div>
        </div>
    );
};

export default ExchangeList;