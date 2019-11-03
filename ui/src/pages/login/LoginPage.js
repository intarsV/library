import React, { useContext, useState } from 'react';
import AuthenticationService from '../../common/services/AuthenticationService'
import {Context} from "../../common/Context";
import history from '../../common/history';
import {Link} from "react-router-dom";

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
                    sessionStorage.setItem("AUTHORITY", response.data.message);
                    setHasLoginFailed(false);
                    history.push('/' + response.data.message.toLowerCase());
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
                <input size="10" name='username' className="login-input-field"
                       onChange={(event) => setUserName(event.target.value)}/>
                <br/>
                password:
                <input size="10" height="5" type='password' name='password' className="login-input-field"
                       onChange={(event) => setPassword(event.target.value)}/>
                       <br/>
                <button className='button-small-margin' onClick={()=>loginClicked()}> Login</button>
                <br/>
                {hasLoginFailed && <div className="error-text">Invalid Credentials!</div>}
                <li className="navLinks"><Link  to='/register'>register</Link>
                </li>
            </div>
        </div>
    )
    };

export default LoginPage



