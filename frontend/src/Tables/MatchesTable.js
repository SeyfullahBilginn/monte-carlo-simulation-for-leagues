import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { TextField } from '@mui/material';

export default function MatchesTable({matches, numOfWeek}) {
    if(numOfWeek === 0) {
        return <div></div>
    }
    return (
        <TableContainer component={Paper} style={{margin:10, padding:10}}>
            <div>{numOfWeek}th Week Match Results</div>
            <Table sx={{ minWidth: 50 }} size="small" aria-label="simple table">
                <TableBody>
                    {matches.map((match) => (
                        <TableRow
                            key={match.homeTeamName}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell >{match.homeTeamName}</TableCell>
                            <TableCell >
                                <TextField
                                    label=""
                                    value={match.homeTeamGoal}
                                    name="numberformat"
                                    id="formatted-numberformat-input"
                                    variant="standard"
                                    size='small'
                                    style={{maxWidth:40}}
                                />
                            </TableCell>
                            <TableCell >
                                <TextField
                                    label=""
                                    value={match.awayTeamGoal}
                                    name="numberformat"
                                    id="formatted-numberformat-input"
                                    variant="standard"
                                    size='small'
                                    style={{maxWidth:40}}
                                />
                            </TableCell>
                            <TableCell >{match.awayTeamName}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
