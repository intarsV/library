import React, {createContext, useReducer} from 'react'

export const CommonContext = createContext(null);

export default ({children}) => {

    const initialState = {
        userName: '', userAuthority: sessionStorage.getItem('AUTHORITY'),
        hasLoginFailed: false, showSuccessMessage: false
    };

    const types = {
        USER_NAME: 'USER_NAME',
        USER_AUTHORITY: 'USER_AUTHORITY',
        LOGIN_FAILED: 'LOGIN_FAILED',
        SHOW_SUCCESS_MESSAGE: 'SHOW_SUCCESS_MESSAGE',
    };

    const reducer = (commonData, action) => {
        switch (action.type) {
            case types.USER_NAME:
                return {
                    ...commonData, userName: action.payload.userName
                };
            case types.USER_AUTHORITY:
                return {
                    ...commonData, userAuthority: action.payload.userAuthority
                };
            case types.LOGIN_FAILED:
                return {
                    ...commonData, hasLoginFailed: action.payload
                };
            case types.SHOW_SUCCESS_MESSAGE:
                return {
                    ...commonData, showSuccessMessage: action.payload
                };
            default:
                return commonData
        }
    };

    const [commonData, commonDispatch] = useReducer(reducer, initialState);

    return <CommonContext.Provider value={{commonData, commonDispatch}}>{children}</CommonContext.Provider>
}