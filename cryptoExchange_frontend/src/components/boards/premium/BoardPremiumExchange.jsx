import React, { useState, useEffect, useMemo } from "react";
import ExchangeDataService from "../../../services/Exchange.service";
import { useTable } from "react-table";

const ExchangeList = (props) => {
    const [exchanges, setExchanges] = useState([]);
    

    useEffect(() => {
        retrieveExchanges();
    }, []);

    const retrieveExchanges = () => {
        ExchangeDataService.getAllExchange()
            .then((response) => {
                setExchanges(response.data);
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
        
        <div className="col-md-12 list">
            <div className="tbl-container bdr">
                <table
                    className="table"
                    class= "table table-striped "
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
                        ))
                        }
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
    );


    
};

export default ExchangeList;




