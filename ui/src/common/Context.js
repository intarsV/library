import React,{useState, createContext} from 'react'

export const Context = createContext(null);

export default ({children}) => {
    /*login info*/
    const [userName, setUserName] = useState('');
    const [userAuthority, setUserAuthority] = useState(sessionStorage.getItem('AUTHORITY'));
    const [hasLoginFailed, setHasLoginFailed] = useState(false);
    const [showSuccessMessage, setShowSuccessMessage] = useState(false);

    /* User data*/
    const [reservationQueue, setReservationQueue] = useState([]);
    const [reservationsData, setReservationData] = useState([]);

    /*Admin data*/
    const [adminReservationQueue, setAdminReservationQueue] = useState([]);
    const [authorData, setAuthorData]=useState([]);
    const [booksData, setBooksData] = useState([]);

    const context = {
        username: [userName, setUserName],
        userAuthority: [userAuthority, setUserAuthority],
        hasLoginFailed: [hasLoginFailed, setHasLoginFailed],
        showSuccessMessage: [showSuccessMessage, setShowSuccessMessage],
        userReservationQueue: [reservationQueue, setReservationQueue],
        userReservationData: [reservationsData, setReservationData],
        adminReservationQueue: [adminReservationQueue, setAdminReservationQueue],
        adminAuthorData: [authorData, setAuthorData],
        adminBookData: [booksData, setBooksData],
    };

    return <Context.Provider value={context}>{children}</Context.Provider>
}