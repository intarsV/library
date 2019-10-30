import React, {useState} from 'react';
import {returnedOptions} from "../../common/Constants";
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";

const ReservationSearch=()=>{

    const [bookTitle, setBookTitle] = useState('');
    const [userName, setUserName] = useState('');
    const [returned, setReturned] = useState('');
    const [reservationsData, setReservationsData] = useState([]);
    const [searchData, setSearchData] = useState({});

    const prepareRequest = () => {
        setSearchData({});
        if (bookTitle !== '') {
            searchData['bookTitle'] = bookTitle;
        }
        if (userName !== '') {
            searchData['userName'] = userName;
        }
        searchData['handOut'] = true;
        if (returned !== '') {
            searchData['returned'] = returned;
        }
    };

    const searchReservations = () => {
        prepareRequest();
        AdminService.searchReservations(searchData)
            .then(
                response => {
                    setReservationsData(response.data)
                }
            )
    };

    const returnBook = (id) => {
        AdminService.takeIn({id: id})
            .then(
                response => {
                    let filteredArray = reservationsData.filter(item => item.id !== id);
                    setReservationsData(filteredArray);
                }
            );
    };

    return (
        <div className="card">
            <h4>Search reservations</h4>
            <div className="row">
                <div className="col-3">
                    Book title:
                </div>
                <div className="col-3">
                    <input className='col-width-height ' type='text' name='bookTitle' value={bookTitle}
                           onChange={(event) => {
                               setBookTitle(event.target.value)
                           }}/>
                </div>
            </div>
            <div className="row">
                <div className="col-3">
                    User name:
                </div>
                <div className="col-3">
                    <input className='col-width-height ' type='text' name='userName'
                           value={userName}
                           onChange={(event) => {
                               setUserName(event.target.value)
                           }}/>
                </div>
                <div className="col-3">
                    <button className=' button ' onClick={() => searchReservations()}> Search</button>
                </div>
            </div>
            <div className="row">
                <div className="col-3">
                    Return status :
                </div>
                <div className="col-3">
                    <select className='col-width-height' name='returned' value={returned}
                            onChange={(event) => {
                                setReturned(event.target.value)
                            }}>
                        <option value={''}/>
                        {returnedOptions.map(option =>
                            <option value={option.value}>{option.key}</option>)
                        }
                    </select>
                </div>
            </div>
            <ReactTable
                defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                data={reservationsData}
                columns={[
                    {
                        show: false,
                        Header: "Id",
                        accessor: "id"
                    },
                    {
                        minWidth: 200,
                        Header: "Title",
                        accessor: "bookTitle"
                    },
                    {
                        minWidth: 200,
                        Header: "User",
                        accessor: "userName"
                    },
                    {
                        className: "columnAlignCenter",
                        id: 'reservationDate',
                        Header: "Date",
                        accessor: 'reservationDate',
                        Cell: props => new Date(props.value).toLocaleDateString()
                    },
                    {
                        className: "columnAlignCenter",
                        Header: "Returned",
                        accessor: "returned",
                        Cell: Cell => (
                            <input type="checkbox" defaultChecked={Cell.original.returned}
                                   disabled={(Cell.original.returned)}
                                   onClick={() => returnBook(Cell.original.id)}/>)
                    }
                ]}
                className="-striped -highlight text-size"
            />
        </div>
    )
};

export default ReservationSearch