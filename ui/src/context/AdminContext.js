import React, {createContext, useReducer} from 'react'

export const AdminContext = createContext(null);

export default ({children}) => {

    const initialState = {adminReservationQueue: [], authorsData: [], booksData: [],usersData:[]};

    const types = {
        ADMIN_RESERVATION_QUEUE: 'ADMIN_RESERVATION_QUEUE',
        AUTHORS_DATA: 'AUTHORS_DATA',
        BOOKS_DATA: 'BOOKS_DATA',
        USERS_DATA: 'USERS_DATA'
    };

    const reducer = (adminData, action) => {
        switch (action.type) {
            case types.ADMIN_RESERVATION_QUEUE:
                return {
                    ...adminData, adminReservationQueue: action.payload.adminReservationQueue
                };
            case types.AUTHORS_DATA:
                return {
                    ...adminData, authorsData: action.payload.authorsData
                };
            case types.BOOKS_DATA:
                return {
                    ...adminData, booksData: action.payload.booksData
                };
            case types.USERS_DATA:
                return {
                    ...adminData, usersData: action.payload.usersData
                };
            default:
                return adminData
        }
    };

    const [adminData, dispatch] = useReducer(reducer, initialState);

    return <AdminContext.Provider value={{adminData, dispatch}}>{children}</AdminContext.Provider>
}