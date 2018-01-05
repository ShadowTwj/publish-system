import axios from 'axios';

// let base = '';
let base = 'http://localhost:8080/publish-service';

let header = {
    'content-type': 'application/json;charset=UTF-8'
};


export const requestLogin = params => {
    return axios.post(`${base}/login`, params, {headers: header}).then(res => res.data);
};

export const getUserList = () => {
    return axios.get(`${base}/user/list`);
};

// export const getUserListPage = params => {
//     return axios.get(`${base}/user/listpage`, {params: params});
// };

export const removeUser = params => {
    return axios.post(`${base}/user/remove`, params, {headers: header}).then(res => res.data);
};

export const batchRemoveUser = params => {
    return axios.post(`${base}/user/batchRemove`, params, {headers: header}).then(res => res.data);
};

export const editUser = params => {
    return axios.post(`${base}/user/edit`, params, {headers: header}).then(res => res.data);
};

export const addUser = params => {
    return axios.post(`${base}/user/add`, params, {headers: header}).then(res => res.data);
};

export const findAccount = params => {
    return axios.get(`${base}/user/findAccount?account=` + params).then(res => res.data);
};

export const getProjectList = () => {
    return axios.get(`${base}/project/list`).then(res => res.data);
};

export const addProject = params => {
    return axios.post(`${base}/project/add`, params, {headers: header}).then(res => res.data);
};

export const editProject = params => {
    return axios.post(`${base}/project/edit`, params, {headers: header}).then(res => res.data);
};

export const removeProject = params => {
    return axios.post(`${base}/project/remove`, params, {headers: header}).then(res => res.data);
};