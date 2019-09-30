import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import './App.css';
import LoginPage from './pages/login/LoginPage';
import LogoutPage from './pages/login/LoginPage';
import AuthenticatedRoute from './common/AuthenticatedRoute';
import BookSearch from "./pages/user/BookSearch";
import {pageRoutes} from "./common/Constants";
import UserPage from "./pages/user/UserPage";
import AdminPage from "./pages/admin/AdminPage";
import BookReservations from "./pages/user/BookReservations";
import AdminService from "./common/services/AdminService";

class App extends Component {


    render() {
        return (
            <Router>
                    <div id='logo-menu'>
                        <h4>Super Library </h4>
                        <h6>by Initex</h6>
                    </div>
                    <div id='library-content'>
                        <Switch>
                        <Route path="/" exact component={LoginPage}/>
                        <Route  path='/login' exact component={LoginPage}/>
                        <AuthenticatedRoute path='pageRoutes.logoutPage'  exact component={LogoutPage}/>
                        <AuthenticatedRoute path="/admin/page" exact component={AdminPage}/>
                        <AuthenticatedRoute path="/user/page" exact component={UserPage}/>
                        {/*<Route exact path={pageRoutes.loginPage} component={LoginPage}/>*/}
                        {/*<AuthenticatedRoute exact path={pageRoutes.logoutPage} component={LogoutPage}/>*/}
                        {/*<AuthenticatedRoute exact path={pageRoutes.bookSearch} component={BookSearch}/>*/}
                        </Switch>
                    </div>
            </Router>

        )
    }
}

export default App;
