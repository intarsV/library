import React, {useContext} from 'react';
import {Link, withRouter} from 'react-router-dom';
import AuthenticationService from '../common/services/AuthenticationService';
import {Context} from '../common/Context';
import {adminMenu, userMenu} from '../common/Constants';


const NavMenu = () => {

    const {userAuthority: [userAuthority, setUserAuthority]} = useContext(Context);

    const logout = () => {
        AuthenticationService.logout();
        sessionStorage.clear();
        setUserAuthority(null);
    };

    return (
        <>
            {userAuthority === 'ADMIN' ?
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