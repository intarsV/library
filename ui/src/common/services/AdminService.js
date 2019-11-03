import axios from 'axios'
import {API_URL, USER_NAME_SESSION_ATTRIBUTE_NAME} from '../Constants'

class AdminService {

    config() {
        return {headers: {authorization: sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)}}
    };

    //Functions for author
    getAllAuthors() {
        return axios.get(API_URL + '/api/v1/authors', this.config());
    }

    addAuthor(authorData) {
        return axios.post(API_URL + '/api/v1/authors/add', authorData, this.config());
    }

    deleteAuthor(authorData) {
        return axios.post(API_URL + '/api/v1/authors/delete', authorData, this.config());
    }

    //Functions for book
    getAllBooks() {
        return axios.get(API_URL + '/api/v1/books', this.config());
    }

    addABook(bookData) {
        return axios.post(API_URL + '/api/v1/books/add', bookData, this.config());
    }

    deleteBook(bookData) {
        return axios.post(API_URL + '/api/v1/books/delete', bookData, this.config());
    }

    //Functions for reservations
    getReservationQueue() {
        return axios.get(API_URL + '/api/v1/reservations/admin/queue', this.config());
    }

    handOut(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/admin/handout', reservationData, this.config());
    }

    searchReservations(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/admin/search', reservationData, this.config());
    }

    takeIn(reservationData){
        return axios.post(API_URL + '/api/v1/reservations/admin/take', reservationData, this.config());
    }

    /* functions for users*/
    getUsers() {
        return axios.get(API_URL + '/api/v1/users', this.config());
    }

    enableUser(userData) {
        return axios.post(API_URL + '/api/v1/users/admin/enable', userData, this.config());
    }

    disableUser(userData) {
        return axios.post(API_URL + '/api/v1/users/admin/disable', userData, this.config());
    }
}

export default new AdminService();