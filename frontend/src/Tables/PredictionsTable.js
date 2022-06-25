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

export default function PredictionsTable({ predictions }) {
    React.useEffect(() => {
        console.log("table");
        console.log(predictions);
    }, [])

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
                    {predictions.length>0 ? predictions.map((item) => (
                        <TableRow
                            key={item.teamName}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                                <TableCell component="th" scope="row">
                                    {item.teamName}
                                </TableCell>
                                <TableCell align="right">%{item.percentage}</TableCell>
                        </TableRow>
                    ))
                    :
                    (
                        <div style={{color:"red"}}>Predictions can be seen after 3 weeks</div>
                    )
                
                }
                </TableBody>
            </Table>
        </TableContainer>
    )
}
