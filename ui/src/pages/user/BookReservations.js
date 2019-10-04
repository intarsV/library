import React, {Component} from 'react';
import {Row, Col} from 'reactstrap';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {Card} from 'react-bootstrap';

class BookReservations extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selection:'optionOne',
            reservations: [],
            reservationData: {},
            message: null
        };
        this.refreshReservations = this.refreshReservations.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.prepareRequestData=this.prepareRequestData.bind(this);
    }

    componentDidMount() {
        this.refreshReservations();
    }

    prepareRequestData() {
        this.setState(this.state.reservationData = {});
        if (this.state.selection === 'optionOne') {
            //let optionOneData = {handOut: 'false', returned: 'false'};
            this.setState({reservationData: {handOut: 'false', returned: 'false'}});
        }
        if (this.state.selection === 'optionTwo') {
            // let optionTwoData = {handOut: 'true', returned: 'false'};
            this.setState({reservationData: {handOut: 'true', returned: 'false'}});
        }
        if (this.state.selection === 'optionThree') {
            // let optionThreeData = {handOut: 'true', returned: 'true'};
            this.setState({reservationData: {handOut: 'true', returned: 'true'}});
        }
    }

    refreshReservations() {
        this.prepareRequestData();
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
               selection: event.target.value
            },()=>this.refreshReservations()
        );
    }

    render() {
        return (
            <Card md={3}>
                <div className='text-size padding-top'>
                    <h4>User reservations</h4>
                    <Row className='space-top margin-bottom'>
                        <Col md={5} sm={7} lg={3} xs={5}>
                            Reservation status:
                        </Col>
                        <Col md={5} sm={7} lg={2} xs={5}>
                            <label>
                                <input type="radio" value='optionOne' name={this.state.selection}
                                       checked={this.state.selection==='optionOne'}
                                       onChange={this.handleChange}/>
                                Queue
                            </label>
                        </Col>
                        <Col md={5} sm={7} lg={2} xs={5}>
                            <label>
                                <input type="radio" value='optionTwo' name={this.state.selection}
                                       checked={this.state.selection==='optionTwo'}
                                       onChange={this.handleChange}/>
                                Active
                            </label>
                        </Col>
                        <Col md={5} sm={7} lg={2} xs={5}>
                            <label>
                                <input type="radio" value='optionThree' name={this.state.selection}
                                       checked={this.state.selection==='optionThree'}
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