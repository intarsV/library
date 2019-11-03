import React, {useState} from "react";
import UserService from "../../common/services/UserService";
import Validate from "../../common/Validation";
import {registrationFieldList} from "../../common/Constants"

const Registration = () => {

    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [infoMessage, setInfoMessage]=useState('');

    const addUser = () => {
        if (Validate.validateForm(registrationFieldList, setInfoMessage)) {
        UserService.register({userName: btoa(userName), password: btoa(password)})
            .then(response => {
                setUserName('');
                setPassword('');
                setInfoMessage("You have successfully registered!");
            })
            .catch((error) => {
                    setInfoMessage(error.response.data.message);
                }
            )}
    };

    return (
        <div className="card">
            <h4 className="header-padding">Registration form</h4>
            <div className="row row-format">
                <h5 className="label">User name:</h5>
                <input type="text" id="userName" className="input-field" value={userName}
                       onChange={(event) => {
                           Validate.validateInput(event.target.id, "reg-login", setInfoMessage);
                           setUserName(event.target.value);
                       }}/>
                <span id="userName_error" className="error-field-hide">
                    User name must be at least 3 letters long!
                </span>
            </div>
            <div className="row row-format">
                <h5 className="label">Password:</h5>
                <input type="password" id="password" className="input-field" value={password}
                       autoComplete="new-password"
                       onChange={(event) => {
                           Validate.validateInput(event.target.id, "reg-password", setInfoMessage);
                           setPassword(event.target.value)
                       }}/>
                <span id="password_error" className="error-field-hide">
                    Password must be at least 3 letters or numbers!
                </span>
            </div>
            <button className="button" onClick={() => addUser()}>Register</button>
            <span>{infoMessage}</span>
        </div>
    )
};

export default Registration;