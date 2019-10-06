import React, {Component} from 'react';
import AdminService from '../../common/services/AdminService';
import {Card, Alert} from 'react-bootstrap';
import ReactTable from 'react-table';
import 'react-table/react-table.css';
import {Row, Col} from 'react-bootstrap';

class ManageAuthor_OLD_State extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            authorName: '',
            authorData: [],
            requestData: {},
            message: null
        };
        this.getAllAuthors = this.getAllAuthors.bind(this);
        this.deleteAuthor = this.deleteAuthor.bind(this);
        this.addAuthor = this.addAuthor.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.prepareRequest = this.prepareRequest.bind(this);
    }

    componentDidMount() {
        this.getAllAuthors();
    }

    handleChange(event) {
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        );
    }

    getAllAuthors() {
        AdminService.getAllAuthors()
            .then(
                response => {
                    this.setState({authorData: response.data});
                }
            )
    }

    deleteAuthor(id) {
        this.state.requestData['id'] = id;
        AdminService.deleteAuthor(this.state.requestData)
            .then(
                response => {
                    let filteredArray = this.state.authorData.filter(item => item.id !== id);
                    this.setState({authorData: filteredArray});
                }
            )
    }

    prepareRequest() {
        this.setState(this.state.requestData = {});
        if (this.state.authorName !== '') {
            this.state.requestData['name'] = this.state.authorName;
        }

    }

    addAuthor() {
        let filteredArray = this.state.authorData.filter(item => item.name === this.state.authorName);
        if (filteredArray.length > 0) {
            alert("There is already author with name!");
        } else {
            this.prepareRequest();
            AdminService.addAuthor(this.state.requestData)
                .then(
                    response => {
                        let newAuthorData = this.state.authorData.push(response.data);
                        this.setState({newAuthorData: newAuthorData});
                    })
        }
    }

    render() {
        return (
            <Card>
                <div className='text-size padding-top'>
                    <h4>Authors</h4>
                    <Row>
                        <Col xl={3}>
                            <input className='col-width-height ' type='text' name='authorName'
                                   value={this.state.authorName} onChange={this.handleChange}/>
                        </Col>
                        <Col>
                            <button className=' button ' onClick={this.addAuthor}>Add author</button>
                        </Col>
                    </Row>
                    <br/>
                    <ReactTable
                        defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                        data={this.state.authorData}
                        columns={[
                            {
                                Header: "Author name",
                                accessor: "name"
                            },
                            {
                                accessor: "id",
                                Cell: ({value}) => (
                                    <button onClick={() => this.deleteAuthor(value)}>Delete</button>
                                )
                            }
                        ]}
                        className="-striped -highlight text-size"
                    />
                </div>
            </Card>
        )
    }
}

export default ManageAuthor_OLD_State;