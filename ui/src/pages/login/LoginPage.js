import React, { useContext, useState } from 'react';
import AuthenticationService from '../../common/services/AuthenticationService'
import {Context} from "../../common/Context";
import history from '../../common/history';

const LoginPage=()=> {

    const {username: [userName, setUserName]} = useContext(Context);
    const [password, setPassword] = useState('');
    const {userAuthority: [userAuthority, setUserAuthority]} = useContext(Context);
    const {hasLoginFailed: [hasLoginFailed, setHasLoginFailed]} = useContext(Context);
    const {showSuccessMessage: [showSuccessMessage, setShowSuccessMessage]}=useContext(Context);

    const loginClicked=()=> {
        AuthenticationService
            .executeBasicAuthenticationService(userName, password)
            .then(response => {
                if (response.data.message === 'ADMIN' || response.data.message === 'USER') {
                    AuthenticationService.registerSuccessfulLogin(userName, password);
                    setUserAuthority(response.data.message);
                    history.push('/' + response.data.message.toLowerCase());
                    sessionStorage.setItem("AUTHORITY", response.data.message);
                    setHasLoginFailed(false);
                } else {
                    setHasLoginFailed(true);
                    setShowSuccessMessage(false);
                }
            }).catch(() => {
            setHasLoginFailed( true);
            setShowSuccessMessage( false);
        })
    };

    return (
        <div>
            <h4>Login</h4>
            <div>
                login:
                <input size="10" type='text' name='username' className="input-field text-size"
                       onChange={(event) => setUserName(event.target.value)}/>
                <br/>
                password:
                <input size="10" height="5" type='password' name='password' className="input-field text-size"
                       onChange={(event) => setPassword(event.target.value)}/>
                       <br/>
                <button className='button' onClick={loginClicked}> Login</button>
                <br/>
                {hasLoginFailed && <div className="error-text">Invalid Credentials!</div>}
            </div>
        </div>
    )
    };

export default LoginPage



