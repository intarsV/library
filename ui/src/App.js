import React, {useContext} from 'react';
import {Route, Router, Switch} from 'react-router-dom'
import './App.css';
import LoginPage from './pages/login/LoginPage';
import AuthenticatedRoute from './common/AuthenticatedRoute';
import UserPage from "./pages/user/UserPage";
import AdminPage from "./pages/admin/AdminPage";
import UserQueue from './pages/user/UserQueue'
import {CommonContext} from './context/CommonContext'
import {UserContext} from "./context/UserContext";
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

    const {commonData} = useContext(CommonContext);
    const {userData} = useContext(UserContext);
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

    return (
        <Router history={history}>
            <div className="container">
                <div className="row">
                <div className="col-md-2 menu-side">
                    <div className="logo">
                        <h4>Super Library</h4>
                        <h6>by Initex</h6>
                    </div>
                    {commonData.userAuthority !== null && isUserLoggedIn === true &&
                    <NavMenu/>}
                </div>
                <div className="col-md-8 card-padding">
                    <Switch>
                        <Route path="/" exact render={() => <MainPage/>}/>
                        <Route path="/register" exact render={() => <Registration/>}/>
                        <AuthenticatedRoute path="/admin" exact render={() => <AdminPage/>}/>
                        <AuthenticatedRoute path="/admin/authors" render={() => <ManageAuthor/>}/>
                        <AuthenticatedRoute path="/admin/books" render={() => <ManageBook/>}/>
                        <AuthenticatedRoute path="/admin/reservations" render={() => <ReservationSearch/>}/>
                        <AuthenticatedRoute path="/admin/queue" render={() => <ReservationQueue/>}/>
                        <AuthenticatedRoute path="/admin/users" render={() => <ManageUsers/>}/>
                        <AuthenticatedRoute path="/user" exact render={() => <UserPage/>}/>
                        <AuthenticatedRoute path="/user/reservations" render={() => <BookReservations/>}/>
                        <AuthenticatedRoute path="/user/search" render={() => <UserBookSearch/>}/>
                    </Switch>
                </div>
                <div className="col-md-2 info-side">
                    <div className="login-input">
                        <Route path="/" exact render={() => <LoginPage/>}/>
                        <Route path='/login' exact render={() => <LoginPage/>}/>
                        <Route path='/register' exact render={() => <LoginPage/>}/>
                        {userData.showUserQueue &&
                        <UserQueue/>}
                    </div>
                </div>
                </div>
            </div>
        </Router>
    )
};


export default App;
