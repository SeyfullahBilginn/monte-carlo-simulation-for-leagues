import * as React from 'react';
import LeagueTable from '../Tables/LeagueTable';
import PredictionsTable from '../Tables/PredictionsTable';
import MatchesTable from '../Tables/MatchesTable';
import { Button } from '@mui/material';
import TeamService from '../Services/TeamService';
import { useState } from 'react';
import { useEffect } from 'react';

export default function Home() {
    const [teams, setTeams] = useState([]);
    const [numOfWeek, setNumOfWeek] = useState(0);
    async function getTeams() {
        TeamService.getTeams().then(res => {
            setTeams(res.data)
        }).catch(err => {
            console.error(err);
        })
    }

    async function nextWeek() {
        TeamService.playNextWeekRandomly().then(res => {
            console.log(res);
            setNumOfWeek(numOfWeek++);
        }).catch(err => {
            console.log(err);
        })
    }

    useEffect(() => {
        getTeams();
    }, [numOfWeek])


    return (
        <div style={{ padding: 10 }}>
            <div style={{ display: "flex", flexDirection: "row" }}>

                <LeagueTable rows={teams} />

                <MatchesTable />

                <PredictionsTable />
            </div>
            <div>
                <Button variant="contained" style={{ margin: 10 }}>Play All</Button>
                <Button variant="contained" style={{ margin: 10 }}>Next Week</Button>
                <Button
                    onClick={() => getTeams()}
                    variant="contained"
                    style={{ margin: 10 }}
                >
                    Get Teams
                </Button>
            </div>
        </div>
    )
}
