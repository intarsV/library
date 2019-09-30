import React, {Component} from 'react'
import AdminService from '../../common/services/AdminService';
import {Card, Alert} from 'react-bootstrap';
import ReactTable from 'react-table';
import 'react-table/react-table.css';
import {Row, Col} from 'react-bootstrap';
import {Form} from 'react-bootstrap';

class ManageBook extends Component{
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            title: '',
            authorName: '',
            genre: '',
            authorData:[],
            requestData:{},
        }
    }

    render(){
        return (
            <Card>
                <div>
                    <Form>

                    </Form>

                </div>
            </Card>
        )
    }
}
export default ManageBook;