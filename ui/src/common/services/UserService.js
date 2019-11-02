import axios from 'axios'
import {API_URL} from '../Constants'
import {USER_NAME_SESSION_ATTRIBUTE_NAME} from "../Constants";

class UserService {

    config = {headers: {authorization: sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)}};

    searchBook(searchData) {
        return axios.post(API_URL + '/api/v1/books/search',
            searchData, this.config);
    }

    getReservations(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/user/queue',
            reservationData, this.config);
    }

    makeReservation(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/user/add',
            reservationData, this.config);
    }

    removeReservation(reservationData){
        return axios.post(API_URL + '/api/v1/reservations/user/delete',
            reservationData, this.config);
    }

    register(userData) {
        return axios.post(API_URL + '/api/v1/users/add', userData, this.config);
    }

}

export default new UserService();