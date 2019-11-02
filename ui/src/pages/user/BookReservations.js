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
            <div class="card">
                <h4 className="header-padding">User reservations</h4>
                <div className="row row-format">
                    <h5 className="label">Status:</h5>
                    {userReservation.map((reservation, i) =>
                        <div key={i}>
                            <input type="radio"
                                   checked={selection === reservation.optionName}
                                   onClick={() => {
                                       getReservationData(reservation.data, reservation.optionName)
                                   }}/>
                            <label className="radio-label">{reservation.optionHeader}</label>
                        </div>
                    )}
                </div>
                <ReactTable
                    minRows={1} noDataText={'No data found'} showPagination={false} data={reservationsData}
                    className={reservationsData.length < 10 ? '-striped -highlight table-format'
                                                            : '-striped -highlight table-format-large'}
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
                />
            </div>
        )
};

export default BookReservations;