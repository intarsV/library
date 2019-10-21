import React, { useContext, useEffect, useState } from 'react';
import {Row, Col, Card} from 'react-bootstrap';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {userReservation} from '../../common/Constants'
import {Context} from "../../common/Context";

const BookReservations =()=> {

    const [selection, setSelection] = useState('optionActive');
    const {userReservationData:[reservationsData, setReservationData]}=useContext(Context);

    useEffect(()=>{
            getReservationData({handOut: "true", returned: "false"}, "optionActive")
        },[]
    );

    const getReservationData = (requestData, selectedOption) => {
        BookService.getReservations(requestData)
            .then(response => {
                    setReservationData(response.data);
                    setSelection(selectedOption);
                }
            );
    };

        return (
            <div className="small-card-padding">
                <Card md={3}>
                    <h4>User reservations</h4>
                    <Row className='space-top margin-bottom'>
                        <label> Reservation status:</label>
                        {userReservation.map((reservation, i) =>
                            <Col  key={i} md={2} sm={2} lg={2} xs={2}>
                                <input type="radio" value='optionOne'
                                       checked={selection === reservation.optionName}
                                       onClick={() => {
                                           getReservationData(reservation.data, reservation.optionName)
                                       }}/>
                                <label>{reservation.optionHeader}</label>
                            </Col>
                        )}
                    </Row>
                    <ReactTable
                        defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                        data={reservationsData}
                        columns={[
                            {
                                minWidth: 200,
                                maxWidth: 500,
                                Header: "Title",
                                accessor: "bookTitle"
                            },
                            {
                                className:"columnAlignCenter",
                                id: 'reservationDate',
                                Header: "Reservation date",
                                accessor: 'reservationDate',
                                Cell: props => new Date(props.value).toLocaleDateString()
                            }
                        ]}
                        className="-striped -highlight text-size"
                    />
                </Card>
            </div>
        )
};

export default BookReservations;