import React, {Component} from 'react';
import AdminService from '../../common/services/AdminService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {Card} from 'react-bootstrap';

class ReservationsQueue extends Component {

    constructor(props) {
        super(props);
        this.state = {
            queue: [],
            reservationData: {},
            message: null
        };
        this.refreshReservations = this.refreshReservations.bind(this);
        this.handOut = this.handOut.bind(this);
    }

    componentDidMount() {
        this.refreshReservations();
    }

    refreshReservations() {
        AdminService.getReservationQueue()
            .then(
                response => {
                    //console.log(response.data);
                    this.setState({queue: response.data})
                }
            )
    }

    handOut(id) {
        this.state.reservationData['id'] = id;
        AdminService.handOut(this.state.reservationData)
            .then(
                response => {
                    //console.log(response);
                    let filteredArray = this.state.queue.filter(item => item.id !== id);
                    this.setState({queue: filteredArray});
                }
            )
    }

    render() {
        return (
            <Card md={3}>
                <div className='text-size padding-top'>
                    <h4>User reservations Queue</h4>
                   <br/>
                        <button onClick={this.refreshReservations}>Refresh</button>
                    <br/>
                    <br/>
                    <ReactTable
                        defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                        data={this.state.queue}
                        columns={[
                            {
                                minWidth: 200,
                                maxWidth: 200,
                                Header: "Title",
                                accessor: "bookTitle"
                            },
                            {
                                Header: "User",
                                accessor:"userName"
                            },
                            {
                                id: 'reservationDate',
                                Header: "Reservation date",
                                accessor: 'reservationDate',
                                Cell: props => new Date(props.value).toLocaleDateString()
                            },
                            {
                                Header: '',
                                accessor: 'id',
                                Cell: ({value}) => (
                                    <button onClick={() => this.handOut(value)}>Hand out</button>)
                            }
                        ]}
                        className="-striped -highlight text-size"
                    />
                </div>
            </Card>
        )
    }
}

export default ReservationsQueue;