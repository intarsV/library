import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import CommonContextProvider from './context/CommonContext'
import AdminContextProvider from "./context/AdminContext";
import UserContextProvider from "./context/UserContext";

ReactDOM.render(
    <CommonContextProvider>
        <AdminContextProvider>
            <UserContextProvider>
                <App/>
            </UserContextProvider>
        </AdminContextProvider>
    </CommonContextProvider>,
    document.getElementById('root')
);
