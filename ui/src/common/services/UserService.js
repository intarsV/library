import axios from 'axios'
import {API_URL} from '../Constants'
import {USER_NAME_SESSION_ATTRIBUTE_NAME} from "../Constants";

class UserService {
    getToken() {
        return sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    }

    searchBook(searchData) {
        return axios.post(API_URL + '/api/v1/books/search',
            searchData, {headers: {authorization: this.getToken()}});
    }

    getReservations(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/user/queue',
            reservationData, {headers: {authorization: this.getToken()}});
    }

    makeReservation(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/user/add',
            reservationData, {headers: {authorization: this.getToken()}});
    }

    removeReservation(reservationData){
        return axios.post(API_URL + '/api/v1/reservations/user/delete',
            reservationData, {headers: {authorization: this.getToken()}});
    }

    register(userData) {
        return axios.post(API_URL + '/api/v1/users/add', userData, {headers: {authorization: this.getToken()}});
    }

}

export default new UserService();