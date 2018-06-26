import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import Vuelidate from 'vuelidate'
import InputTag from 'vue-input-tag'
import { VueEditor } from 'vue2-editor'
import PulseLoader from 'vue-spinner/src/PulseLoader.vue'

import App from './App'
import router from './router'

// Import Stylesheets
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false

// Register modules
Vue.use(Vuelidate)
Vue.use(BootstrapVue);

// Register Components
Vue.component('input-tag', InputTag);
Vue.component('html-editor', VueEditor);
Vue.component('clip-loader',PulseLoader);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})