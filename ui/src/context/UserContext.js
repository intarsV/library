import React, {createContext, useReducer} from 'react'

export const UserContext = createContext(null);

export default ({children}) => {

    const initialState = {showUserQueue: false, reservationQueue: [], reservationsData: []};

    const types = {
        SHOW_USER_QUEUE: 'SHOW_USER_QUEUE',
        SET_RESERVATION_QUEUE: 'SET_RESERVATION_QUEUE',
        SET_RESERVATION_DATA: 'SET_RESERVATION_DATA',
    };

    const reducer = (userData, action) => {
        switch (action.type) {
            case types.SHOW_USER_QUEUE:
                return {
                    ...userData, showUserQueue: action.payload
                };
            case types.SET_RESERVATION_QUEUE:
                return {
                    ...userData, reservationQueue: action.payload.reservationQueue
                };
            case types.SET_RESERVATION_DATA:
                return {
                    ...userData, reservationsData: action.payload.reservationsData
                };
        }
    };

    const[userData, dispatch]=useReducer(reducer, initialState);

    return <UserContext.Provider value={{userData, dispatch}}>{children}</UserContext.Provider>
}