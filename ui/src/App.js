import React, {useContext} from 'react';
import { Router, Route, Switch} from 'react-router-dom'
import './App.css';
import LoginPage from './pages/login/LoginPage';
import AuthenticatedRoute from './common/AuthenticatedRoute';
import UserPage from "./pages/user/UserPage";
import AdminPage from "./pages/admin/AdminPage";
import UserQueue from './pages/user/UserQueue'
import {Context} from './common/Context'
import history from './common/history';
import NavMenu from "./menu/NavMenu";
import UserBookSearch from "./pages/user/UserBookSearch";
import BookReservations from "./pages/user/BookReservations";
import ManageAuthor from "./pages/admin/ManageAuthor";
import ManageBook from "./pages/admin/ManageBook";
import ReservationSearch from "./pages/admin/ReservationSearch";
import ReservationQueue from "./pages/admin/ReservationQueue";
import AuthenticationService from "./common/services/AuthenticationService";
import MainPage from "./pages/main/MainPage";
import ManageUsers from "./pages/admin/ManageUsers";
import Registration from "./pages/registration/Registration";

const App = () => {

    const {userAuthority: [userAuthority, setAuthority]} = useContext(Context);
    const {showUserQueue:[showUserQueue, setShowUserQueue]} = useContext(Context);
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

    return (
        <Router history={history}>
            <div class="container">
                <div className="row">
                <div class="col-md-2 menu-side">
                    <div className="logo">
                        <h4>Super Library </h4>
                        <h6>by Initex</h6>
                    </div>
                    {userAuthority !== null && isUserLoggedIn === true &&
                    <NavMenu/>}
                </div>
                <div class="col-md-8 card-padding">
                    <Switch>
                        <Route path="/" exact render={() => <MainPage/>}/>
                        <Route path="/register" exact render={() => <Registration/>}/>
                        <AuthenticatedRoute path="/admin" exact render={() => <AdminPage/>}/>
                        <AuthenticatedRoute path="/admin/add/author" render={() => <ManageAuthor/>}/>
                        <AuthenticatedRoute path="/admin/add/book" render={() => <ManageBook/>}/>
                        <AuthenticatedRoute path="/admin/reservations" render={() => <ReservationSearch/>}/>
                        <AuthenticatedRoute path="/admin/queue" render={() => <ReservationQueue/>}/>
                        <AuthenticatedRoute path="/admin/users" render={() => <ManageUsers/>}/>
                        <AuthenticatedRoute path="/user" exact render={() => <UserPage/>}/>
                        <AuthenticatedRoute path="/user/reservations" render={() => <BookReservations/>}/>
                        <AuthenticatedRoute path="/user/search" render={() => <UserBookSearch/>}/>
                    </Switch>
                </div>
                <div class="col-md-2 info-side">
                    <div className="login-input">
                        <Route path="/" exact render={() => <LoginPage/>}/>
                        <Route path='/login' exact render={() => <LoginPage/>}/>
                        <Route path='/register' exact render={() => <LoginPage/>}/>
                        {showUserQueue &&
                        <UserQueue/>}
                    </div>
                </div>
                </div>
            </div>
        </Router>
    )
};


export default App;
