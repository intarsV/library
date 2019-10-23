import React, {useContext, useEffect, useState} from 'react';
import Card from "react-bootstrap/Card";
import {Context} from "../../common/Context";
import {Col, Row} from "react-bootstrap";
import ReactTable from "react-table";
import AdminService from "../../common/services/AdminService";

const ManageUsers=()=>{

    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const{adminUserData: [userData, setUserData]}=useContext(Context);

    useEffect(() => {
            AdminService.getUsers()
                .then(
                    response => {
                        if (response.status === 200) {
                            setUserData(response.data);
                        }
                    }
                )
        }, []
    );

    const addUser = () => {
        let filteredArray = userData.filter(item => item.userName === userName);
        if (filteredArray.length > 0) {
            alert("There is already author with name!");
        } else {


            AdminService.addUser({userName:  btoa(userName), password:  btoa(password)})
                .then(
                    response => {
                        // setAuthorData([...authorData, response.data]);
                    })
        }

    };

    const disableUser = (id) => {
        // AdminService.deleteAuthor({id: id})
        //     .then(response => {
        //             // let filteredArray = authorData.filter(item => item.id !== id);
        //             // setAuthorData(filteredArray);
        //         }
        //     )
    };

    return(
        <div>
            <Card>
                <h4>Users</h4>
                <Row>
                    <Col xl={3}>
                        <input className='col-width-height ' type='text' name='uaserName'
                               value={userName} onChange={(event) => setUserName(event.target.value)}/>
                        <input className='col-width-height ' type='password' name='password'
                               value={password} onChange={(event) => setPassword(event.target.value)}/>
                    </Col>
                    <Col>
                        <button className=' button ' onClick={() => addUser()}>Add author</button>
                    </Col>
                </Row>
                <br/>
                <ReactTable
                    defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={false}
                    data={userData}
                    columns={[
                        {
                            maxWidth: 500,
                            Header: "User name",
                            accessor: "userName"
                        },
                        {
                            className:"columnAlignCenter",
                            accessor: "id",
                            Cell: ({value}) => (
                                <button onClick={() => disableUser(value)}>Delete</button>
                            )
                        }
                    ]}
                    className="-striped -highlight text-size"
                />
            </Card>
        </div>
    )
};

export default ManageUsers;