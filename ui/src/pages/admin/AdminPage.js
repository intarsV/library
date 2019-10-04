import React,{Component}  from 'react';
import ReservationQueue from './ReservationQueue'
import ReservationSearch from './ReservarionSearch';
import ManageAuthor from './ManageAuthor';
import ManageBook from "./ManageBook";

class AdminPage extends Component {
    render() {
        return (
            <>
                <br/>
                <ReservationQueue/>
                <br/>
                <ReservationSearch/>
                <br/>
                <ManageAuthor/>
                <br/>
                <ManageBook/>
            </>
        )
    }
}

export default AdminPage;