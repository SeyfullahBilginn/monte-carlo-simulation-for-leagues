import { Grid, TextField, Button } from '@mui/material'
import React, { useEffect, useRef, useState } from 'react'
import TeamService from '../Services/TeamService';
import LeagueTable from '../Tables/LeagueTable';
import ArrowRightIcon from '@mui/icons-material/ArrowRight';

export default function Landing() {
    const [teams, setTeams] = useState([]);

    const addedTeamRef = useRef();

    function addTeam() {
        const addedTeam = {
            teamName: addedTeamRef.current.value,
            points: 0,
            numOfWon: 0,
            numOfDrawn: 0,
            numOfLost: 0,
            numOfPlayed: 0,
            numOfGoalsFor: 0,
            numOfGoalsAgainst: 0,
            average: 0,
        }
        setTeams(oldArray => [...oldArray, addedTeam]);
        addTeamToDb(addedTeam);
        addedTeamRef.current.value = "";
    }

    function addTeamToDb(addedTeam) {
        TeamService.addTeam(addedTeam);
    }

    useEffect(() => {
        TeamService.deleteDb().then(res => {
            console.log("deletedDb");
        }).catch(err => {
            console.log(err);
        })
    }, [])


    return (
        <Grid display="flex" container justifyContent="center" alignItems="center" >
            <Grid padding={20}>
                {
                    (teams.length < 4) ? (
                        <Grid item>
                            <TextField
                                id="myTextField"
                                label="Enter team name"
                                variant="outlined"
                                inputRef={addedTeamRef}
                            />
                            <Button
                                variant="contained"
                                onClick={() => { addTeam() }}
                            >
                                addTeams
                            </Button>
                        </Grid>
                    ) :
                        (
                            <Button
                                href='/home'
                                variant="contained"
                                color='secondary'
                                endIcon={<ArrowRightIcon />}
                            >
                                go to simulation
                            </Button>
                        )
                }
                <LeagueTable rows={teams} numOfWeek={0} />
            </Grid>
        </Grid >
    )
}
