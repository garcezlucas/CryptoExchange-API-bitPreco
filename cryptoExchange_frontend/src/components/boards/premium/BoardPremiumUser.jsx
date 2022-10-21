import React, { useState, useEffect, useMemo } from "react";
import UserDataService from "../../../services/User.service";
import { useTable } from "react-table";

const UserList = (props) => {
    const [users, setUsers] = useState([]);
    

    useEffect(() => {
        retrieveUsers();
    }, []);

    const retrieveUsers = () => {
        UserDataService.getAllUsers()
            .then((response) => {
                setUsers(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    
        const columns = useMemo(
            () => [
            {
                Header: "Id",
                accessor: "id",
            },
            {
                Header: "Usuário",
                accessor: "username",
            },
            {
                Header: "Email",
                accessor: "email",
            },
            {
                Header: "Roles",
                accessor: "roles.name",
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
        data: users,
    });

    return (
        
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

export default UserList;




