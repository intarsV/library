import React, {useContext, useEffect, useState} from 'react';
import UserService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {userReservation} from '../../common/Constants'
import {UserContext} from "../../context/UserContext";

const BookReservations = () => {

    const [selection, setSelection] = useState('optionActive');
    const {userData, dispatch} = useContext(UserContext);

    useEffect(() => {
            getReservationData('HANDOUT', "optionActive")
        }, []
    );

    const getReservationData = (requestData, selectedOption) => {
        UserService.searchReservations({status: requestData})
            .then(response => {
                    dispatch({type: 'SET_RESERVATION_DATA', payload: {reservationsData: response.data}});
                    setSelection(selectedOption);
                }
            )
            .catch((error) => {
                console.log(error.response.data.message);
            })
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
                    minRows={1} noDataText={'No data found'} showPagination={false} data={userData.reservationsData}
                    className={userData.reservationsData.length < 10 ? '-striped -highlight table-format'
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