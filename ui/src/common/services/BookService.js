import axios from 'axios'
import {API_URL} from '../Constants'

class BookService {

    retrieveAllBooks() {
        console.log(API_URL+'/api/v1/books');
        return axios.get(API_URL+'/api/v1/books');
    }

}

export default new BookService();