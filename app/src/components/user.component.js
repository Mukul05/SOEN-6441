import React, { Component } from 'react';
import UserDataService from '../services/user.service';
import { withRouter } from '../common/with-router';

class User extends Component {
  constructor(props) {
    super(props);

    this.state = {
      isLoading: false,
      isDelete: false,
      currentUser: {
        id: null,
        firstName: '',
        lastName: '',
        email: '',
        published: false,
      },
      fields: [{
        key: 'firstName',
        label: 'First Name',
      }, {
        key: 'lastName',
        label: 'Last Name',
      }, {
        key: 'email',
        label: 'Email',
      }],
      message: '',
    };
  }

  componentDidMount() {
    this.getUser(this.props.router.params.id);
  }

  onChange = (e) => {
    this.setState((prevState) => {
      return {
        currentUser: {
          ...prevState.currentUser,
          [e.target.name]: e.target.value
        }
      };
    });
  }

  getUser(id) {
    UserDataService.get(id)
      .then(response => {
        this.setState({
          currentUser: response.data
        });
      })
      .catch(e => {
        console.log(e);
      });
  }

  updateUser = () => {
    this.setState({
      isLoading: true,
    });
    UserDataService.update(
      this.state.currentUser.id,
      this.state.currentUser
    )
      .then(response => {
        this.setState({
          message: 'The User was updated successfully!',
          isLoading: false,
        });
      })
      .catch(e => {
        this.setState({
          isLoading: false,
        });
        console.log(e);
      });
  }

  deleteUser = () => {
    UserDataService.delete(this.state.currentUser.id)
      .then(response => {
        this.props.router.navigate('/users');
      })
      .catch(e => {
        this.setState({
          isDelete: false,
        });
        console.log(e);
      });
  }

  render() {
    const { currentUser } = this.state;

    return (
      this.state.isLoading ? (
        <div
          style={{
            position: 'fixed',
            width: '100%',
            height: '100%',
            display: 'flex',
            alignItems: 'center',
            top: '0',
          }}
        >
          <div
            className="spinner-border"
            role="status"
            style={{ margin: '0 auto' }}
          />
        </div>
      ) : (
        <div>
          {currentUser ? (
            <div className='edit-form'>
              <h4>User</h4>
              <form>
                {this.state.fields.map(({ key, label }) => (
                  <div key={key} className='form-group'>
                    <label htmlFor={key}>{label}</label>
                    <input
                      type='text'
                      className='form-control'
                      id={key}
                      name={key}
                      value={currentUser[key]}
                      onChange={this.onChange}
                    />
                  </div>
                ))}
              </form>

              <button
                type="button"
                className="btn btn-danger"
                onBlur={() => this.setState({ isDelete: false })}
                onClick={() => {
                  if (this.state.isDelete) {
                    this.deleteUser();
                  }
                  else {
                    this.setState({ isDelete: true });
                  }
                }}
              >
                {this.state.isDelete ? 'Confirm?' : 'Delete'}
              </button>
              <button
                type='submit'
                style={{ margin: 8 }}
                className='btn btn-success'
                onClick={this.updateUser}
              >
                Update
              </button>
              <p>{this.state.message}</p>
            </div>
          ) : (
            <div>
              <br />
              <p>No user selected</p>
            </div>
          )}
        </div>
      )
    );
  }
}

export default withRouter(User);