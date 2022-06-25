import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import LeagueTable from './Tables/LeagueTable';
import PredictionsTable from './Tables/PredictionsTable';
import MatchesTable from './Tables/MatchesTable';
import { Button } from '@mui/material';
import MatchesService from './Services/MatchesService';

export default function Home() {
    function getTeams() {
        MatchesService.getTeams().then(res => {
            console.log(res);
        }).catch(err => {
            console.log(err);
        })
    }
    return (
        <div style={{ padding: 10 }}>
            <div style={{ display: "flex", flexDirection: "row" }}>

                <LeagueTable />

                <MatchesTable />

                <PredictionsTable />
            </div>
            <div>
                <Button variant="contained" style={{ margin: 10 }}>Play All</Button>
                <Button variant="contained" style={{ margin: 10 }}>Next Week</Button>
                <Button
                    variant="contained"
                    style={{ margin: 10 }}
                >
                    Get Teams
                </Button>
            </div>
        </div>
    )
}
