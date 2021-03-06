import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export default function LeagueTable({rows, numOfWeek}) {
    return (
        <TableContainer component={Paper}
            style={{
                backgroundColor: "RGB(220,220,220)", margin: 10, marginRight:10, padding: 10
            }}>
            <div>League Table at {numOfWeek}th week</div>
            <Table sx={{ minWidth: 250 }} size="small" aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Teams</TableCell>
                        <TableCell align="right">PTS</TableCell>
                        <TableCell align="right">P</TableCell>
                        <TableCell align="right">W</TableCell>
                        <TableCell align="right">D</TableCell>
                        <TableCell align="right">L</TableCell>
                        <TableCell align="right">GD</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rows.map((row) => (
                        <TableRow
                            key={row.teamName}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" size='small' scope="row">
                                {row.teamName}
                            </TableCell>
                            <TableCell align="right">{row.points}</TableCell>
                            <TableCell align="right">{row.numOfPlayed}</TableCell>
                            <TableCell align="right">{row.numOfWon}</TableCell>
                            <TableCell align="right">{row.numOfDrawn}</TableCell>
                            <TableCell align="right">{row.numOfLost}</TableCell>
                            <TableCell align="right">{row.average}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
