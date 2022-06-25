import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { TextField } from '@mui/material';

export default function MatchesTable({matches}) {
    return (
        <TableContainer component={Paper}>
            <div>nth Week Matches</div>
            <Table sx={{ minWidth: 50 }} size="small" aria-label="simple table">
                <TableBody>
                    {matches.map((match) => (
                        <TableRow
                            key={match.homeTeam}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell >{match.homeTeamName}</TableCell>
                            <TableCell >
                                <TextField
                                    label=""
                                    value={match.homeTeamGoal}
                                    // onChange={handleChange}
                                    name="numberformat"
                                    id="formatted-numberformat-input"
                                    // InputProps={{
                                    //     inputComponent: NumberFormatCustom,
                                    // }}
                                    variant="standard"
                                />
                            </TableCell>
                            <TableCell >
                                <TextField
                                    label=""
                                    value={match.awayTeamGoal}
                                    // onChange={handleChange}
                                    name="numberformat"
                                    id="formatted-numberformat-input"
                                    // InputProps={{
                                    //     inputComponent: NumberFormatCustom,
                                    // }}
                                    variant="standard"
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
