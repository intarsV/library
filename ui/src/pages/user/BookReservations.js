import React, {Component} from 'react';
import {Row, Col} from 'reactstrap';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {returned} from '../../common/Constants';
import {Card} from 'react-bootstrap';

class BookReservations extends Component {

    constructor(props) {
        super(props);
        this.state = {
            returned: "false",
            reservations: [],
            reservationData: {},
            message: null
        };
        this.refreshReservations = this.refreshReservations.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
        this.refreshReservations();
    }

    refreshReservations() {
        this.state.reservationData['returned'] = this.state.returned;
        BookService.getReservations(this.state.reservationData)
            .then(
                response => {
                    this.setState({reservations: response.data})
                }
            )
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            },()=>this.refreshReservations()
        );
    }

    render() {
        return (
            <Card>
                <div className='text-size padding-top'>
                    <h2>User reservations</h2>
                    <Row className='space-top margin-bottom'>
                        <Col md={5} sm={7} lg={2} xs={3}>
                            Reservation status:
                        </Col>
                        <Col xl={3}>
                            <label>
                                <input type="radio"  value='false' name='returned'
                                       checked={this.state.returned === 'false'}
                                       onChange={this.handleChange}/>
                                Active
                            </label>
                        </Col>
                        <Col xl={3}>
                            <label>
                                <input type="radio"  value='true' name='returned'
                                       checked={this.state.returned === 'true'}
                                       onChange={this.handleChange}/>
                                History
                            </label>
                        </Col>
                    </Row>
                    <ReactTable
                        defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                        data={this.state.reservations}
                        columns={[
                            {
                                show: false,
                                Header: "Id",
                                accessor: "id"
                            },
                            {
                                minWidth: 200,
                                maxWidth: 200,
                                Header: "Title",
                                accessor: "bookTitle"
                            },
                            {
                                id: 'reservationDate',
                                Header: "Reservation date",
                                accessor: 'reservationDate',
                                Cell: props => new Date(props.value).toLocaleDateString()
                            }
                        ]}
                        className="-striped -highlight text-size"
                    />
                </div>
            </Card>
        )
    }
}

export default BookReservations;