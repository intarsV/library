import React, {useContext, useEffect, useState} from 'react';
import {Card, Col, Row} from 'react-bootstrap';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import AdminService from "../../common/services/AdminService";
import {Context} from "../../common/Context";
import Validate from "../../common/Validation";
import {AuthorFieldList} from "../../common/Constants"

const ManageAuthor = () => {

    const [authorName, setAuthorName] = useState('');
    const {adminAuthorData: [authorData, setAuthorData]} = useContext(Context);
    const [infoMessage, setInfoMessage] = useState({type:'', msg: ''});

    useEffect(() => {
            AdminService.getAllAuthors()
                .then(response => {
                        setAuthorData(response.data);
                    })
                .catch((error) => {
                    message('error', error.response.data.message);
                })
        }, []
    );

    const addAuthor = () => {
        if ( Validate.validateForm(AuthorFieldList,  message)) {
            AdminService.addAuthor({name: authorName})
                .then(response => {
                    setAuthorData([...authorData, response.data]);
                    setAuthorName('');
                    message('info', 'Author added successfully!');
                })
                .catch((error) => {
                    message('error', error.response.data.message);
                })
        }
    };

    const deleteAuthor = (id) => {
        AdminService.deleteAuthor({id: id})
            .then(response => {
                let filteredArray = authorData.filter(item => item.id !== id);
                setAuthorData(filteredArray);
                message('info', 'Author deleted successfully!');
            })
            .catch((error) => {
                message('error', error.response.data.message);
            })
    };

    const message = (type, msg) => {
        setInfoMessage ({type: type, msg: msg})
    };

    return (
        <div className="small-card-padding">
            <Card>
                <h4>Manage Authors</h4>
                <Row>
                    <Col >
                        <input className='col-width-height' type='text' id='Author name'
                               value={authorName} onChange={(event) => {
                            Validate.validateInput(event.target.id, message, "text");
                            setAuthorName(event.target.value);}}
                        />
                    </Col>
                    <Col>
                        <button className='button' onClick={() => addAuthor()}>Add author</button>
                    </Col>
                    <Col className={infoMessage.type === 'error' ? "error-text" : "info-text"}>
                    <span>
                       {infoMessage.msg}
                    </span>
                    </Col>
                </Row>
                <br/>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={authorData}
                    columns={[
                        {
                            maxWidth: 500,
                            Header: "Author name",
                            accessor: "name"
                        },
                        {
                            className: "columnAlignCenter",
                            accessor: "id",
                            Cell: ({value}) => (
                                <button onClick={() => deleteAuthor(value)}>Delete</button>
                            )
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </Card>
        </div>
    )
};

export default ManageAuthor