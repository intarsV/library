import React, {useContext} from 'react';
import {Link, withRouter} from 'react-router-dom';
import AuthenticationService from '../common/services/AuthenticationService';
import {CommonContext} from '../context/CommonContext';
import {adminMenu, userMenu} from '../common/Constants';
import {UserContext} from "../context/UserContext";

const NavMenu = () => {

    const {commonData, commonDispatch} = useContext(CommonContext);
    const {dispatch} = useContext(UserContext);

    const logout = () => {
        AuthenticationService.logout();
        clearUserData();
    };

    const clearUserData = () => {
        commonDispatch({type: 'USER_AUTHORITY', payload: {userAuthority: null}});
        dispatch({type: 'SET_RESERVATION_QUEUE', payload: {reservationQueue: []}});
        dispatch({type: 'SHOW_USER_QUEUE', payload: false});
    };

    return (
        <>
            {commonData.userAuthority === 'ADMIN' ?
                <>
                    {adminMenu.map((item) =>
                        <li key={item.key} className="navLinks"><Link className="navLinks" to={item.value}>{item.key}</Link></li>
                    )}
                    <li className="navLinks"><Link className="navLinks" to='/' onClick={() => logout()}>Logout</Link>
                    </li>
                </>
                :
                <>
                    {userMenu.map((item) =>
                        <li key={item.key} className="navLinks"><Link className="navLinks" to={item.value}>{item.key}</Link></li>
                    )}
                    <li className="navLinks"><Link className="navLinks" to='/' onClick={() => logout()}>Logout</Link>
                    </li>
                </>
            }
        </>
    )
};

export default withRouter(NavMenu);