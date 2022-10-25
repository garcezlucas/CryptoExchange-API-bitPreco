import React, { useState, useEffect, useMemo, useRef } from "react";
import UserDataService from "../../../services/User.service";
import { useTable } from "react-table";
import { useNavigate } from 'react-router-dom';


const BoardUser = props => {
    const [users, setUsers] = useState([]);
    const [searchUser, setSearchUser] = useState("");
    const usersRef = useRef();

    const history = useNavigate();

    usersRef.current = users;

    useEffect(() => {
        retrieveUsers();
    }, []);

    const onChangeSearchUser = (e) => {
        const searchUser = e.target.value;
        setSearchUser(searchUser);
    };

    const retrieveUsers = () => {
        UserDataService.getAllUsers()
            .then((response) => {
                setUsers(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const refreshList = () => {
        retrieveUsers();
    };

    const findByUser = () => {
        UserDataService.getUserByUsername(searchUser)
            .then((response) => {
                setUsers(response.data)
            })
            .catch((e) => {
                console.log(e);
            });
    };

    const openUser = (rowIndex) => {
        const id = usersRef.current[rowIndex].id;
        history("/editusers/" + id);
    };

    const createUser = () => {
        history("/createuser");
    }

    const deleteUser = (rowIndex) => {         
        const id = usersRef.current[rowIndex].id;

        UserDataService.deleteUserById(id)
            .then((response) => {

                let newUsers = [...usersRef.current];
                newUsers.splice(rowIndex, 1);

                setUsers(newUsers);
                refreshList();
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
                Header: "",
                accessor: "actions",
                Cell: (props) => {
                    const rowIdx = props.row.id;
                    return (
                        <div>
                            <span onClick={() => openUser(rowIdx)}>
                                <i className="far fa-edit action mr-2"></i>
                            </span>

                            <span onClick={() => deleteUser(rowIdx)}>
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
        data: users

    });

    return (
        <div className="list row">
            <div className="col-md-8">
                <div className="search">
                    <div className="input-group mb-3">
                        <input
                            type="text"
                            className="search-bar"
                            placeholder="Procurar por usuário"
                            value={searchUser}
                            onChange={onChangeSearchUser}
                        />
                        <div className="input-group-append">
                            <button
                            className="search-button"
                            data-toggle="button"
                            type="button"
                            onClick={findByUser}
                            >
                            Procurar
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div className="table-bdr">
                <div className="table-responsive">
                    <table
                        className= "table table-striped table-hover"
                        {...getTableProps()}
                    >
                        <thead className="table-head">
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
                    className="btn-all mr-2" 
                    data-toggle="button"
                    onClick={retrieveUsers}
                >
                    Todos Usuários
                </button>
                <button
                    className="btn-new mr-2" 
                    data-toggle="button"
                    onClick={createUser}
                >
                    Criar novo usuário
                </button>
            </div>
        </div>
    );
};

export default BoardUser;