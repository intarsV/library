import React,{Component}  from 'react';
import BookReservations from "./BookReservations";
import UserBookSearch from "./UserBookSearch";

class UserPage extends Component {
    render() {
        return (
            <>
                <br/>
                <BookReservations/>
                <br/>
                <UserBookSearch/>
            </>
        )
    }
}

export default UserPage;