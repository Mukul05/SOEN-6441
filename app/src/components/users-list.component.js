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
      searchQuery: '',
      pageNumber: 1,
      pageSize: 5,
      totalCount: 20
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
    UserDataService.getAll(this.state.pageNumber, this.state.pageSize, this.state.searchQuery)
      .then(response => {
        const { users = [], count = 0 } = response.data || {};
        this.setState({
          users,
          totalCount: count,
        });
      })
      .catch(e => {
        console.log(e);
      });
  }

  handleSearch = (e) => {
    this.setState({
      searchQuery: e.target.value,
    }, () => {
      this.retrieveUsers();
    });
  }

  componentDidUpdate(prevProps, prevState) {
    if ((prevState.pageNumber !== this.state.pageNumber) || (prevState.pageSize !== this.state.pageSize)) {
      this.retrieveUsers();
    }
  }

  setActiveUser = (user, index) => {
    this.setState({
      currentUser: user,
      currentIndex: index
    });
  }

  render() {
    const { users, currentUser, currentIndex } = this.state;

    return (
      <div className="list row">
        
        <div className="col-md-6">
          <div>
            <h4>Users List</h4>
            <span>(Click to edit)</span>
            <div className="input-group input-group-sm mb-3">
              <input
                type="text"
                placeholder="Search by name..."
                className="form-control"
                aria-label="Sizing example input"
                aria-describedby="inputGroup-sizing-sm"
                onChange={this.handleSearch}
              />
            </div>
          </div>

          <ul className="list-group" style={{ margin: 8 }}>
            {users && users.length ? (
              users.map((user, index) => (
                <li
                  className={
                    'list-group-item ' +
                    (index === currentIndex ? 'active' : '')
                  }
                  style={{ cursor: 'pointer' }}
                  onClick={() => this.setActiveUser(user, index)}
                  key={index}
                >
                  {`${user.firstName} ${user.lastName}`}
                </li>
              ))) : 'No users found'}
          </ul>
          <span style={{ margin: 8 }}>
            <nav style={{ float: 'right' }}>
              <ul className="pagination">
                <li className={`page-item ${this.state.pageNumber === 1 ? 'disabled' : ''}`}>
                  <button
                    className="page-link"
                    onClick={() => this.setState((prevState) => ({ 
                      pageNumber: prevState.pageNumber - 1 
                    }))}
                  >
                    Previous
                  </button>
                </li>
                <li className={`page-item ${(this.state.pageSize * this.state.pageNumber) >= this.state.totalCount ? 'disabled' : ''}`}>
                  <button
                    className='page-link'
                    onClick={() => this.setState((prevState) => ({ 
                      pageNumber: prevState.pageNumber + 1 
                    }))}
                  >
                    Next
                  </button>
                </li>
              </ul>
            </nav>
            <select
              className='form-select'
              onChange={(event) => this.setState({
                pageSize: event.target.value,
                pageNumber: 1,
                searchQuery: '',
              })}
              defaultValue={5}
            >
              {[5, 10, 20, 30].map(val => (
                <option
                  key={val}
                  value={val}
                >
                  {val}
                </option>
              ))}
            </select>
          </span>
        </div>
        {users.length ? <div className="col-md-6">
          { currentUser ? (
            <div>
              <h4>User Details</h4>
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
