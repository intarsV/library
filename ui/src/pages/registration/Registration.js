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
            <h4>Registration form</h4>
            <div className="info">
                user name:
                <input size="20" type='text' name='username' className="input-field text-size"
                       onChange={(event) => {
                           setUserName(event.target.value);
                           setInfoMessage('')
                       }}/>
                <br/>
                password:
                <input size="20" height="5" type='password' name='password' className="input-field text-size"
                       onChange={(event) => setPassword(event.target.value)}/>
                <br/>
                <button className=' button ' onClick={() => addUser()}>Register</button>
                <br/>
                <span>{infoMessage}</span>

            </div>
        </div>
    )
};

export default Registration;