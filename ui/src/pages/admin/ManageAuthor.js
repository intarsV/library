import React, {useContext, useEffect, useState} from 'react';
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
        if (Validate.validateForm(AuthorFieldList, setInfoMessage)) {
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
        <div class="card">
            <h4>Manage Authors</h4>
            <div className="row">
                <div className="col-sm">
                    <input className='col-width-height' type='text' id='authorName'
                           value={authorName} onChange={(event) => {
                        Validate.validateInput(event.target.id, "text", setInfoMessage);
                        setAuthorName(event.target.value);
                    }}
                    />
                    <div id="authorName_error" className="error-field-hide">Author name field not valid!</div>
                </div>
                <div className="col-sm padding-bottom">
                    <button className='button' onClick={() => addAuthor()}>Add author</button>
                </div>
                <div className={infoMessage.type === 'error' ? " col-sm error-text" : " col-sm info-text"}>
                       <span>
                       {infoMessage.msg}
                       </span>
                </div>
            </div>
            <ReactTable
                defaultPageSize={10} minRows={1} noDataText={'No data found'} showPagination={authorData.length > 10}
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
        </div>
    )
};

export default ManageAuthor