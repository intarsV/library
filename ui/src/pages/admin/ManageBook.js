import React, {useContext, useEffect, useState} from 'react';
import {Card, Col, Row} from "react-bootstrap";
import {genres} from "../../common/Constants";
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";
import {Context} from "../../common/Context";

const ManageBook=()=>{

    const [title, setTitle] = useState('');
    const [authorName, setAuthorName] = useState('');
    const [genre, setGenre] = useState('');
    const [copies, setCopies] = useState('');
    const{adminBookData: [booksData, setBooksData]}=useContext(Context);
    const {adminAuthorData: [authorData, setAuthorData]} = useContext(Context);
    const [error, setError]=useState('');

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
        let filteredArray = booksData.filter(item => item.title === title);
        if (filteredArray.length > 0) {
            setError("There is already book with such title!");
        } else {
            AdminService.addABook({title: title, authorName: authorName, genre: genre, copies: copies})
                .then(response => {
                        setBooksData([...booksData, response.data]);
                    })
        }
    };

    const deleteBook = (id) => {
        AdminService.deleteBook({id: id})
            .then(response => {
                    let filteredArray =booksData.filter(item => item.id !== id);
                    setBooksData(filteredArray);
                }
            )
    };

    return(
        <div className="small-card-padding">
            <Card>
                <h4>Books</h4>
                <Row>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='title'
                               onChange={(event) => setTitle(event.target.value)}/>
                        <span><div className="error-text">{error}</div></span>
                    </Col>
                    <Col xl={3}>
                        <select className='col-width-height' name='authorName'
                                onChange={(event) => setAuthorName(event.target.value)}>
                            <option value={''}/>
                            {authorData.map(author =>
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
                        <button className=' button ' onClick={() => addBook()}>Add book</button>
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