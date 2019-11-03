import React, {useContext, useEffect } from 'react';
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";
import {Context} from "../../common/Context";

const ReservationQueue=()=>{

    const{adminReservationQueue:[adminReservationQueue, setAdminReservationQueue]}=useContext(Context);

    useEffect(() => {
        refreshReservations();
        }, []
    );

    const refreshReservations = () => {
        AdminService.getReservationQueue()
            .then(
                response => {
                    setAdminReservationQueue(response.data);
                }
            )
    };

    const handOut = (id) => {
        AdminService.handOut({id: id})
            .then(response => {
                    let filteredArray = adminReservationQueue.filter(item => item.id !== id);
                    setAdminReservationQueue(filteredArray);
                }
            )
    };

    const removeReservation = (id) => {
        AdminService.removeReservation({id: id})
            .then(response => {
                    let filteredArray = adminReservationQueue.filter(item => item.id !== id);
                    setAdminReservationQueue(filteredArray);
                }
            )
    };

    return (
        <div className="card">
                <h4 className="header-padding">User reservations Queue</h4>
                <button className="button-small-margin" onClick={() => refreshReservations()}>Refresh</button>
                <ReactTable
                    minRows={1} noDataText={'No data found'} showPagination={false} data={adminReservationQueue}
                    className={adminReservationQueue.length < 10 ? '-striped -highlight table-format'
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
                            Cell: ({value}) => (<button onClick={() => {handOut(value)}}>Hand out</button>)
                        },
                        {
                            className:"columnAlignCenter",
                            maxWidth: 100,
                            Header: '',
                            accessor: 'id',
                            Cell: ({value}) => (
                                <button onClick={() => {removeReservation(value)}}>Remove</button>)
                        }
                    ]}
                />
            </div>
    )
};

export default ReservationQueue