import axios from 'axios'

const API_URL = 'http://localhost:8080/api/v1';

class BookService {

    retrieveAllBooks() {
        return axios.get(`${API_URL}/books`);
    }

}

export default new BookService();