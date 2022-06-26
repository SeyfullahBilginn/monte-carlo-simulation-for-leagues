import { Grid, TextField, Button } from '@mui/material'
import React, { useEffect, useRef, useState } from 'react'
import TeamService from '../Services/TeamService';
import LeagueTable from '../Tables/LeagueTable';

export default function Landing() {
    const [teams, setTeams] = useState([]);

    const addedTeamRef = useRef();

    function addTeam() {
        console.log("addTeam");
        console.log("ref: " + addedTeamRef.current);
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
        <Grid container justifyContent="center" alignItems="center" >
            <Grid item padding={20}>
                <TextField
                    id="myTextField"
                    label="Text Field"
                    variant="outlined"
                    inputRef={addedTeamRef}
                    style={{ marginRight: 20 }}
                />
                {
                    (teams.length < 4) ? (
                        <Button variant="contained" onClick={() => { addTeam() }}>
                            addTeams
                        </Button>
                    ) :
                        (
                            <Button href='/home' variant="contained">
                                go to simulation
                            </Button>
                        )
                }
                <LeagueTable rows={teams} />
            </Grid>
        </Grid >
    )
}
