import Vue from 'vue'
import Router from 'vue-router'

import EmailComposer from '@/components/EmailComposer'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'EmailComposer',
      component: EmailComposer
    }
  ]
})
