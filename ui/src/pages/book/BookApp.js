import React,{Component}  from 'react';
import BookList from './BookList';

class BookApp extends Component {
    render() {
        return (
            <React.Fragment>
            <h1>Library</h1>
            <BookList/>
            </React.Fragment>
        )
    }
}

export default BookApp;