import axios from 'axios';

export default axios.create({
  baseURL: 'http://localhost:8001/server/',
  headers: {
    'Content-type': 'application/json'
  }
});