import axios from 'axios'
import {API_URL} from '../Constants'

class AdminService {

    //Functions for author
    getAllAuthors() {
        return axios.get(API_URL + '/api/v1/authors');
    }

    addAuthor(authorData) {
        return axios.post(API_URL + '/api/v1/authors', authorData);
    }

    deleteAuthor(authorData) {
        return axios.post(API_URL + '/api/v1/authors/delete', authorData);
    }

    //Functions for book



    //Functions for reservations
    getReservationQueue() {
        return axios.get(API_URL + '/api/v1/reservations/admin/queue');
    }

    handOut(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/admin/hand-out', reservationData);
    }

    searchReservations(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/admin/search', reservationData);
    }
}

export default new AdminService();