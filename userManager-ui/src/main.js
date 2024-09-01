import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios';

// 在组件中通过this.$axios来访问
axios.defaults.baseURL = 'http://127.0.0.1:8080'
Vue.prototype.$axios = axios;

Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
