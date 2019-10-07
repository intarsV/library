import React, { Component} from 'react';
import { Link, withRouter } from 'react-router-dom';
import AuthenticationService from './common/services/AuthenticationService';


class NavMenu extends Component{
    render() {
        const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

        return (
            <header>
                <nav className="navbar navbar-expand-md navbar-light ">

                    <ul className="navbar-nav">
                        <li><Link className="nav-link" to="/courses">Courses</Link></li>
                    </ul>
                    <ul className="navbar-nav navbar-collapse justify-content-end">
                        {!isUserLoggedIn && <li><Link className="nav-link" to="/login">Login</Link></li>}
                        {isUserLoggedIn &&
                        <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout}>Logout</Link>
                        </li>}
                    </ul>
                </nav>
            </header>
        )
    }
}

export default withRouter(NavMenu);