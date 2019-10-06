import React, {useState} from 'react';
import {Card} from "react-bootstrap";
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";

const ReservationQueue=()=>{

    const[queue, setQueue]=useState([]);
    const [firstLoad, setFirstLoad] = useState(true);

    const isFirstLoad = () => {
        if (firstLoad) {
            refreshReservations()
        }
    };

    const refreshReservations = () => {
        AdminService.getReservationQueue()
            .then(
                response => {
                    setQueue(response.data);
                    setFirstLoad(false)
                }
            )
    };

    const handOut = (id) => {
        AdminService.handOut({id: id})
            .then(
                response => {
                    let filteredArray = queue.filter(item => item.id !== id);
                    setQueue(filteredArray);
                }
            )
    };

    return (
        <Card md={3}>
            <div className='text-size padding-top'>
                {isFirstLoad()}
                <h4>User reservations Queue</h4>
                <br/>
                <button onClick={()=>refreshReservations()}>Refresh</button>
                <br/>
                <br/>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={queue}
                    columns={[
                        {
                            minWidth: 200,
                            maxWidth: 200,
                            Header: "Title",
                            accessor: "bookTitle"
                        },
                        {
                            Header: "User",
                            accessor:"userName"
                        },
                        {
                            id: 'reservationDate',
                            Header: "Reservation date",
                            accessor: 'reservationDate',
                            Cell: props => new Date(props.value).toLocaleDateString()
                        },
                        {
                            Header: '',
                            accessor: 'id',
                            Cell: ({value}) => (
                                <button onClick={() => {handOut(value)}}>Hand out</button>)
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </div>
        </Card>
    )
};

export default ReservationQueue