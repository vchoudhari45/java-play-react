import React, { Component } from 'react';
import HttpClient from 'axios';
import DriverDetails from '../driver/DriverDetails'
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Alert from '@material-ui/lab/Alert';
import './Booking.css';

const PLAY_BOOKING_URL = 'http://localhost:9000/booking';

const validateForm = (errors) => {
  let valid = true;
  Object.values(errors).forEach(
    (val) => val.length > 0 && (valid = false)
  );
  return valid;
}

class Booking extends Component {

  constructor(props) {
      super(props);
      this.state = {
        fullName: null,
        pickupLocation: null,
        errors: {
          fullName: '',
          pickupLocation: '',
        },
        drivers: []
      };
  }

  handleChange = (event) => {
    event.preventDefault();
    const { name, value } = event.target;
    let errors = this.state.errors;

    switch (name) {
      case 'fullName':
        errors.fullName =
          value.length < 5
            ? 'Name must be 5 characters long'
            : '';
        break;
      case 'pickupLocation':
        errors.pickupLocation =
          value.length < 5
            ? 'Pickup location must be 5 characters long'
            : '';
        break;
      default:
        break;
    }
    this.setState({errors, [name]: value});
  }

  handleSubmit = (event) => {
    event.preventDefault();
    if(this.state.fullName && this.state.pickupLocation && validateForm(this.state.errors)) {
        let URL = PLAY_BOOKING_URL + "?name=" + this.state.fullName + "&pickupLocation=" + this.state.pickupLocation
        HttpClient.get(URL)
        .then(response => {
            if(response.data && response.data.length > 0) {
                this.setState({...this.state, drivers: response.data})
                console.log(this.state)
            }
        })
        .catch(error => {
            if (error.response) {
                this.setState({...this.state, message: "Request failed with status "+error.response.status})
            } else if (error.request) {
                this.setState({...this.state, message: "Request failed with status "+error.request})
            } else {
                this.setState({...this.state, message: "Request failed "+error.message})
            }
        })
    }
    else{
        let errors = this.state.errors;
        if(this.state.pickupLocation == null) errors.pickupLocation = 'Pickup location is required';
        if(this.state.fullName == null) errors.fullName = 'Name is required';
        this.setState({...this.state, errors});
    }
  }

  render() {
    const {errors} = this.state;

    if(this.state.drivers && this.state.drivers.length > 0) {
        return (
            <div className='wrapper'>
                <DriverDetails drivers={this.state.drivers} />
            </div>
        );
    }
    else {
        return (
          <div className='wrapper'>
            <h1>&nbsp;Booking App</h1>
            { this.state.message && this.state.message.trim().length > 0 ? <Alert severity="info">{this.state.message}</Alert> : "" }
            <div className='form-wrapper'>
              <form onSubmit={this.handleSubmit} noValidate>
                <div className='fullName'>
                  <TextField fullWidth
                             helperText=<span className='error'>{errors.fullName}</span>
                             style={{ margin: 9 }}
                             margin="normal"
                             name="fullName"
                             placeholder="Your Name"
                             onChange={this.handleChange} noValidate />
                </div>
                 <div className='pickupLocation'>
                  <TextField fullWidth
                             helperText=<span className='error'>{errors.pickupLocation}</span>
                             style={{ margin: 9 }}
                             margin="normal"
                             name="pickupLocation"
                             placeholder="Pickup Location"
                             onChange={this.handleChange} noValidate />
                </div>
                &nbsp;&nbsp;<Button variant="contained" color="primary" onClick={this.handleSubmit}>Request</Button>
              </form>
            </div>
          </div>
        );
    }
  }
}


export default Booking;

