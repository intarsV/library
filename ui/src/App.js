import React,{Component}  from 'react';
import {BrowserRouter as Router, Route,Switch} from 'react-router-dom'
import './App.css';
import LoginPage from './pages/login/LoginPage';
import LogoutPage from './pages/login/LoginPage';
import BookPage from './pages/book/BookPage';
import AuthenticatedRoute from './common/AuthenticatedRoute';

class App extends Component {

    render() {
        return (
            <>
                <Router>
                    <>
                        <Switch>
                            <Route path='/' exact component={LoginPage}/>
                            <Route path='/login' exact component={LoginPage}/>
                            <AuthenticatedRoute path='/logout' exact component={LogoutPage}/>
                            <AuthenticatedRoute path='/books' exact component={BookPage}/>
                        </Switch>
                    </>
                </Router>

            </>
        )
    }
}

export default App;


{/*<div className="container">*/}
{/*  <BookPage />*/}
{/*</div>*/}