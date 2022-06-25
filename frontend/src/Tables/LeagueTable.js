import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export default function LeagueTable() {

    function createData(name, pts, p, w, d, l, gd) {
        return { name, pts, p, w, d, l, gd };
    }
    const rows = [
        createData('Arsenal', 1, 2, 3, 4, 5, 6),
        createData('Liverpool', 1, 2, 3, 4, 5, 6),
        createData('Chelsea', 1, 2, 3, 4, 5, 6),
        createData('Manchester City', 1, 2, 3, 4, 5, 6),
    ];
    return (
        <TableContainer component={Paper}
            style={{
                backgroundColor: "RGB(220,220,220)", margin: 0, padding: 0
            }}>
            <div>League Table</div>
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
                            key={row.name}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" size='small' scope="row">
                                {row.name}
                            </TableCell>
                            <TableCell align="right">{row.pts}</TableCell>
                            <TableCell align="right">{row.p}</TableCell>
                            <TableCell align="right">{row.w}</TableCell>
                            <TableCell align="right">{row.d}</TableCell>
                            <TableCell align="right">{row.l}</TableCell>
                            <TableCell align="right">{row.gd}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
