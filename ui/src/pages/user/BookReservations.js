import React, { useContext, useEffect, useState } from 'react';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {userReservation} from '../../common/Constants'
import {Context} from "../../common/Context";

const BookReservations = () => {

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
            <div className="card ">
                <h4>User reservations</h4>
                <div className="row card-body">
                    <label>Reservation status:</label>
                    {userReservation.map((reservation, i) =>
                        <div className="col-sm" key={i}>
                            <input type="radio"
                                   checked={selection === reservation.optionName}
                                   onClick={() => {
                                       getReservationData(reservation.data, reservation.optionName)
                                   }}/>
                            <label>{reservation.optionHeader}</label>
                        </div>
                    )}
                </div>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={reservationsData > 10}
                    data={reservationsData}
                    columns={[
                        {
                            minWidth: 200,
                            maxWidth: 500,
                            Header: "Title",
                            accessor: "bookTitle"
                        },
                        {
                            className: "columnAlignCenter",
                            id: 'reservationDate',
                            Header: "Reservation date",
                            accessor: 'reservationDate',
                            Cell: props => new Date(props.value).toLocaleDateString()
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </div>
        )
};

export default BookReservations;