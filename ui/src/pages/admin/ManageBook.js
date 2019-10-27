import React, {useContext, useEffect, useState} from 'react';
import {Card, Col, Row} from "react-bootstrap";
import {genres, BookFieldlist} from "../../common/Constants";
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";
import {Context} from "../../common/Context";
import Validate from "../../common/Validation";

const ManageBook=()=>{

    const [title, setTitle] = useState('');
    const [authorName, setAuthorName] = useState('');
    const [genre, setGenre] = useState('');
    const [copies, setCopies] = useState('');
    const {adminBookData: [booksData, setBooksData]} = useContext(Context);
    const {adminAuthorData: [authorData, setAuthorData]} = useContext(Context);
    const [infoMessage, setInfoMessage] = useState({type:'', msg: ''});

    useEffect(() => {
            AdminService.getAllBooks()
                .then(
                    response => {
                        setBooksData(response.data)
                    }
                );
            AdminService.getAllAuthors()
                .then(
                    response => {
                        if (response.status === 200) {
                            setAuthorData(response.data);
                        }
                    }
                )
        }, []
    );

    const addBook = () => {
        if (Validate.validateForm(BookFieldlist,  message)) {
            AdminService.addABook({title: title, authorName: authorName, genre: genre, copies: copies})
                .then(response => {
                    setBooksData([...booksData, response.data]);
                    resetFields();
                    message('info', 'Book added successfully!');
                })
                .catch((error) => {
                    message('error', error.response.data.message);
                })
        }
    };

    const deleteBook = (id) => {
        AdminService.deleteBook({id: id})
            .then(response => {
                    let filteredArray = booksData.filter(item => item.id !== id);
                    setBooksData(filteredArray);
                }
            )
    };

    const message = (type, msg) => {
        setInfoMessage ({type: type, msg: msg})
    };

    const resetFields = () => {
        setTitle('');
        setAuthorName('');
        setGenre('');
        setCopies('');
    };

    return(
        <div className="small-card-padding">
            <Card>
                {console.log('dfd '+booksData.length)}
                <h4>Books</h4>
                <Row>
                    <Col xl={3}>
                        <input className='col-width-height' id='Book title' value={title}
                               onChange={(event) =>{
                                   Validate.validateInput(event.target.id,  message, "text");
                                   setTitle(event.target.value)}}/>
                    </Col>
                    <Col xl={3}>
                        <select className='col-width-height' id='Author' value={authorName}
                                onChange={(event) =>{
                                    Validate.validateInput(event.target.id,  message, "text");
                                    setAuthorName(event.target.value)}}>
                            <option value={''}/>
                            {authorData.map(author =>
                                <option value={author.name}>{author.name}</option>)
                            }
                        </select>
                    </Col>
                    <Col xl={3}>
                        <select className='col-width-height' id='Genre' value={genre}
                                onChange={(event) =>{
                                    Validate.validateInput(event.target.id,  message, "text");
                                    setGenre(event.target.value)}}>
                            <option value={''}/>
                            {genres.map(genre =>
                                <option value={genre}>{genre}</option>)
                            }
                        </select>
                    </Col>
                    <Col xl={3}>
                        <input className='col-width-height ' id='Copies' value={copies}
                               onChange={(event) =>{
                                   Validate.validateInput(event.target.id,  message, "number");
                                   setCopies(event.target.value)}}/>
                    </Col>
                    <Col>
                        <button className=' button ' onClick={() => addBook()}>Add book</button>
                    </Col>
                    <Col className={infoMessage.type === 'error' ? "error-text" : "info-text"}>
                    <span>
                       {infoMessage.msg}
                    </span>
                    </Col>
                </Row>
                <br/>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={booksData.length > 10}
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
                            className:"columnAlignCenter",
                            Header: "Genre",
                            accessor: "genre"
                        },
                        {
                            className:"columnAlignCenter",
                            Header: "Copies",
                            accessor: "copies"
                        },
                        {
                            className:"columnAlignCenter",
                            accessor: "id",
                            Cell: ({value}) => (
                                <button onClick={() => deleteBook(value)}>Delete</button>
                            )
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </Card>
        </div>
    )
};

export default ManageBook