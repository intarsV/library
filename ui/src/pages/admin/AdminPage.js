import React,{Component}  from 'react';
import ReservationQueue from './ReservationQueue'
import ReservationSearch from './ReservarionSearch';
import ManageAuthor from './ManageAuthor';

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
            </>
        )
    }
}

export default AdminPage;