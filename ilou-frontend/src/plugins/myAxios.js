import axios from "axios";

// Set config defaults when creating the instance
const myAxios = axios.create({
    baseURL: 'https://ilou-api.yinan.fun/api',
});

// 添加请求拦截器
// axios.interceptors.request.use(function (config) {
//     // 在发送请求之前做些什么
//     console.log('request');
//     console.log('我发请求了', config);
//     return config;
// }, function (error) {
//     // 对请求错误做些什么
//     return Promise.reject(error);
// });

// 添加响应拦截器
// axios.interceptors.response.use(function (response) {
//     // 对响应数据做点什么
//     console.log('我收到响应了', response);
//     return response;
// }, function (error) {
//     // 对响应错误做点什么
//     return Promise.reject(error);
// });

export default myAxios;