import React, { Component } from 'react';
import UserDataService from '../services/user.service';

export default class AddUser extends Component {
  constructor(props) {
    super(props);

    this.state = {
      id: null,
      firstName: '',
      lastName: '',
      email: '',
      submitted: false,
      error: false,
    };
  }

  
  onChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value,
    });
  }

  saveUser = () => {
    const data = {
      firstName: this.state.firstName,
      lastName: this.state.lastName,
      email: this.state.email,
    };

    UserDataService.create(data)
      .then(response => {
        this.setState({
          ...response.data,
          submitted: true
        });
      })
      .catch(e => {
        console.log(e);
      });
  }

  newUser = () => {
    this.setState({
      id: null,
      firstName: '',
      lastName: '',
      email: '',
      submitted: false
    });
  }

  render() {
    return (
      <div className="submit-form">
        {this.state.submitted ? (
          <div>
            <h4>{this.state.error ? this.state.message : 'User created successfully!'}</h4>
            <button className="btn btn-success" onClick={this.newUser}>
              Add
            </button>
          </div>
        ) : (
          <div>
            <div className="form-group">
              <label htmlFor="firstName">First Name</label>
              <input
                type="text"
                className="form-control"
                id="firstName"
                required
                value={this.state.firstName}
                onChange={this.onChange}
                name="firstName"
              />
            </div>

            <div className="form-group">
              <label htmlFor="lastName">Last Name</label>
              <input
                type="text"
                className="form-control"
                id="lastName"
                required
                value={this.state.lastName}
                onChange={this.onChange}
                name="lastName"
              />
            </div>

            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="text"
                className="form-control"
                id="email"
                required
                value={this.state.email}
                onChange={this.onChange}
                name="email"
              />
            </div>

            <button
              onClick={this.saveUser} className="btn btn-success"
              disabled={!this.state.firstName || !this.state.lastName || !this.state.email}
            >
              Submit
            </button>
          </div>
        )}
      </div>
    );
  }
}
