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
            <h4 className="header-padding">Manage Authors</h4>
            <div className="row row-format">
                <h5 className="label">Author name:</h5>
                    <input className='input-field' type='text' id='authorName' value={authorName}
                           onChange={(event) => {
                               Validate.validateInput(event.target.id, "text", setInfoMessage);
                               setAuthorName(event.target.value);
                           }}
                    />
                <span id="authorName_error" className="error-field-hide">Author name field not valid!</span>
                <span className={infoMessage.type === 'error' ? " col-3 error-text" : " col-3 info-text"}>
                       {infoMessage.msg}
                </span>
            </div>
            <button className="button" onClick={() => addAuthor()}>Add author</button>
            <ReactTable
                 minRows={1} noDataText={'No data found'} showPagination={false} data={authorData}
                className={authorData.length < 10 ? '-striped -highlight table-format'
                                                  : '-striped -highlight table-format-large'}
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
            />
        </div>
    )
};

export default ManageAuthor