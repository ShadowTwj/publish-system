import axios from 'axios';

// let base = '';
let base = 'http://localhost:8080/publish-service';

let header = {
    'content-type': 'application/json;charset=UTF-8'
};


export const requestLogin = params => {
    return axios.post(`${base}/login`, params, {headers: header}).then(res => res.data);
};

export const getUserList = params => {
    return axios.get(`${base}/user/list`, {params: params});
};

export const getUserListPage = params => {
    return axios.get(`${base}/user/listpage`, {params: params});
};

export const removeUser = params => {
    return axios.get(`${base}/user/remove`, {params: params});
};

export const batchRemoveUser = params => {
    return axios.get(`${base}/user/batchremove`, {params: params});
};

export const editUser = params => {
    return axios.get(`${base}/user/edit`, {params: params});
};

export const addUser = params => {
    return axios.post(`${base}/user/add`, params, {headers: header}).then(res => res.data);
};