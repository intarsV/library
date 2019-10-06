import React, {Component} from 'react'
import AdminService from '../../common/services/AdminService';
import {Card, Alert} from 'react-bootstrap';
import ReactTable from 'react-table';
import 'react-table/react-table.css';
import {Row, Col} from 'react-bootstrap';
import {Form} from 'react-bootstrap';
import {genres} from "../../common/Constants";

class ManageBook_OLD_State extends Component{
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            title: '',
            authorName: '',
            genre: '',
            copies: '',
            bookData: [],
            authorData: [],
            requestData: {},
        };
        this.handleChange = this.handleChange.bind(this);
        this.prepareRequest = this.prepareRequest.bind(this);
        this.deleteBook = this.deleteBook.bind(this);
        this.addBook = this.addBook.bind(this);
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        );
    }

    componentDidMount() {
        AdminService.getAllAuthors()
            .then(
                response => {//
                    this.setState({authorData: response.data});
                    // console.log(this.state.authorData)
                }
            );
        AdminService.getAllBooks()
            .then(
                response => {//
                    this.setState({bookData: response.data});
                    // console.log(this.state.bookData)
                }
            )
    }

    prepareRequest() {
        this.setState(this.state.requestData = {});
        if (this.state.title !== '') {
            this.state.requestData['title'] = this.state.title;
        }
        if (this.state.authorName !== '') {
            this.state.requestData['authorName'] = this.state.authorName;
        }
        if (this.state.genre !== '') {
            this.state.requestData['genre'] = this.state.genre;
        }
        if (this.state.copies !== '') {
            this.state.requestData['copies'] = this.state.copies;
        }
    }

    deleteBook(id) {
        this.state.requestData['id'] = id;
        AdminService.deleteBook(this.state.requestData)
            .then(
                response => {
                    let filteredArray = this.state.authorData.filter(item => item.id !== id);
                    this.setState({authorData: filteredArray});
                }
            )
    }

    addBook() {
        console.log("Baaa")
        console.log(this.state.title+ " "+this.state.authorName+" "+this.state.genre+" " +this.state.copies);
        let filteredArray = this.state.bookData.filter(item => item.title === this.state.title);
        if (filteredArray.length > 0) {
            alert("There is already author with name!");
        } else {
            this.prepareRequest();
            AdminService.addABook(this.state.requestData)
                .then(
                    response => {
                        let newBookData = this.state.bookData.push(response.data);
                        this.setState({newAuthorData: newBookData});
                    })
        }
    }


    render(){
        return (
            <Card>
                <div className='text-size padding-top'>
                    <h4>Books</h4>
                    <Row>
                        <Col xl={3}>
                            <input className='col-width-height ' type='text' name='title'
                                    onChange={this.handleChange}/>
                        </Col>
                        <Col xl={3}>
                            <select className='col-width-height' name='authorName'
                                    onChange={this.handleChange}>
                                <option value={''}/>
                                {this.state.authorData.map(author =>
                                    <option value={author.name}>{author.name}</option>)
                                }
                            </select>
                        </Col>
                        <Col xl={3}>
                            <select className='col-width-height' name='genre'
                                    onChange={this.handleChange}>
                                <option value={''}/>
                                {genres.map(genre =>
                                    <option value={genre}>{genre}</option>)
                                }
                            </select>
                        </Col>
                        <Col xl={3}>
                            <input className='col-width-height ' type='text' name='copies'
                                   onChange={this.handleChange}/>
                        </Col>
                        <Col>
                            <button className=' button ' onClick={this.addBook}>Add book</button>
                        </Col>
                    </Row>
                    <br/>
                    <ReactTable
                        defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                        data={this.state.bookData}
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
                                    <button onClick={() => this.deleteBook(value)}>Delete</button>
                                )
                            }
                        ]}
                        className="-striped -highlight text-size"
                    />
                </div>
            </Card>
        )
    }
}
export default ManageBook_OLD_State;