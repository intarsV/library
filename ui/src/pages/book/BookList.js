import React, {Component} from 'react';
import BookService from '../../common/services/BookService'
import ReactTable from "react-table";
import "react-table/react-table.css";

class BookList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            books: [],
            message: null
        };
        this.refreshBooks = this.refreshBooks.bind(this)
    }

    componentDidMount() {
        this.refreshBooks();
    }

    refreshBooks() {
        BookService.retrieveAllBooks()
            .then(
                response => {
                    console.log(response);
                    this.setState({books: response.data})
                }
            )
    }

    render() {
        return (
            <div>
                <ReactTable
                    data={this.state.books}
                    columns={[
                        {
                            Header: "Id",
                            accessor: "id"
                        },
                        {
                            Header: "Title",
                            accessor: "bookTitle"
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
                            accessor: "copies"
                        },
                        {
                            Header: "Available",
                            accessor: "available"
                        }

                    ]}
                    className="-striped -highlight"
                />
            </div>
        )
    };
}

export default BookList;