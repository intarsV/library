import React,{Component}  from 'react';
import BookReservations from "./BookReservations";
import BookSearch from './BookSearch';

class UserPage extends Component {
    render() {
        return (
            <>
                <br/>
                <BookReservations/>
                <br/>
                <BookSearch/>
            </>
        )
    }
}

export default UserPage;