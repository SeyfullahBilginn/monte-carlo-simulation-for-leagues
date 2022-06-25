import axios from "axios";

const API_BASE_URL = "http://localhost:8084/api/V1";

class TeamService {
    addTeam(addedTeam) {
        console.log(API_BASE_URL);
        console.log(addedTeam);
        return axios.post(API_BASE_URL + "/teams", addedTeam)
    }
    
    getTeams() {
        return axios.get(API_BASE_URL + "/teams")
    }

    playNextWeekRandomly(numOfWeek) {
        return axios.post(API_BASE_URL + "/playNextWeekRandomly/" + numOfWeek);

    }

}

export default new TeamService();
