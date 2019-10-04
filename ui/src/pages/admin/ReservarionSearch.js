import React, {Component} from 'react';
import {Row, Col} from 'react-bootstrap';
import AdminService from '../../common/services/AdminService'
import ReactTable from "react-table";
import 'react-table/react-table.css';
import {returned} from '../../common/Constants';
import {Card} from 'react-bootstrap';

class ReservationSearch extends Component{

    constructor(props) {
        super(props);
        this.state = {
            bookTitle: "",
            userName: "",
            handOut: "",
            returned: "",
            reservations: [],
            searchData: {},
            message: null
        };
        this.handleChange = this.handleChange.bind(this);
        this.searchReservations = this.searchReservations.bind(this);
    }

    prepareRequest(){
        this.setState(this.state.searchData={});
        if(this.state.bookTitle!==''){
            this.state.searchData['bookTitle']=this.state.bookTitle;
        }
        if(this.state.userName!==''){
            this.state.searchData['userName']=this.state.userName;
        }
        this.state.searchData['handOut']=true;
        if(this.state.returned!==''){
            this.state.searchData['returned']=this.state.returned;
        }
    }

    searchReservations() {
        this.prepareRequest();

        AdminService.searchReservations(this.state.searchData)
            .then(
                response => {
                    //console.log(response);
                    this.setState({reservations: response.data})
                }
            )
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        );
    }

    render() {
        return (
            <Card>
                <div className='text-size padding-top'>
                    <h4>Search reservations</h4>
                    <Row className='space-top'>
                        <Col md={3} sm={5} lg={2} xs={3}>
                            Book title:
                        </Col>
                        <Col xl={3}>
                            <input className='col-width-height ' type='text' name='bookTitle' value={this.state.bookTitle}
                                   onChange={this.handleChange}/>
                        </Col>
                    </Row>
                    <Row className='space-top'>
                        <Col md={3} sm={5} lg={2} xs={3}>
                            User name:
                        </Col>
                        <Col xl={3}>
                            <input className='col-width-height ' type='text' name='userName'
                                   value={this.state.userName}
                                   onChange={this.handleChange}/>
                        </Col>
                        <Col>
                            <button className=' button ' onClick={this.searchReservations}> Search</button>
                        </Col>
                    </Row>
                    <Row className='space-top margin-bottom'>
                        <Col md={3} sm={5} lg={2} xs={3}>
                            Return status :
                        </Col>
                        <Col xl={3}>
                            <select className='col-width-height' name='returned' value={this.state.returned}
                                    onChange={this.handleChange}>
                                <option value={''}/>
                                {returned.map(returned =>
                                    <option value={returned.value}>{returned.key}</option>)
                                }
                            </select>
                        </Col>
                    </Row>
                    <ReactTable
                        defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                        data={this.state.reservations}
                        columns={[
                            {
                                minWidth: 200,
                                Header: "Title",
                                accessor: "bookTitle"
                            },
                            {
                                minWidth: 200,
                                Header: "User",
                                accessor: "userName"
                            },
                            {
                                id: 'reservationDate',
                                Header: "Date",
                                accessor: 'reservationDate',
                                Cell: props => new Date(props.value).toLocaleDateString()
                            },
                            {
                               Header: "Returned",
                                accessor:"returned",
                                Cell: ({value}) => (
                                    <input type="checkbox" defaultChecked={value} disabled='true'/>)
                            }

                        ]}
                        className="-striped -highlight text-size"
                    />
                </div>
            </Card>
        )
    }
}

export default ReservationSearch;