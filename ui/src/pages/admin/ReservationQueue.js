import React, {useContext, useEffect } from 'react';
import {Card} from "react-bootstrap";
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

    return (
        <div className="small-card-padding">
            <Card md={3}>
                <h4>User reservations Queue</h4>
                <br/>
                <div className="row-format">
                <button className="button" onClick={() => refreshReservations()}>Refresh</button>
                </div>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={adminReservationQueue}
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
                            Header: '',
                            accessor: 'id',
                            Cell: ({value}) => (
                                <button onClick={() => {
                                    handOut(value)
                                }}>Hand out</button>)
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </Card>
        </div>
    )
};

export default ReservationQueue