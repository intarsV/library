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
        return axios.post(API_URL + '/api/v1/authors', authorData, this.config());
    }

    disableAuthor(authorData) {
        return axios.put(API_URL + '/api/v1/authors/' + authorData.id, authorData, this.config());
    }

    //Functions for book
    getAllBooks() {
        return axios.get(API_URL + '/api/v1/books', this.config());
    }

    addABook(bookData) {
        return axios.post(API_URL + '/api/v1/books', bookData, this.config());
    }

    disableBook(bookData) {
        return axios.put(API_URL + '/api/v1/books/' + bookData.id, bookData, this.config());
    }

    //Functions for reservations
    process(reservationData) {
        return axios.put(API_URL + '/api/v1/reservations/' + reservationData.id, reservationData, this.config());
    }

    searchReservations(reservationData) {
        return axios.get(API_URL + '/api/v1/reservations' ,  {
            params: reservationData,
            headers: {authorization: sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME)}
        });
    }

    /*Functions for users*/
    getUsers() {
        return axios.get(API_URL + '/api/v1/users', this.config());
    }

    changeUserStatus(userData) {
        return axios.put(API_URL + '/api/v1/users/'+userData.id, userData, this.config());
    }
}

export default new AdminService();