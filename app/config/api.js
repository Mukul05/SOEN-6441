'use strict';
const {
  DUMMY_API_BASE_URL,
  DUMMY_API_ENDPOINT,
  APP_ID,
} = require('./config');

class User {
  constructor () {
    this.users = [];
  }

  async getUsers(page = 0, limit = 10) {
    let url = DUMMY_API_BASE_URL + DUMMY_API_ENDPOINT;
    url += `?page=${page}&limit=${limit}`; 
    fetch(url, {
      ['app-id']: APP_ID,
    })
    .then(data => {
      this.users = data;
    })
    .catch(error => {
      console.log('[api.js] Error fetching users', error);
    })
  }

  async createUser(userData) {
    const {
      firstName,
      lastName,
      email,
    } = userData;
    // all required validation
    const url = DUMMY_API_BASE_URL + DUMMY_API_ENDPOINT;
    fetch(url, {
      method: 'POST',
      ['app-id']: APP_ID,
      body: {
        firstName,
        lastName,
        email,
      },
    })
    .then(async newUser => {
      console.log('[api.js] Successfully created user', newUser);
      await this.getUsers();
    })
    .catch(error => {
      console.log('[api.js] Error creating user', error);
    })
  }

  async updateUser(userData) {
    const {
      userId,
      firstName,
      lastName,
    } = userData;
    const url = `${DUMMY_API_BASE_URL}${DUMMY_API_ENDPOINT}/${userId}`;
    fetch(url, {
      method: 'PUT',
      ['app-id']: APP_ID,
      body: {
        firstName,
        lastName,
      },
    })
    .then(async newUser => {
      console.log('[api.js] Successfully updated user', newUser);
      await this.getUsers();
    })
    .catch(error => {
      console.log('[api.js] Error updating user', error);
    })
  }

  async deleteUser(userId) {
    if (!userId) {
      return;
    }
    const url = `${DUMMY_API_BASE_URL}${DUMMY_API_ENDPOINT}/${userId}`;
    fetch(url, {
      method: 'DELETE',
      ['app-id']: APP_ID,
      body: {
        firstName,
        lastName,
      },
    })
    .then(async response => {
      console.log('[api.js] Successfully deleted user');
      await this.getUsers();
    })
    .catch(error => {
      console.log('[api.js] Error deleting user', error);
    })
  }
}

module.exports = User;