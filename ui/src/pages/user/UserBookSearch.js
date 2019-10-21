import React, {useContext, useState} from 'react';
import {Card, Col, Row} from 'react-bootstrap';
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
        BookService.searchBook(searchData)
            .then(
                response => {
                    setBooks(response.data)
                }
            )
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
        <div className="small-card-padding">
            <Card>
                <h4>Search books</h4>
                <Row className='space-top'>
                    <Col md={3} sm={5} lg={2} xs={3}>
                        Book title:
                    </Col>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='title' value={title}
                               onChange={(event) => setTitle(event.target.value)}/>
                    </Col>
                </Row>
                <Row className='space-top'>
                    <Col md={3} sm={5} lg={2} xs={3}>
                        Book author:
                    </Col>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='authorName'
                               value={authorName}
                               onChange={(event) => setAuthorName(event.target.value)}/>
                    </Col>
                    <Col>
                        <button className="button" onClick={() => {
                            searchBooks()
                        }}> Search
                        </button>
                    </Col>
                </Row>
                <Row className='space-top margin-bottom'>
                    <Col md={3} sm={5} lg={2} xs={3}>
                        Book genre:
                    </Col>
                    <Col xl={3}>
                        <select className='col-width-height' name='genre' value={genre}
                                onChange={(event) => setGenre(event.target.value)}>
                            <option value={''}/>
                            {genres.map(genre =>
                                <option value={genre}>{genre}</option>)
                            }
                        </select>
                    </Col>
                </Row>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={books}
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
                            className:"columnAlignCenter",
                            minWidth: 60,
                            Header: "Genre",
                            accessor: "genre"
                        },
                        {
                            className:"columnAlignCenter",
                            minWidth: 70,
                            Header: "Available",
                            accessor: "available"
                        },
                        {
                            className:"columnAlignCenter",
                            Header: '',
                            accessor: 'id',
                            Cell: ({value}) => (
                                <button onClick={() => makeReservation(value)}>Get book</button>)
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </Card>
        </div>
    )
};

export default UserBookSearch;