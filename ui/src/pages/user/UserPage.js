import React, {useContext, useEffect} from 'react';
import UserService from "../../common/services/UserService";
import {UserContext} from "../../context/UserContext";

const UserPage = () => {

    const {userData, dispatch} = useContext(UserContext);

    useEffect(() => {
            UserService.searchReservations({status: 'QUEUE'})
                .then(response => {
                        dispatch({type: 'SET_RESERVATION_QUEUE', payload: {reservationQueue: response.data}});
                    }
                );
        }, []
    );

    useEffect(() => {
            if (userData.reservationQueue.length > 0) {
                dispatch({type: 'SHOW_USER_QUEUE', payload: true});
            }
        }, [userData.reservationQueue]
    );

    return (
        <>
            <h1 className="header-padding">This is USER section.</h1>
                Menu options (on the left side)<br/>
                "My reservations" -> You can view your reservations: active or history;<br/>
                "Search book" -> You can search books and add to the queue;
        </>
    )
};

export default UserPage;