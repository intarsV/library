import React, {useContext, useEffect} from 'react';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {Context} from "../../common/Context";
import BookService from "../../common/services/UserService";

const UserQueue = () => {

    const {userReservationQueue:[reservationQueue, setReservationQueue]} = useContext(Context);
    const {showUserQueue:[showUserQueue, setShowUserQueue]} = useContext(Context);

    const removeReservation = (id) => {
        BookService.removeReservation({id: id})
            .then(
                response => {
                    let filteredArray = reservationQueue.filter(item => item.id !== id);
                    setReservationQueue(filteredArray);
                }
            )
    };

    useEffect(() => {
            if (reservationQueue.length === 0) {
                setShowUserQueue(false);
            }
        }, [reservationQueue]
    );


    return (
        <div className="card">
            <div className='text-size'>
                <h6>Queue</h6>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={''} showPagination={false}
                    data={reservationQueue}
                    columns={[
                        {
                            className: "columnAlignLeft",
                            Header: "Title",
                            accessor: "bookTitle"
                        },
                        {
                            className: "columnAlignCenter",
                            Header: "Delete",
                            maxWidth: 55,
                            accessor: 'id',
                            Cell: ({value}) => (
                                <input type="checkbox" onClick={() => removeReservation(value)}/>)
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </div>
        </div>
    )
};

export default UserQueue;