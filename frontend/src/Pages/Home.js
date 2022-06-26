import * as React from 'react';
import LeagueTable from '../Tables/LeagueTable';
import PredictionsTable from '../Tables/PredictionsTable';
import MatchesTable from '../Tables/MatchesTable';
import { Button, Grid } from '@mui/material';
import TeamService from '../Services/TeamService';
import { useState } from 'react';
import { useEffect } from 'react';

export default function Home() {
    const [teams, setTeams] = useState([]);
    const [numOfWeek, setNumOfWeek] = useState(0);
    const [matchesOfWeek, setMatchesOfWeek] = useState([])
    const [predictionsData, setPredictionsData] = useState([]);

    async function getTeams() {
        TeamService.getTeams().then(res => {
            console.log("res");
            setTeams(res.data)
        }).catch(err => {
            console.error(err);
        })
    }

    async function nextWeek() {
        console.log(numOfWeek);
        TeamService.playNextWeekRandomly(numOfWeek + 1).then(res => {
            console.log(res);
            setMatchesOfWeek(res.data)
            setNumOfWeek(numOfWeek + 1);
        }).catch(err => {
            console.error(err);
        })
    }

    function deleteDb() {
        TeamService.deleteDb().then(res => {
            console.log("database deleted successfully");
        }).catch(err => {
            console.log(err);
        })
    }

    function getSimulate() {
        if(numOfWeek>=3) {
            console.log("SIM");
            TeamService.getSimulate(numOfWeek).then(res => {
                console.log(res.data);
                setPredictionsData(res.data);
                const predictions = [];
                Object.keys(res.data).map(key => {
                    console.log("-----");
                    console.log(key);
                    predictions.push({ "teamName": key, "percentage": res.data[key] })
                    // setPredictionsData(...predictions, { "teamName": key, "percentage": res.data[key] })
                })
                setPredictionsData(predictions);
                console.log(predictions);
            }).catch(err => {
                console.log(err);
            })
        }
    }

    useEffect(() => {
        getTeams();
    }, [numOfWeek])


    return (
        <Grid container style={{ padding: 10 }}>
            <div>{numOfWeek}</div>
            <Grid container style={{ display: "flex", flexDirection: "row" }}>
                <Grid item>
                    <LeagueTable rows={teams} />
                </Grid>
                <Grid item maxWidth={500}>
                    <MatchesTable matches={matchesOfWeek} />
                </Grid>
                <Grid item>
                    <PredictionsTable predictions={predictionsData} />
                </Grid>
            </Grid>
            <div>
                <Button variant="contained" style={{ margin: 10 }}>Play All</Button>
                <Button
                    variant="contained"
                    style={{ margin: 10 }}
                    onClick={() => nextWeek()}
                >
                    Next Week
                </Button>
                <Button
                    onClick={() => getTeams()}
                    variant="contained"
                    style={{ margin: 10 }}
                >
                    Get Teams
                </Button>
                <Button
                    onClick={() => getSimulate()}
                    variant="contained"
                    style={{ margin: 10 }}
                >
                    Get Simulate
                </Button>
                <Button
                    onClick={() => deleteDb()}
                    variant="contained"
                    style={{ margin: 10 }}
                    href="/"
                >
                    Delete Database
                </Button>
            </div>
        </Grid>
    )
}
