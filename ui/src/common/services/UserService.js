import axios from 'axios'
import {API_URL} from '../Constants'

class UserService {

    searchBook(searchData) {
        //console.log("Fire this"+API_URL + '/api/v1/books/search');
        return axios.post(API_URL + '/api/v1/books/search', searchData);
    }

    getReservations(reservationData) {
        //console.log(API_URL + '/api/v1/reservations/user/list-reservation');
        return axios.post(API_URL + '/api/v1/reservations/user/list-reservation', reservationData);
    }

    makeReservation(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/user/make-reservation', reservationData);
    }
}

export default new UserService();