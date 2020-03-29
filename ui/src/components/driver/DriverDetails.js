import React from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

class DriverDetails extends React.Component {

  render() {
    return (
      <div className='wrapper'>
          <h1>Booking App</h1>
          Your Request is confirmed your driver {this.props.drivers[0].driverName} is on his way
          <br />&nbsp;
          <TableContainer component={Paper}>
              <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Driver&rsquo;s Name</TableCell>
                      <TableCell align="right">Latitude</TableCell>
                      <TableCell align="right">Longitude</TableCell>
                      <TableCell>Status</TableCell>
                    </TableRow>
                  </TableHead>

                  <TableBody>
                    {
                      this.props.drivers.map(row => (
                          <TableRow key={row.id}>
                            <TableCell component="th" scope="row">
                              {row.driverName}
                            </TableCell>
                            <TableCell align="right">{row.currentLatitude}</TableCell>
                            <TableCell align="right">{row.currentLongitude}</TableCell>
                            <TableCell>{row.status}</TableCell>
                          </TableRow>
                        )
                      )
                    }
                  </TableBody>
              </Table>
          </TableContainer>
       </div>
    );
  }
}

export default DriverDetails;

