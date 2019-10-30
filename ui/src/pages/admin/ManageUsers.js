import React, {useContext, useEffect} from 'react';
import {Context} from "../../common/Context";
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";

const ManageUsers=()=>{

    const{adminUserData: [userData, setUserData]}=useContext(Context);

    useEffect(() => {
            AdminService.getUsers()
                .then(
                    response => {
                        setUserData(response.data);
                    }
                )
        }, []
    );

    const enableUser = (id) => {
        AdminService.enableUser({id: id})
            .then(
                response => {
                    updateData(true, id)
                })
    };

    const disableUser = (id) => {
        AdminService.disableUser({id: id})
            .then(
                response => {
                    updateData(false, id)
                })
    };

    const updateData = (boolean, id) => {
        let updatedData = userData.map((item) => {
            if (item.id === id) {
                return {id: item.id, userName: item.userName, enabled: boolean}
            }
            return item
        });
        setUserData(updatedData);
    };

    return(
        <div class="card">
            <h4>Users</h4>
            <br/>
            <ReactTable
                defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={userData > 10}
                data={userData}
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
                            <button onClick={() => disableUser(Cell.original.id)}>Disable</button>
                            :
                            <button onClick={() => enableUser(Cell.original.id)}>Enable</button>
                    }
                ]}
                className="-striped -highlight text-size"
            />
        </div>
    )
};

export default ManageUsers;