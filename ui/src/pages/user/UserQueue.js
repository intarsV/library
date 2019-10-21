import React, { useContext } from 'react';
import { Card } from 'react-bootstrap';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {Context} from "../../common/Context";

const UserQueue = () => {

    const {userReservationQueue:[reservationQueue, setReservationQueue]} = useContext(Context);

    return (
        <Card>
            <div className='text-size'>
                <h6>Queue</h6>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={''} showPagination={false}
                    data={reservationQueue}
                    columns={[
                        {
                            Header: "Title",
                            accessor: "bookTitle"
                        },
                        {
                            id: 'reservationDate',
                            Header: "Reservation date",
                            accessor: 'reservationDate',
                            Cell: props => new Date(props.value).toLocaleDateString()
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </div>
        </Card>
    )
};

export default UserQueue;