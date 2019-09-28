import axios from 'axios'
import {API_URL} from '../Constants'

class UserService {

    static searchBook(searchData) {
        //console.log("Fire this"+API_URL + '/api/v1/books/search');
        return axios.post(API_URL + '/api/v1/books/search', searchData);
    }

    static getReservations(reservationData){
        //console.log(API_URL + '/api/v1/reservations/user/list-reservation');
        return axios.post(API_URL + '/api/v1/reservations/user/list-reservation', reservationData);
    }

    static makeReservation(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/user/make-reservation', reservationData);
    }
}

export default new UserService();