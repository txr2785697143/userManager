import axios from 'axios'

export default {

    // 查询用户信息
    getUserList(data){
        return axios({
            method: 'get',
            url: 'user/getUserList',
            params: data
        })
    },

    // 新增用户信息
    addUser(data){
        return axios({
            method: 'post',
            url: '/user/addUser',
            data: data
        })
    },

    // 修改用户信息
    updateUser(data){
        return axios({
            method: 'put',
            url: '/user/updateUser',
            data: data
        })
    },

    // 删除用户信息
    deleteUser(data){
        return axios({
            method: 'delete',
            url: '/user/deleteUser',
            params: data
        })
    },

    // 下载用户表Excel
    exportExcel(name, current, size){
        if(name === '' || name === null){
            name = ' '
        }
        return axios.defaults.baseURL+'/user/exportExcel/'+name+'/'+current+'/'+size+'/用户表'
    }
}
