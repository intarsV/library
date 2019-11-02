import React, {useState} from "react";
import UserService from "../../common/services/UserService";

const Registration = () => {

    const [userName, setUserName] = useState('');
    const [password, setPassword] = useState('');
    const [infoMessage, setInfoMessage]=useState('');

    const addUser = () => {
        UserService.register({userName: btoa(userName), password: btoa(password)})
            .then(response => {
                setInfoMessage("You have successfully registered!");
            })
            .catch((error) => {
                    setInfoMessage(error.response.data.message);
                }
            )
    };

    return (
        <div className="card">
            <h4 className="header-padding">Registration form</h4>
            <div className="row row-format">
                <h5 className="label">User name:</h5>
                <input type="text" name="username" className="input-field"
                       onChange={(event) => {
                           setUserName(event.target.value);
                           setInfoMessage('')
                       }}/>
            </div>
            <div className="row row-format">
                <h5 className="label">Password:</h5>
                <input type="password" name="password" className="input-field"
                       onChange={(event) => setPassword(event.target.value)}/>
            </div>
            <button className="button" onClick={() => addUser()}>Register</button>
            <span>{infoMessage}</span>
        </div>
    )
};

export default Registration;