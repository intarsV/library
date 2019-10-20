import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import StoreProvider from './common/Context'

ReactDOM.render(
    <StoreProvider>
    <App />
    </StoreProvider>,
    document.getElementById('root')
);
