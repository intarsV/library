import React, {Component, useState } from 'react';
import {Row, Col, Card,Form, FormCheck, FormControl} from 'react-bootstrap';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {userReservation} from '../../common/Constants'

const BookReservations =()=> {

    const [selection, setSelection] = useState('optionActive');
    const [reservationsData, setReservationData] = useState([]);
    const [firstLoad, setFirstLoad] = useState(true);

    const isFirstLoad=()=>{
        if(firstLoad){
            getReservationData  ({handOut: "true", returned: "false"}, "optionActive")}
        };

    const getReservationData = (requestData, selectedOption) => {
        BookService.getReservations(requestData)
            .then(response => {
                    setReservationData(response.data);
                    setSelection(selectedOption);
                    setFirstLoad(false)
                }
            );
    };

        return (
            <Card md={3}>
                <div className='text-size padding-top'>
                    {isFirstLoad()}
                    <h4>User reservations</h4>
                    <Row className='space-top margin-bottom'>
                        <label> Reservation status:</label>
                        {userReservation.map((reservation) =>
                            <Col md={2} sm={2} lg={2} xs={2}>
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
                                maxWidth: 200,
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

export default BookReservations;