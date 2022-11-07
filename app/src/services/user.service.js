import http from '../http-common';

const DEFAULT_PAGE_NUMBER = 1;
const DEFAULT_PAGE_SIZE = 5;

class UserDataService {

  getAll(pageNumber = DEFAULT_PAGE_NUMBER, pageSize = DEFAULT_PAGE_SIZE, query = '') {
    let url = '/user';
    if (query) {
      url += `?searchQuery=${query}`;
    } else {
      url += `?pageNumber=${pageNumber}&pageSize=${pageSize}`;
    }
    return http.get(url);
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