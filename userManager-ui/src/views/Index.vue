<template>
  <el-container class="main">

    <!--头部区域-->
    <el-header>
      <div>
        <router-link to="/cpgl" class="logo-title">&nbsp;&nbsp;用户管理系统</router-link>
      </div>
      <el-dropdown>
        <span class="el-dropdown-link">{{ adminName }}<i class="el-icon-arrow-down el-icon--right"></i></span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="loginOut">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>

    <!--页面主体区域-->
    <el-container>

      <!--侧边栏-->
      <!-- 用户管理、订单管理 -->
      <el-aside style="width: 200px">
        <el-menu text-color="#008390" active-text-color="#008390" class="el-menu-vertical-demo">
          <router-link class="router-link-active" to="/user">
            <el-menu-item index="1">
              <template slot="title">
                <i class="el-icon-user" style="color: #008390"></i>
                <span slot="title">用户管理</span>
              </template>
            </el-menu-item>
          </router-link>
        <!--
          <router-link class="router-link-active" to="/ddgl">
            <el-menu-item index="2">
              <template slot="title">
                <i class="el-icon-date" style="color: #008390"></i>
                <span slot="title">订单管理</span>
              </template>
            </el-menu-item>
          </router-link>
           -->
        </el-menu>
      </el-aside>


      <!--右侧内容主体-->
      <el-main style="background-color: white">
        <router-view></router-view>
      </el-main>

    </el-container>
  </el-container>
</template>


<script>
export default {
  name: "index",
  data() {
    return {
      adminName: "管理员"
    }
  },
  created() {
  },
  methods: {
    //退出登录
    loginOut() {
      localStorage.clear()
      this.$router.push("/login")
    }
  }
}
</script>

<style>
@import "../assets/css/Index.css";
</style>