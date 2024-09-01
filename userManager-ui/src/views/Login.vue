<template>
  <div class="login">
    <!-- 卡片 -->
    <el-card class="box-card">
      <h1 style="margin: 0 0 14px 90px">用户管理系统</h1>
      <!-- 登录 or 注册 -->
      <el-radio-group v-model="labelPosition" class="radioGroup" size="small">
        <el-radio-button label="login">登录</el-radio-button>
        <el-radio-button label="signIn">注册</el-radio-button>
      </el-radio-group>
      <!-- user输入表单 -->
      <el-form label-position="right" label-width="80px" :model="user">
        <el-form-item
            label="用户名"
            prop="name"
            :rules="[ { required: true, message: '请输入用户名', trigger: 'blur' } ]">
          <el-input v-model="user.name"></el-input>
        </el-form-item>
        <el-form-item
            label="密码"
            prop="password"
            :rules="[ { required: true, message: '请输入密码', trigger: 'blur' } ]">
          <el-input type="password" v-model="user.password" show-password></el-input>
        </el-form-item>
        <el-form-item
            v-if="labelPosition==='signIn'"
            label="确认密码"
            prop="checkPassword"
            :rules="[ { required: true, message: '请输入再次输入密码', trigger: 'blur' } ]">
          <el-input type="password" v-model="user.checkPassword" show-password></el-input>
        </el-form-item>
        <!--按钮-->
        <el-form-item class="button">
          <el-button v-if="labelPosition==='login'" type="warning" @click="login"
                     :disabled="user.name===''||user.password===''" round>登录
          </el-button>
          <el-button v-if="labelPosition==='signIn'" type="warning" @click="signIn"
                     :disabled="user.name===''||user.password===''||user.checkPassword===''" round>注册
          </el-button>
          <el-button @click="resetForm" round>重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import router from "@/router";

export default {
  data() {
    return {
      labelPosition: 'login',  // 开始先定位到登录
      // 用户数据
      user: {
        name: '',
        password: '',
        checkPassword: '',
      }
    }
  },
  mounted() {
    this.getCookie()
  },
  methods: {
    // 登录
    login() {
      this.$router.push("/")
      this.setCookie(this.user.name, this.user.password, 7)  // 保存用户名密码到cookie中7天
    },
    // 注册
    signIn() {
      if (this.user.checkPassword !== this.user.password) {
        this.$message.error("两次输入的密码不一致!")
      }
    },
    // 重置表单
    resetForm() {
      this.user.name = ""
      this.user.password = ""
      this.user.checkPassword = ""
    },
    //设置cookie，储存账户和密码
    setCookie(c_name, c_pwd, exdays) {
      let exdate = new Date(); //获取时间
      exdate.setTime(exdate.getTime() + 24 * 60 * 60 * 1000 * exdays); //保存的天数
      //字符串拼接cookie
      window.document.cookie =
          "account" + "=" + c_name + ";path=/;expires=" + exdate.toGMTString();
      window.document.cookie =
          "password" + "=" + c_pwd + ";path=/;expires=" + exdate.toGMTString();
    },
    // 读取cookie中的账户和密码
    getCookie: function() {
      let that = this;
      if (document.cookie.length > 0) {
        let arr = document.cookie.split("; "); //这里显示的格式需要切割一下自己可输出看下
        for (let i = 0; i < arr.length; i++) {
          let arr2 = arr[i].split("="); //再次切割
          //判断查找相对应的值
          if (arr2[0] === "account") {
            that.user.name = arr2[1]; //保存到保存数据的地方
          } else if (arr2[0] === "password") {
            that.user.password = arr2[1];
          }
        }
      }
    },
  }
}
</script>

<style>
@import "../assets/css/Login.css";
</style>