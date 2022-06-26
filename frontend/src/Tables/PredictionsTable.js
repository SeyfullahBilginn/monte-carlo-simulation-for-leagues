import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';


export default function PredictionsTable({ predictions, numOfWeek }) {
    const [table, setTable] = React.useState();

    async function setData(data){
        setTable(data)
    }

    React.useEffect(() => {
        predictions.sort((a,b) => b.percentage - a.percentage); // b - a for reverse sort
        setData(predictions)
    }, [predictions])

    if(!table) return (
        <div>Loading</div>
    );
    
    return (
        <TableContainer component={Paper}
            style={{
                backgroundColor: "RGB(220,220,220)", margin: 10, marginLeft:20, padding: 10
            }}
        >
            <div>{numOfWeek}th Week Predictions of Championship</div>
            <Table sx={{ minWidth: 50 }} size="small" aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell> Teams</TableCell>
                        <TableCell align="right">Probabilities</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {table.length>0 ? table.map((item) => (
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
                        <div style={{color:"red"}}>Predictions will be seen after 3 weeks</div>
                    )
                }
                </TableBody>
            </Table>
        </TableContainer>
    )
}
