/* eslint-disable react-hooks/exhaustive-deps */
import * as React from 'react';
import LeagueTable from '../Tables/LeagueTable';
import PredictionsTable from '../Tables/PredictionsTable';
import MatchesTable from '../Tables/MatchesTable';
import { Button, Grid } from '@mui/material';
import TeamService from '../Services/TeamService';
import { useState } from 'react';
import { useEffect } from 'react';
import DeleteIcon from '@mui/icons-material/Delete';
import PlayCircleIcon from '@mui/icons-material/PlayCircle';
export default function Home() {
    const [teams, setTeams] = useState([]);
    const [numOfWeek, setNumOfWeek] = useState(0);
    const [matchesOfWeek, setMatchesOfWeek] = useState([])
    const [predictionsData, setPredictionsData] = useState([]);

    async function getTeams() {
        TeamService.getTeams().then(res => {
            setTeams(res.data)
        }).catch(err => {
            console.error(err);
        })
    }

    async function nextWeek() {
        TeamService.playNextWeekRandomly(numOfWeek + 1).then(res => {
            setMatchesOfWeek(res.data)
            setNumOfWeek(numOfWeek + 1);
        }).catch(err => {
            console.error(err);
        })
    }

    async function playAll() {
        for (var i = 0; i < 6; i++) {
            TeamService.playNextWeekRandomly(numOfWeek + 1).then(res => {
                setMatchesOfWeek(res.data)
                setNumOfWeek(numOfWeek + 1);
            }).catch(err => {
                console.error(err);
            })
        }
    }

    function deleteDb() {
        TeamService.deleteDb().then(res => {
            console.log("database deleted successfully");
        }).catch(err => {
            console.log(err);
        })
    }

    function getSimulate() {
        if (numOfWeek >= 3) {
            TeamService.getSimulate(numOfWeek).then(res => {
                setPredictionsData(res.data);
                const predictions = [];
                Object.keys(res.data).map(key => {
                    predictions.push({ "teamName": key, "percentage": res.data[key] })
                })
                setPredictionsData(predictions);
            }).catch(err => {
                console.log(err);
            })
        }
    }

    useEffect(() => {
        getTeams();
        if (numOfWeek >= 3) getSimulate();
    }, [numOfWeek])


    return (
        <Grid container style={{ padding: 10 }}>
            <div>{numOfWeek}</div>
            <Grid container style={{ display: "flex", flexDirection: "row" }}>
                <Grid item>
                    <LeagueTable rows={teams} numOfWeek={numOfWeek} />
                </Grid>
                <Grid item maxWidth={500}>
                    <MatchesTable matches={matchesOfWeek} numOfWeek={numOfWeek} />
                </Grid>
                <Grid item>
                    <PredictionsTable predictions={predictionsData} numOfWeek={numOfWeek} />
                </Grid>
            </Grid>
            {
                numOfWeek === 6 ? (
                    <div>
                        <Button
                            onClick={() => deleteDb()}
                            variant="contained"
                            color="error"
                            style={{ margin: 10 }}
                            href="/"
                            startIcon={<DeleteIcon />}
                        >
                            Delete Database
                        </Button>
                    </div>
                )
                    :
                    (
                        <div>
                            <Button
                                variant="contained"
                                style={{ margin: 10 }}
                                onClick={() => playAll()}
                                >
                                Play All
                            </Button>
                            <Button
                                variant="contained"
                                style={{ margin: 10 }}
                                onClick={() => nextWeek()}
                            >
                                Next Week
                            </Button>
                            <Button
                                onClick={() => getSimulate()}
                                variant="contained"
                                style={{ margin: 10 }}
                                endIcon={<PlayCircleIcon />}
                            >
                                Run Simulation Again
                            </Button>
                            <Button
                                onClick={() => deleteDb()}
                                variant="contained"
                                color="error"
                                style={{ margin: 10 }}
                                href="/"
                                startIcon={<DeleteIcon />}
                            >
                                Delete Database
                            </Button>
                        </div>
                    )
            }
        </Grid >
    )
}
