import React, {Component} from 'react';
import { Row, Col} from 'reactstrap';
import BookService from '../../common/services/BookService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {genres} from '../../common/Constants';

class BookSearch extends Component{

    constructor(props) {
        super(props);
        this.state = {
            title: "",
            author: "",
            genre: "",
            books: [],
            searchData:{},
            message: null
        };
        this.refreshBooks = this.refreshBooks.bind(this)
        this.handleChange = this.handleChange.bind(this);
        this.searchBooks = this.searchBooks.bind(this)
    }

    componentDidMount() {
        // this.refreshBooks();
    }

    prepareRequest(){
        this.setState(this.state.searchData={});
        if(this.state.title!==''){
            this.state.searchData['title']=this.state.title;
        }
        if(this.state.author!==''){
            this.state.searchData['author']=this.state.author;
        }
        if(this.state.genre!==''){
            this.state.searchData['genre']=this.state.genre;
        }
    }

    searchBooks() {
       this.prepareRequest();
        BookService.searchBook(this.state.searchData)
            .then(
                response => {
                    console.log(response);
                    this.setState({books: response.data})
                }
            )
    }

    refreshBooks() {
        BookService.retrieveAllBooks()
            .then(
                response => {
                    this.setState({books: response.data})
                }
            )
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        );
    }

    render() {
        return(
            <div className='text-size'>
                <h2>Search books</h2>
                <Row className='space-top'>
                    <Col md={3} sm={5} lg={2} xs={3}>
                        Book title:
                    </Col>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='title' value={this.state.title} onChange={this.handleChange}/>
                    </Col>
                </Row>
                <Row className='space-top'>
                    <Col md={3} sm={5} lg={2} xs={3}>
                        Book author:
                    </Col>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='author' value={this.state.author} onChange={this.handleChange}/>
                    </Col>
                    <Col>
                        <button className=' button ' onClick={this.searchBooks}> Search</button>
                    </Col>
                </Row>
                <Row className='space-top margin-bottom'>
                    <Col md={3} sm={5} lg={2} xs={3}>
                        Book genre:
                    </Col>
                    <Col xl={3}>
                        <select className='col-width-height ' name='genre' value={this.state.genre} onChange={this.handleChange}>
                            <option value={''}/>
                            {genres.map(genre =>
                                <option value={genre}>{genre}</option>)
                            }
                        </select>
                    </Col>
                </Row >
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={this.state.books}
                    columns={[
                        {
                            show: false,
                            Header: "Id",
                            accessor: "id"
                        },
                        {
                            minWidth: 200,
                            Header: "Title",
                            accessor: "title"
                        },
                        {
                            minWidth: 200,
                            Header: "Author",
                            accessor: "authorName"
                        },
                        {
                            minWidth: 40,
                            Header: "Genre",
                            accessor: "genre"
                        },
                        {
                            show: false,
                            Header: "Copies",
                            accessor: "copies"
                        },
                        {
                            minWidth: 40,
                            Header: "Available",
                            accessor: "available"
                        }

                    ]}
                    className="-striped -highlight text-size"
                />
            </div>
        )
}
}

export default BookSearch;