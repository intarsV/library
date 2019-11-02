import React, {useContext, useState} from 'react';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {genres} from "../../common/Constants";
import {Context} from "../../common/Context";

const UserBookSearch = () => {

    const [title, setTitle] = useState('');
    const [authorName, setAuthorName] = useState('');
    const [genre, setGenre] = useState('');
    const [books, setBooks] = useState([]);
    const [searchData, setSearchData] = useState({});
    const {userReservationQueue:[reservationQueue, setReservationQueue]} = useContext(Context);
    const {showUserQueue:[showUserQueue, setShowUserQueue]} = useContext(Context);


    const prepareRequest = () => {
        setSearchData({});
        if (title !== '') {
            searchData['title'] = title;
        }
        if (authorName !== '') {
            searchData['authorName'] = authorName;
        }
        if (genre !== '') {
            searchData['genre'] = genre;
        }
    };

    const searchBooks = () => {
        prepareRequest();
        if (searchData.length > 0) {
        BookService.searchBook(searchData)
            .then(
                response => {
                    setBooks(response.data)
                }
            )}
    };

    const makeReservation = (bookId) => {
        BookService.makeReservation({bookId: bookId})
            .then(
                response => {
                    setReservationQueue([...reservationQueue, response.data]);
                    setShowUserQueue(true)
                }
            )
    };

    return (
        <div className="card">
            <h4 className="header-padding">Search books</h4>
            <div className="row row-format">
                <h5 className="label">Book title:</h5>
                <input className="input-field" type='text' name='title' value={title}
                       onChange={(event) => setTitle(event.target.value)}/>
            </div>
            <div className="row row-format">
                <h5 className="label">Book author:</h5>
                <input className='input-field' type='text' name='authorName'
                       value={authorName}
                       onChange={(event) => setAuthorName(event.target.value)}/>
            </div>
            <div className="row row-format">
                <h5 className="label">Book genre:</h5>
                    <select className="input-field" name='genre' value={genre}
                            onChange={(event) => setGenre(event.target.value)}>
                        <option value={''}/>
                        {genres.map(genre =>
                            <option value={genre}>{genre}</option>)
                        }
                    </select>
            </div>
            <button className="button" onClick={() => {searchBooks()}}>Search</button>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={books}
                className={books.length < 10 ? '-striped -highlight table-format'
                                                        : '-striped -highlight table-format-large'}
                columns={[
                    {
                        minWidth: 250,
                        Header: "Title",
                        accessor: "title"
                    },
                    {
                        minWidth: 200,
                        Header: "Author",
                        accessor: "authorName"
                    },
                    {
                        className: "columnAlignCenter",
                        minWidth: 60,
                        Header: "Genre",
                        accessor: "genre"
                    },
                    {
                        className: "columnAlignCenter",
                        minWidth: 70,
                        Header: "Available",
                        accessor: "available"
                    },
                    {
                        className: "columnAlignCenter",
                        Header: '',
                        accessor: 'id',
                        Cell: ({value}) => (
                            <button onClick={() => makeReservation(value)}>Get book</button>)
                    }
                ]}
            />
        </div>
    )
};

export default UserBookSearch;