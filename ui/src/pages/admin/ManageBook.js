import React, {useState} from 'react';
import {Card, Col, Row} from "react-bootstrap";
import {genres} from "../../common/Constants";
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";

const ManageBook=()=>{

    const [title, setTitle] = useState('');
    const [authorName, setAuthorName] = useState('');
    const [genre, setGenre] = useState('');
    const [copies, setCopies] = useState('');
    const [booksData, setBooksData] = useState([]);
    const [authorsData, setAuthorsData] = useState([]);
    const [firstLoad, setFirstLoad] = useState(true);

    const isFirstLoad=()=>{
        if(firstLoad){
            getAllBooks();
            getAllAuthors()}
    };

    const getAllBooks = () => {
        AdminService.getAllBooks()
            .then(
                response => (
                    setBooksData(response.data)
                )
            )
    };

    const getAllAuthors = () => {
        AdminService.getAllAuthors()
            .then(
                response => {
                    setAuthorsData(response.data);
                    setFirstLoad(false)
                }
            )
    };

    const addBook = () => {
        let filteredArray = booksData.filter(item => item.title === title);
        if (filteredArray.length > 0) {
            alert("There is already author with name!");
        } else {
            AdminService.addABook({title: title, authorName: authorName, genre: genre, copies: copies})
                .then(
                    response => {
                        setBooksData([...booksData, response.data]);
                    })
        }
    };

    const deleteBook = (id) => {
        AdminService.deleteBook({id: id})
            .then(
                response => {
                    let filteredArray =booksData.filter(item => item.id !== id);
                    setBooksData(filteredArray);
                }
            )
    };

    return(
        <Card>
            <div className='text-size padding-top'>
                {isFirstLoad()}
                <h4>Books</h4>
                <Row>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='title'
                               onChange={(event) => setTitle(event.target.value)}/>
                    </Col>
                    <Col xl={3}>
                        <select className='col-width-height' name='authorName'
                                onChange={(event) => setAuthorName(event.target.value)}>
                            <option value={''}/>
                            {authorsData.map(author =>
                                <option value={author.name}>{author.name}</option>)
                            }
                        </select>
                    </Col>
                    <Col xl={3}>
                        <select className='col-width-height' name='genre'
                                onChange={(event) => setGenre(event.target.value)}>
                            <option value={''}/>
                            {genres.map(genre =>
                                <option value={genre}>{genre}</option>)
                            }
                        </select>
                    </Col>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='copies'
                               onChange={(event) => setCopies(event.target.value)}/>
                    </Col>
                    <Col>
                        <button className=' button ' onClick={()=>addBook()}>Add book</button>
                    </Col>
                </Row>
                <br/>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={booksData}
                    columns={[
                        {
                            Header: "Book Title",
                            accessor: "title"
                        },
                        {
                            Header: "Author",
                            accessor: "authorName"
                        },
                        {
                            Header: "Genre",
                            accessor: "genre"
                        },
                        {
                            Header: "Copies",
                            accessor:"copies"
                        },
                        {
                            accessor: "id",
                            Cell: ({value}) => (
                                <button onClick={() => deleteBook(value)}>Delete</button>
                            )
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </div>
        </Card>
    )
};

export default ManageBook