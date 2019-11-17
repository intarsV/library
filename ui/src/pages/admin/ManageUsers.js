import React, {useContext, useEffect} from 'react';
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";
import {AdminContext} from "../../context/AdminContext";

const ManageUsers = () => {

    const {adminData, dispatch} = useContext(AdminContext);

    useEffect(() => {
            AdminService.getUsers()
                .then(
                    response => {
                        dispatch({type: 'USERS_DATA', payload: {usersData: response.data}});
                    }
                )
        }, []
    );

    const changeUserStatus = (id) => {
        AdminService.changeUserStatus({id: id})
            .then(
                response => {
                    updateData(id)
                })
    };

    const updateData = (id) => {
        let updatedData = adminData.usersData.map((item) => {
            if (item.id === id) {
                let previousStatus = item.enabled;
                return {id: item.id, userName: item.userName, enabled: !previousStatus}
            }
            return item
        });
        dispatch({type: 'USERS_DATA', payload: {usersData: updatedData}});
    };

    return(
        <div class="card">
            <h4 className="header-padding">Users</h4>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={adminData.usersData}
                className={adminData.usersData.length < 10 ? '-striped -highlight table-format'
                                                  : '-striped -highlight table-format-large'}
                columns={[
                    {
                        Header: "User name",
                        accessor: "userName"
                    },
                    {
                        maxWidth: 100,
                        className: "columnAlignCenter",
                        Header: "Status",
                        accessor: "enabled",
                        Cell: props => props.value === true ? "Active" : "Disabled"
                    },
                    {
                        maxWidth: 70,
                        className: "columnAlignCenter",
                        accessor: "id",
                        Cell: Cell => Cell.original.enabled === true ?
                            <button onClick={() => changeUserStatus(Cell.original.id)}>Enable</button>
                            :
                            <button onClick={() => changeUserStatus(Cell.original.id)}>Disable</button>
                    }
                ]}
            />
        </div>
    )
};

export default ManageUsers;