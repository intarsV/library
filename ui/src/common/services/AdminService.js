import axios from 'axios'
import {API_URL, USER_NAME_SESSION_ATTRIBUTE_NAME} from '../Constants'

class AdminService {

    getToken() {
        return sessionStorage.getItem(USER_NAME_SESSION_ATTRIBUTE_NAME);
    }

    //Functions for author
    getAllAuthors() {
        return axios.get(API_URL + '/api/v1/authors', {headers: {authorization: this.getToken()}});
    }

    addAuthor(authorData) {
        return axios.post(API_URL + '/api/v1/authors/add', authorData,{headers: {authorization: this.getToken()}});
    }

    deleteAuthor(authorData) {
        return axios.post(API_URL + '/api/v1/authors/delete', authorData, {headers: {authorization: this.getToken()}});
    }

    //Functions for book
    getAllBooks() {
        return axios.get(API_URL + '/api/v1/books', {headers: {authorization: this.getToken()}});
    }

    addABook(bookData) {
        return axios.post(API_URL + '/api/v1/books/add', bookData, {headers: {authorization: this.getToken()}});
    }

    deleteBook(bookData) {
        return axios.post(API_URL + '/api/v1/books/delete', bookData, {headers: {authorization: this.getToken()}});
    }

    //Functions for reservations
    getReservationQueue() {
        return axios.get(API_URL + '/api/v1/reservations/admin/queue', {headers: {authorization: this.getToken()}});
    }

    handOut(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/admin/handout', reservationData, {headers: {authorization: this.getToken()}});
    }

    searchReservations(reservationData) {
        return axios.post(API_URL + '/api/v1/reservations/admin/search', reservationData, {headers: {authorization: this.getToken()}});
    }

    takeIn(reservationData){
        return axios.post(API_URL + '/api/v1/reservations/admin/take', reservationData, {headers: {authorization: this.getToken()}});
    }

    /* functions for users*/
    getUsers() {
        return axios.get(API_URL + '/api/v1/users', {headers: {authorization: this.getToken()}});
    }

    enableUser(userData) {
        return axios.post(API_URL + '/api/v1/users/admin/enable', userData, {headers: {authorization: this.getToken()}});
    }

    disableUser(userData) {
        return axios.post(API_URL + '/api/v1/users/admin/disable', userData, {headers: {authorization: this.getToken()}});
    }
}

export default new AdminService();