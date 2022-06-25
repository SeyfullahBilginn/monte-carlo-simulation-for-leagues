import axios from "axios";

const API_BASE_URL = "http://localhost:3000/";

class MatchesServices {
    getTeams() {
        return axios.get(API_BASE_URL + "teams")
    }
}

export default new MatchesServices();
