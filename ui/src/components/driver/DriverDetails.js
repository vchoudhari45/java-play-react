import React from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import NumericLabel from 'react-pretty-numbers';

let params = {
  justification: 'R',
  locales : 'en-AU',
  percentage: false,
  precision: 2,
  commafy: true,
  shortFormat: true
};

class DriverDetails extends React.Component {

  render() {
    return (
      <div className='wrapper'>
          <h1>Booking App</h1>
          {this.props.customerName}, your request is confirmed your driver <b>{this.props.drivers[0].driver.driverName}</b> is on his way
          <br />&nbsp;
          <TableContainer component={Paper}>
              <Table>
                  <TableHead>
                    <TableRow>
                      <TableCell>Driver&rsquo;s Name</TableCell>
                      <TableCell align="right">Latitude</TableCell>
                      <TableCell align="right">Longitude</TableCell>
                      <TableCell align="right">Distance(km)</TableCell>
                      <TableCell>Status</TableCell>
                    </TableRow>
                  </TableHead>

                  <TableBody>
                    {
                      this.props.drivers.map(row => (
                          <TableRow key={row.driverId}>
                            <TableCell component="th" scope="row">
                              {row.driver.driverName}
                            </TableCell>
                            <TableCell align="right">{row.driver.latitude}</TableCell>
                            <TableCell align="right">{row.driver.longitude}</TableCell>
                            <TableCell align="right"><NumericLabel params={params}>{row.distance}</NumericLabel></TableCell>
                            <TableCell>{row.driver.status}</TableCell>
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

