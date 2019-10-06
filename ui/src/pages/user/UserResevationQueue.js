import React,  {useState } from 'react';
import { Card} from 'react-bootstrap';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';

const UserReservationQueue = () => {

    const requestData = {handOut: "false", returned: "false"};
    const [reservationsData, setReservationData] = useState([]);
    const [firstLoad, setFirstLoad] = useState(true);

    const isFirstLoad = () => {
        if (firstLoad) {
            getReservationData(requestData)
        }
    };

    const getReservationData = (requestData) => {
        BookService.getReservations(requestData)
            .then(response => {
                    setReservationData(response.data);
                    setFirstLoad(false)
                }
            );
    };

    return (
        <Card md={3}>
            <div className='text-size padding-top'>
                {isFirstLoad()}
                <h6>Queue</h6>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={reservationsData}
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

export default UserReservationQueue;