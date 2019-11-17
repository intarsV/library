import React, { useContext, useState } from 'react';
import AuthenticationService from '../../common/services/AuthenticationService'
import {CommonContext} from "../../context/CommonContext";
import history from '../../common/history';
import {Link} from "react-router-dom";

const LoginPage = () => {

    const {commonData, commonDispatch} = useContext(CommonContext);
    const [password, setPassword] = useState('');

    const loginClicked = () => {
        AuthenticationService
            .executeBasicAuthenticationService(commonData.userName, password)
            .then(response => {
                if (response.data.message === 'ADMIN' || response.data.message === 'USER') {
                    AuthenticationService.registerSuccessfulLogin(commonData.userName, password);
                    commonDispatch({type: 'USER_AUTHORITY', payload: {userAuthority: response.data.message}});
                    sessionStorage.setItem("AUTHORITY", response.data.message);
                    commonDispatch({type: 'LOGIN_FAILED', payload: false});
                    history.push('/' + response.data.message.toLowerCase());
                } else {
                    commonDispatch({type: 'LOGIN_FAILED', payload: true});
                    commonDispatch({type: 'SHOW_SUCCESS_MESSAGE', payload: false});
                }
            }).catch(() => {
            commonDispatch({type: 'LOGIN_FAILED', payload: true});
            commonDispatch({type: 'SHOW_SUCCESS_MESSAGE', payload: false});
        })
    };

    return (
        <div>
            <h4>Login</h4>
            <div>
                login:
                <input size="10" name='username' className="login-input-field"
                       onChange={(event) =>
                           commonDispatch({type: 'USER_NAME', payload: {userName: event.target.value}})}/>
                <br/>
                password:
                <input size="10" height="5" type='password' name='password' className="login-input-field"
                       onChange={(event) => setPassword(event.target.value)}/>
                       <br/>
                <button className='button-small-margin' onClick={()=>loginClicked()}> Login</button>
                <br/>
                {commonData.hasLoginFailed && <div className="error-text">Invalid Credentials!</div>}
                <li className="navLinks"><Link  to='/register'>register</Link>
                </li>
            </div>
        </div>
    )
    };

export default LoginPage



