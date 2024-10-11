import axios from "axios";

const API_URL = "http://localhost:5000/api/test/";

class UserService {
    getUserBoard() {
        return axios.get(API_URL + "user", {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('token'))
            }
        });
    }

    getAdminBoard() {
        return axios.get(API_URL + "admin", {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem('token'))
            }
        });
    }
}

export default new UserService();
