<template>
  <div>
    <!--条件搜索区域-->
    <el-row>
      <el-col :span="24">
        <el-card header=" ">
          <span class="header2">用户列表</span>
          <el-form :inline="true" style="text-align: center;">
            <el-form-item label="用户名称：">
              <el-input
                  v-model="searchuser.userName"
                  placeholder="请输入用户名"
                  clearable>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button icon="el-icon-search" @click="search">搜索</el-button>
            </el-form-item>
            <el-form-item>
              <el-button icon="el-icon-download" @click="exportExcel">导出Excel</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
    </el-row>

    <!--数据显示区域-->
    <el-row>
      <el-col :span="24">
        <el-card>
          <!-- 新增按钮 -->
          <el-header style="background-color: #ffffff; margin-left: -5px">
            <el-button @click="handleSave()" icon="el-icon-circle-plus-outline">新增用户</el-button>
          </el-header>
          <!-- 用户详细信息 -->
          <el-table :data="userData" style="width: 100%">
            <el-table-column
                width="80"
                prop="id"
                label="ID">
            </el-table-column>
            <el-table-column
                width="220"
                prop="name"
                label="用户名称">
            </el-table-column>
            <el-table-column
                width="140"
                prop="age"
                label="年龄">
            </el-table-column>
            <el-table-column
                width="120"
                prop="gender"
                label="性别">
            </el-table-column>
            <el-table-column
                prop="address"
                label="家庭地址">
            </el-table-column>
            <el-table-column
                prop="updateTime"
                label="更新时间">
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                    style="margin: 0 0 0 0"
                    icon="el-icon-setting"
                    size="mini"
                    type="warning"
                    @click="setDialog(scope.row.id,scope.row.name,scope.row.age,scope.row.gender,scope.row.address)"
                    slot="reference">编辑
                </el-button>
                <el-popconfirm :title="('确定删除 '+ scope.row.name +' 吗？')" @confirm="remove(scope.row.name)">
                  <el-button
                      style="margin: 0 0 0 15px"
                      icon="el-icon-close"
                      size="mini"
                      type="danger"
                      slot="reference">删除
                  </el-button>
                </el-popconfirm>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <!--分页-->
    <el-row class="page-row">
      <el-pagination
          class="el-pagination"
          layout="total, sizes, prev, pager, next, jumper"
          :page-sizes="[5, 10, 30, 50, 200]"
          @size-change="sizeChange"
          @current-change="currentChange"
          @prev-click="currentChange"
          @next-click="currentChange"
          :current-page="current"
          :page-size="size"
          :total="total">
      </el-pagination>
    </el-row>

    <!-- 添加 -->
    <el-dialog title="添加用户" :visible.sync="saveDialog" width="600px">
      <el-form :model="userObj" ref="ruleForm" label-width="80px">
        <el-form-item label="用户名称" prop="name" :rules="[{ required: true, message: '名称不能为空'}]">
          <el-input v-model="userObj.name" maxlength="8" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="[{ type: 'number', required: true, message: '年龄必须为数字值'}]">
          <el-input v-model.number="userObj.age"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="userObj.gender">
            <el-radio-button label="男"></el-radio-button>
            <el-radio-button label="女"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="家庭地址" prop="address">
          <el-input v-model="userObj.address" show-word-limit></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 255px; margin: 45px 0 0 65px" @click="userAdd"
                     :disabled="userObj.name===''||userObj.age===null||typeof this.userObj.age !== 'number'"
          >立即添加
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <!-- 编辑 -->
    <el-dialog title="编辑用户" :visible.sync="updateDialog" width="600px">
      <el-form :model="userSet" ref="ruleForm" label-width="80px">
        <el-form-item label="用户ID" prop="id">
          <el-input v-model="userSet.id" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="用户名称" prop="name" :rules="[{ required: true, message: '名称不能为空'}]">
          <el-input v-model="userSet.name" maxlength="8" show-word-limit></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age" :rules="[{ type: 'number', required: true, message: '年龄必须为数字值'}]">
          <el-input v-model.number="userSet.age"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="userSet.gender">
            <el-radio-button label="男"></el-radio-button>
            <el-radio-button label="女"></el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="家庭地址" prop="address">
          <el-input v-model="userSet.address" show-word-limit></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 255px; margin: 45px 0 0 65px" @click="updateuser"
                     :disabled="userSet.name===''||userSet.age===null||typeof this.userSet.age !== 'number'"
          >确定修改
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import userApi from "@/assets/api/userApi";
export default {
  data() {
    return {
      adminName: "",
      // 搜索条件对象
      searchuser: {
        userName: '',
      },
      // 用户数据 (页面初始化时会将数据传进来)
      userData: [],
      size: 5, // 每页的数据大小
      current: 1, //当前页数
      total: 0, // 总数量
      // 新增用户
      saveDialog: false,  // 是否显示
      // 添加的用户对象
      userObj: {
        name: '',
        age: null,
        gender: '男',
        address: ''
      },
      // 编辑用户
      updateDialog: false,  // 是否显示
      // 编辑的用户对象
      userSet: {
        id: null,
        name: '',
        age: null,
        gender: '男',
        address: ''
      },
    }
  },
  created() {
    this.getData()
  },
  methods: {
    // 添加框
    handleSave() {
      this.saveDialog = true
    },
    // 编辑框
    setDialog(id, name, age, gender, address) {
      this.userSet.id = id
      this.userSet.name = name
      this.userSet.age = age
      this.userSet.gender = gender
      this.userSet.address = address
      this.updateDialog = true
    },
    // 删除
    remove(name) {
      userApi.deleteUser({name: name }).then(r => {
        if (r.data.code === 200) {
          this.$message.success(r.data.message)
          this.getData()
        }else {
          this.$message.error(r.data.message)
        }
      })
    },
    // 立即添加
    userAdd() {
      userApi.addUser({
        name: this.userObj.name,
        age: this.userObj.age,
        gender: this.userObj.gender,
        address: this.userObj.address
      }).then(r => {
        if (r.data.code === 200) {
          this.$message.success(r.data.message)
          this.saveDialog = false
          this.getData()
          this.userObj.name = ''
          this.userObj.age = null
          this.userObj.gender = '男'
          this.userObj.address = ''
        }else {
          this.$message.error(r.data.message)
        }
      })
    },
    // 更新用户信息
    updateuser() {
      userApi.updateUser({
        id: this.userSet.id,
        name: this.userSet.name,
        age: this.userSet.age,
        gender: this.userSet.gender,
        address: this.userSet.address
      }).then(r => {
        if (r.data.code === 200) {
          this.$message.success(r.data.message)
          this.updateDialog = false
          this.getData()
          this.userSet.id = null
          this.userSet.name = ''
          this.userSet.age = null
          this.userSet.gender = '男'
          this.userSet.address = ''
        }else {
          this.$message.error(r.data.message)
        }
      })
    },
    // 每页大小修改
    sizeChange(val) {
      this.size = val
      if(this.searchuser.userName !== ''){
        this.search()
      }else {
        this.getData()
      }
    },
    // 根据搜索条件查询
    search() {
      if(this.searchuser.userName !== ''){
        this.getData()
      }else {
        this.$message.error("搜索的用户名不能为空")
        this.getData()
      }
    },
    // 第几页
    currentChange(val) {
      this.current = val
      if(this.searchuser.userName !== ''){
        this.search()
      }else {
        this.getData()
      }
    },
    // 获取数据，并赋值给userData
    getData() {
      userApi.getUserList({
        name: this.searchuser.userName,
        current: this.current,
        size: this.size
      }).then(r => {
        this.userData = r.data.data.userPage.records
        this.total = r.data.data.userPage.total
      })
    },
    // 下载用户信息Excel
    exportExcel(){
      window.open(userApi.exportExcel(this.searchuser.userName, this.current, this.size))
    }
  }
}
</script>

<style>
@import "../assets/css/user.css";
</style>