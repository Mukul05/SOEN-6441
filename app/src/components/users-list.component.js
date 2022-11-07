/* eslint-disable jsx-a11y/no-noninteractive-element-interactions */
/* eslint-disable jsx-a11y/click-events-have-key-events */
import { Component } from 'react';
import UserDataService from '../services/user.service';
import { Link } from 'react-router-dom';

export default class UsersList extends Component {
  constructor(props) {
    super(props);

    this.state = {
      users: [],
      currentUser: null,
      currentIndex: -1,
      searchTitle: '',
    };
  }

  componentDidMount() {
    this.retrieveUsers();
  }

  retrieveUsers = () => {
    this.setState({
      currentUser: null,
      currentIndex: -1
    });
    UserDataService.getAll()
      .then(response => {
        this.setState({
          users: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  setActiveUser = (user, index) => {
    this.setState({
      currentUser: user,
      currentIndex: index
    });
  }

  searchTitle() {
    this.setState({
      currentUser: null,
      currentIndex: -1
    });

    UserDataService.findByTitle(this.state.searchTitle)
      .then(response => {
        this.setState({
          Users: response.data
        });
        console.log(response.data);
      })
      .catch(e => {
        console.log(e);
      });
  }

  render() {
    const { users, currentUser, currentIndex } = this.state;

    return (
      <div className="list row">
        
        <div className="col-md-6">
          <h4>Users List
            <h6>(Click to edit)</h6>
          </h4>

          <ul className="list-group">
            {users && users.length ? (
              users.map((user, index) => (
                <li
                  className={
                    'list-group-item ' +
                    (index === currentIndex ? 'active' : '')
                  }
                  onClick={() => this.setActiveUser(user, index)}
                  key={index}
                >
                  {`${user.firstName} ${user.lastName}`}
                </li>
              ))) : 'No users found'}
          </ul>
        </div>
        {users.length ? <div className="col-md-6">
          { currentUser ? (
            <div>
              <h4>User</h4>
              <div>
                <label htmlFor='firstName'>
                  <strong>First Name:</strong>
                </label>{' '}
                {currentUser.firstName}
              </div>
              <div>
                <label htmlFor='lastName'>
                  <strong>Last Name:</strong>
                </label>{' '}
                {currentUser.lastName}
              </div>
              <div>
                <label htmlFor='email'>
                  <strong>Email:</strong>
                </label>{' '}
                {currentUser.email}
              </div>

              <Link
                to={'/users/' + currentUser.id}
                className="badge badge-warning"
              >
                Edit
              </Link>
            </div>
          ) : null}
        </div> : null}
      </div>
    );
  }
}
