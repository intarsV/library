import React,{Component}  from 'react';
import BookList from './BookList';

class BookPage extends Component {
    render() {
        return (
            <>
                <h1>Library</h1>
                <BookList/>
            </>
        )
    }
}

export default BookPage;