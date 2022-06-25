import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';


function createData(name, calories, fat, carbs, protein) {
    return { name, calories, fat, carbs, protein };
}

const rowsProbability = [
    createData('Arsenal', "%45"),
    createData('Liverpool', "%25"),
    createData('Chelsea', "15%"),
    createData('Manchester City', "15%"),
];

export default function PredictionsTable() {
    return (
        <TableContainer component={Paper}
            style={{
                backgroundColor: "RGB(220,220,220)", margin: 0, padding: 0
            }}
        >
            <div>Predictions</div>
            <Table sx={{ minWidth: 50 }} size="small" aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell> Teams</TableCell>
                        <TableCell align="right">Probabilities</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {rowsProbability.map((row) => (
                        <TableRow
                            key={row.name}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {row.name}
                            </TableCell>
                            <TableCell align="right">{row.calories}</TableCell>
                            <TableCell align="right">{row.fat}</TableCell>
                            <TableCell align="right">{row.carbs}</TableCell>
                            <TableCell align="right">{row.protein}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}
