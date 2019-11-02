import React, {useContext, useEffect, useState} from 'react';
import {genres, bookFieldList} from "../../common/Constants";
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
        if (Validate.validateForm(bookFieldList, setInfoMessage)) {
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
        <div class="card">
            <h4 className="header-padding">Books</h4>
            <div className="row row-format">
                    <h5 className="label"> Book title:</h5>
                    <input className='input-field' id='bookTitle' value={title} type="text"
                           onChange={(event) => {
                               Validate.validateInput(event.target.id, "text", setInfoMessage);
                               setTitle(event.target.value)
                           }}/>
                <div id="bookTitle_error" className="error-field-hide">Title field not valid!</div>
            </div>
            <div className="row row-format">
                    <h5 className="label">Book author:</h5>
                    <select className='input-field' id='author' value={authorName}
                            onChange={(event) => {
                                Validate.validateInput(event.target.id, "text", setInfoMessage);
                                setAuthorName(event.target.value)
                            }}>
                        <option value={''}/>
                        {authorData.map(author =>
                            <option value={author.name}>{author.name}</option>)
                        }
                    </select>
                <div id="author_error" className="error-field-hide">Author field not valid!</div>
            </div>
            <div className="row row-format">
                    <h5 className="label">Book genre:</h5>
                    <select className='input-field' id='genre' value={genre}
                            onChange={(event) => {
                                Validate.validateInput(event.target.id, "text", setInfoMessage);
                                setGenre(event.target.value)
                            }}>
                        <option value={''}/>
                        {genres.map(genre =>
                            <option value={genre}>{genre}</option>)
                        }
                    </select>
                <div id="genre_error" className="error-field-hide">Genre field not valid!</div>
            </div>
            <div className="row row-format">
                    <h5 className="label">Book copies:</h5>
                    <input className='input-field' id='copies' value={copies} type="text"
                           onChange={(event) => {
                               Validate.validateInput(event.target.id, "number", setInfoMessage);
                               setCopies(event.target.value)
                           }}/>
                <div id="copies_error" className="error-field-hide">Copies field not valid!</div>
            </div>
            <button className="button" onClick={() => addBook()}>Add book</button>
            <span className={infoMessage.type === 'error' ? "col-sm error-text" : "col-sm info-text"}>
                  {infoMessage.msg}
            </span>
            <ReactTable
                minRows={1} noDataText={'No data found'} showPagination={false} data={booksData}
                className={booksData.length < 10 ? '-striped -highlight table-format'
                                                 : '-striped -highlight table-format-large'}
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
                        className: "columnAlignCenter",
                        Header: "Genre",
                        accessor: "genre"
                    },
                    {
                        className: "columnAlignCenter",
                        Header: "Copies",
                        accessor: "copies"
                    },
                    {
                        className: "columnAlignCenter",
                        accessor: "id",
                        Cell: ({value}) => (
                            <button onClick={() => deleteBook(value)}>Delete</button>
                        )
                    }
                ]}
            />
        </div>
    )
};

export default ManageBook