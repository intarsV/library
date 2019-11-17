import React, {useContext, useEffect} from 'react';
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";
import {AdminContext} from "../../context/AdminContext";

const ReservationQueue = () => {

    const {adminData, dispatch} = useContext(AdminContext);

    const statusTypes = {
        QUEUE: 'QUEUE',
        HANDOUT: 'HANDOUT',
        CANCELED: 'CANCELED'
    };

    useEffect(() => {
            refreshReservations();
        }, []
    );

    const refreshReservations = () => {
        AdminService.searchReservations({status: 'QUEUE'})
            .then(response => {
                    dispatch({type: 'ADMIN_RESERVATION_QUEUE', payload: {adminReservationQueue: response.data}});
                }
            )
            .catch((error) => {
                console.log(error.response.data.message);
            })
    };

    const process = (id, statusValue) => {
        AdminService.process({id: id, status: statusValue})
            .then(response => {
                    let filteredArray = adminData.adminReservationQueue.filter(item => item.id !== id);
                    dispatch({type: 'ADMIN_RESERVATION_QUEUE', payload: {adminReservationQueue: filteredArray}});
                }
            )
            .catch((error) => {
                console.log(error.response.data.message);
            })
    };

    return (
        <div className="card">
                <h4 className="header-padding">User reservations Queue</h4>
                <button className="button-small-margin" onClick={() => refreshReservations()}>Refresh</button>
                <ReactTable
                    minRows={1} noDataText={'No data found'} showPagination={false}
                    data={adminData.adminReservationQueue}
                    className={adminData.adminReservationQueue.length < 10 ? '-striped -highlight table-format'
                                                                 : '-striped -highlight table-format-large'}
                    columns={[
                        {
                            minWidth: 200,
                            maxWidth: 200,
                            Header: "Title",
                            accessor: "bookTitle"
                        },
                        {
                            Header: "User",
                            accessor: "userName"
                        },
                        {
                            className:"columnAlignCenter",
                            id: 'reservationDate',
                            Header: "Reservation date",
                            accessor: 'reservationDate',
                            Cell: props => new Date(props.value).toLocaleDateString()
                        },
                        {
                            className:"columnAlignCenter",
                            maxWidth: 100,
                            Header: '',
                            accessor: 'id',
                            Cell: ({value}) => (<button onClick={() => {process(value, statusTypes.HANDOUT)}}>Hand out</button>)
                        },
                        {
                            className:"columnAlignCenter",
                            maxWidth: 100,
                            Header: '',
                            accessor: 'id',
                            Cell: ({value}) => (
                                <button onClick={() => {process(value, statusTypes.CANCELED)}}>Remove</button>)
                        }
                    ]}
                />
            </div>
    )
};

export default ReservationQueue