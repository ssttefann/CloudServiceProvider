import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vuetify from './plugins/vuetify';
import axios from 'axios';
import {store} from './store/store'
import DatetimePicker from 'vuetify-datetime-picker'


Vue.use(DatetimePicker)
Vue.config.productionTip = false

Vue.prototype.$axios = axios

export const bus = new Vue();

new Vue({
  router,
  vuetify,
  store,
  render: h => h(App)
}).$mount('#app')
