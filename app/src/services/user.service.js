import http from '../http-common';

class UserDataService {
  getAll() {
    return http.get('/user');
  }

  get(id) {
    return http.get(`/user?userId=${id}`);
  }

  create(data) {
    return http.post('/user', data);
  }

  update(id, data) {
    return http.put(`/user?userId=${id}`, data);
  }

  delete(id) {
    return http.delete(`/user?userId=${id}`);
  }

  findByName(name) {
    return http.get(`/user?name=${name}`);
  }
}

export default new UserDataService();