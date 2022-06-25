import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { TextField } from '@mui/material';


function createData(homeTeam, fat, carbs, awayTeam) {
    return { homeTeam, fat, carbs, awayTeam };
}

const rowsProbability = [
    createData('Arsenal', "2", "3", "Chelsea"),
    createData('Manchester City', "4", "1", "Liverpool"),
];

export default function MatchesTable() {
    return (
        <TableContainer component={Paper}>
            <div>nth Week Matches</div>
            <Table sx={{ minWidth: 50 }} size="small" aria-label="simple table">
                <TableBody>
                    {rowsProbability.map((row) => (
                        <TableRow
                            key={row.homeTeam}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell >{row.homeTeam}</TableCell>
                            <TableCell >
                                <TextField
                                    label=""
                                    value={12}
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
                                    value={12}
                                    // onChange={handleChange}
                                    name="numberformat"
                                    id="formatted-numberformat-input"
                                    // InputProps={{
                                    //     inputComponent: NumberFormatCustom,
                                    // }}
                                    variant="standard"
                                />
                            </TableCell>
                            <TableCell >{row.awayTeam}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
