import axios from 'axios';
import {API_URL} from '../Constants'

export const USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';

class AuthenticationService{

    executeBasicAuthenticationService(username, password) {
        return axios.get(`${API_URL}/basicauth`,
            {headers: {authorization: this.createBasicAuthToken(username, password)}})
    }

    createBasicAuthToken(username, password) {
        return 'Basic ' + window.btoa(username + ":" + password)
    }

    registerSuccessfulLogin(username, password) {
        sessionStorage.setItem(USER_NAME_SESSION_ATTRIBUTE_NAME,  this.createBasicAuthToken(username, password));
    }

    logout() {
        sessionStorage.removeItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    }

    isUserLoggedIn() {
        let user = sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
        return user !== null;
    }
}
export default new AuthenticationService()