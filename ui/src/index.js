import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import ContextProvider from './common/Context'

ReactDOM.render(
    <ContextProvider>
    <App />
    </ContextProvider>,
    document.getElementById('root')
);
