import React, {useContext, useEffect} from 'react';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import UserService from "../../common/services/UserService";
import {UserContext} from "../../context/UserContext";

const UserQueue = () => {

    const {userData, dispatch} = useContext(UserContext);

    const removeReservation = (id, statusValue) => {
        UserService.processReservation({id: id, status: 'CANCELED'})
            .then(
                response => {
                    let filteredArray = userData.reservationQueue.filter(item => item.id !== id);
                    dispatch({type: 'SET_RESERVATION_QUEUE', payload: {reservationQueue: filteredArray}});
                }
            )
    };

    useEffect(() => {
            if (userData.reservationQueue.length === 0) {
                dispatch({type: 'SHOW_USER_QUEUE', payload: false});
            }
        }, [userData.reservationQueue]
    );


    return (
        <div className="card-transparent">
            <div>
                <h6 className="login-header">Queue</h6>
                <ReactTable
                    minRows={1} noDataText={''} showPagination={false} data={userData.reservationQueue}
                    className={userData.reservationQueue.length < 10 ? '-striped -highlight table-format'
                                                            : '-striped -highlight table-format-large'}
                    columns={[
                        {
                            className: "columnAlignLeft",
                            Header: "Title",
                            accessor: "bookTitle"
                        },
                        {
                            className: "columnAlignCenter",
                            Header: "Delete",
                            maxWidth: 55,
                            accessor: 'id',
                            Cell: ({value}) => (
                                <input type="checkbox" onClick={() => removeReservation(value)}/>)
                        }
                    ]}
                />
            </div>
        </div>
    )
};

export default UserQueue;