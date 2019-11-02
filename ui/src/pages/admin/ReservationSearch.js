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
        <div class="card">
            <h4 className="header-padding">Search reservations</h4>
            <div className="row row-format">
                    <h5 className="label">Book title:</h5>
                    <input className='input-field' type='text' name='bookTitle' value={bookTitle}
                           onChange={(event) => {
                               setBookTitle(event.target.value)
                           }}/>
            </div>
            <div className="row row-format">
                    <h5 className="label">User name:</h5>
                    <input className='input-field' type='text' name='userName'
                           value={userName}
                           onChange={(event) => {
                               setUserName(event.target.value)
                           }}/>
            </div>
            <div className="row row-format">
                    <h5 className="label">Return status :</h5>
                    <select className='input-field' name='returned' value={returned}
                            onChange={(event) => {
                                setReturned(event.target.value)
                            }}>
                        <option value={''}/>
                        {returnedOptions.map(option =>
                            <option value={option.value}>{option.key}</option>)
                        }
                    </select>
            </div>
            <button className='button' onClick={() => searchReservations()}> Search</button>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={reservationsData}
                className={reservationsData.length < 10 ? '-striped -highlight table-format'
                                                        : '-striped -highlight table-format-large'}
                columns={[
                    {
                        show: false,
                        Header: "Id",
                        accessor: "id"
                    },
                    {
                        maxWidth: 150,
                        Header: "Title",
                        accessor: "bookTitle"
                    },
                    {
                        maxWidth: 100,
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
            />
        </div>
    )
};

export default ReservationSearch