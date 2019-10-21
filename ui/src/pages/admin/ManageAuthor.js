import React, {useContext, useEffect, useState} from 'react';
import {Card, Col, Row} from 'react-bootstrap';
import ReactTable from "react-table";
import 'react-table/react-table.css';
import AdminService from "../../common/services/AdminService";
import {Context} from "../../common/Context";

const ManageAuthor = () => {

    const [authorName, setAuthorName]=useState('');
    const {adminAuthorData: [authorData, setAuthorData]} = useContext(Context);

    useEffect(() => {
            AdminService.getAllAuthors()
                .then(
                    response => {
                        if (response.status === 200) {
                            setAuthorData(response.data);
                        }
                    }
                )
        }, []
    );

    const addAuthor = () => {
        let filteredArray = authorData.filter(item => item.name === authorName);
        if (filteredArray.length > 0) {
             alert("There is already author with name!");
        } else {
            AdminService.addAuthor({name: authorName})
                .then(
                    response => {
                        setAuthorData([...authorData, response.data]);
                    })
        }

    };

    const deleteAuthor = (id) => {
        AdminService.deleteAuthor({id: id})
            .then(response => {
                    let filteredArray = authorData.filter(item => item.id !== id);
                    setAuthorData(filteredArray);
                }
            )
    };

        return (
            <div className="small-card-padding">
                <Card>
                    <h4>Authors</h4>
                    <Row>
                        <Col xl={3}>
                            <input className='col-width-height ' type='text' name='authorName'
                                   value={authorName} onChange={(event) => setAuthorName(event.target.value)}/>
                        </Col>
                        <Col>
                            <button className=' button ' onClick={() => addAuthor()}>Add author</button>
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
                                className:"columnAlignCenter",
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