import React, { useState, useEffect, useMemo } from "react";
import CoinsDataService from "../../../services/Coin.service";
import { useTable } from "react-table";

const CoinsList = (props) => {
    const [coins, setCoins] = useState([]);
    

    useEffect(() => {
        retrieveCoins();
    }, []);

    const retrieveCoins = () => {
        CoinsDataService.getAllCoins()
            .then((response) => {
                setCoins(response.data);
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
        
        <div className="col-md-12 list">
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
    );


    
};

export default CoinsList;




