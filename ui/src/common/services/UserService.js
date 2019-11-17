import axios from 'axios'
import {API_URL, USER_NAME_SESSION_ATTRIBUTE_NAME} from '../Constants'

class UserService {

    config (){
        return {headers: {authorization: sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)}}
    };

    searchBook(searchData) {
        return axios.get(API_URL + '/api/v1/books', {
            params: searchData,
            headers: {authorization: sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)}
        });
    }

    searchReservations(reservationData) {
        return axios.get(API_URL + '/api/v1/reservations', {
            params: reservationData,
            headers: {authorization: sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)}
        });
    }

    processReservation(reservationData) {
        return axios.put(API_URL + '/api/v1/reservations/'+reservationData.id, reservationData, this.config());
    }

    makeReservation(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations', reservationData, this.config());
    }

    register(userData) {
        return axios.post(API_URL + '/api/v1/users', userData);
    }
}

export default new UserService();