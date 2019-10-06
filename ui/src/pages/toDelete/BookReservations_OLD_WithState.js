import React, {Component, useState } from 'react';
import {Row, Col} from 'react-bootstrap';
import BookService from '../../common/services/UserService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {Card} from 'react-bootstrap';

class BookReservations_OLD_WithState extends Component {

    constructor(props) {
        super(props);
        this.state = {
            selection:'optionOne',
            reservations: [],
            requestData: {},
            message: null
        };
        this.getReservations = this.getReservations.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.prepareRequestData = this.prepareRequestData.bind(this);
    }

    // componentDidMount() {
    //     this.prepareRequestData();
    // }

    prepareRequestData() {
        this.setState(this.state.requestData = {});
        if (this.state.selection === 'optionOne') {
            this.setState({requestData: {handOut: 'false', returned: 'false'}}, () => {
                this.getReservations();
            })
        }
        if (this.state.selection === 'optionTwo') {
            this.setState({requestData: {handOut: 'true', returned: 'false'}}, () => {
                this.getReservations();
            });
        }
        if (this.state.selection === 'optionThree') {
            this.setState({requestData: {handOut: 'true', returned: 'true'}}, () => {
                this.getReservations();
            });
        }
    }

    getReservations() {
        BookService.getReservations(this.state.requestData)
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
            },()=>this.prepareRequestData()
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

export default BookReservations_OLD_WithState;