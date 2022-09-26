import React, { Component } from 'react';
import UserDataService from '../services/user.service';
import { withRouter } from '../common/with-router';

class User extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: {
        id: null,
        firstName: '',
        lastName: '',
        email: '',
        published: false
      },
      message: '',
    };
  }

  componentDidMount() {
    this.getUser(this.props.router.params.id);
  }

  onChange(e) {
    this.setState(function(prevState) {
      return {
        currentUser: {
          ...prevState.currentUser,
          [e.target.name]: e.target.value
        }
      };
    });
  }

  onChangeDescription(e) {
    const description = e.target.value;
    
    this.setState(prevState => ({
      currentUser: {
        ...prevState.currentUser,
        description: description
      }
    }));
  }

  getUser(id) {
    UserDataService.get(id)
      .then(response => {
        this.setState({
          currentUser: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  updateUser() {
    UserDataService.update(
      this.state.currentUser.id,
      this.state.currentUser
    )
      .then(response => {
        console.log(response.data);
        this.setState({
          message: 'The User was updated successfully!'
        });
      })
      .catch(e => {
        console.log(e);
      });
  }

  deleteUser() {    
    UserDataService.delete(this.state.currentUser.id)
      .then(response => {
        console.log(response.data);
        this.props.router.navigate('/users');
      })
      .catch(e => {
        console.log(e);
      });
  }

  render() {
    const { currentUser } = this.state;

    return (
      <div>
        {currentUser ? (
          <div className='edit-form'>
            <h4>User</h4>
            <form>
              <div className='form-group'>
                <label htmlFor='firstName'>First Name</label>
                <input
                  type='text'
                  className='form-control'
                  id='firstName'
                  name='firstName'
                  value={currentUser.firstName}
                  onChange={this.onChange}
                />
              </div>
              <div className='form-group'>
                <label htmlFor='lastName'>Last Name</label>
                <input
                  type='text'
                  className='form-control'
                  id='lastName'
                  name='lastName'
                  value={currentUser.lastName}
                  onChange={this.onChange}
                />
              </div>
              <div className='form-group'>
                <label htmlFor='email'>Email</label>
                <input
                  type='text'
                  className='form-control'
                  id='email'
                  name='email'
                  value={currentUser.email}
                  onChange={this.onChange}
                />
              </div>
              <div className='form-group'>
                <label htmlFor='status'>
                  <strong>Status:</strong>
                </label>
                {currentUser.published ? 'Published' : 'Pending'}
              </div>
            </form>

            <button
              className='badge badge-danger mr-2'
              onClick={this.deleteUser}
            >
              Delete
            </button>

            <button
              type='submit'
              className='badge badge-success'
              onClick={this.updateUser}
            >
              Update
            </button>
            <p>{this.state.message}</p>
          </div>
        ) : (
          <div>
            <br />
            <p>Please click on a User...</p>
          </div>
        )}
      </div>
    );
  }
}

export default withRouter(User);