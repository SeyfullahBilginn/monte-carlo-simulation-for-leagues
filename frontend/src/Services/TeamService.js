import axios from "axios";

const API_BASE_URL = "http://localhost:8084/api/V1";

class TeamService {
    addTeam(addedTeam) {
        return axios.post(API_BASE_URL + "/teams", addedTeam)
    }
    
    getTeams() {
        return axios.get(API_BASE_URL + "/teams")
    }

    playNextWeekRandomly(numOfWeek) {
        return axios.post(API_BASE_URL + "/playNextWeekRandomly/" + numOfWeek);
    }

    playAllWeeksRandomly(numOfWeek) {
        return axios.post(API_BASE_URL + "/playAllWeeksRandomly/" + numOfWeek);
    }

    getSimulate(numOfWeek) {
        return axios.request(API_BASE_URL + "/teams/simulate/" + numOfWeek)
    }

    deleteDb() {
        return axios.delete(API_BASE_URL + "/teams");
    }
}

export default new TeamService();
