import React, {Component} from 'react'
import {Route, Redirect} from 'react-router-dom'
import AuthenticationService from './services/AuthenticationService';

class AuthenticatedRoute extends Component{
    render() {
        if (AuthenticationService.isUserLoggedIn()) {
            console.log("isAuth");
            return <Route {...this.props}/>
        } else {
            console.log("need login");
            return <Redirect to='/login'/>
        }
    }
}
export default AuthenticatedRoute