import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import { library } from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
/* import specific icons */
import { faTrashCan } from '@fortawesome/free-solid-svg-icons'
import { faPenToSquare } from '@fortawesome/free-solid-svg-icons'
import { faBars } from '@fortawesome/free-solid-svg-icons'
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons'
import { faMessage } from '@fortawesome/free-solid-svg-icons'
import { faUser } from '@fortawesome/free-solid-svg-icons'
import { faVenus } from '@fortawesome/free-solid-svg-icons'
import { faMars } from '@fortawesome/free-solid-svg-icons'

import MathJax, { initMathJax, renderByMathjax } from 'mathjax-vue'

function onMathJaxReady() {
    const el = document.getElementById('app')
    renderByMathjax(el)
}
initMathJax({}, onMathJaxReady)
    /* add icons to the library */
library.add(faTrashCan)
library.add(faPenToSquare)
library.add(faPaperPlane)
library.add(faBars)
library.add(faMessage)
library.add(faUser)
library.add(faVenus)
library.add(faMars)

Vue.use(MathJax)

Vue.use(ElementUI)
    //* add font awesome icon component */
Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.config.productionTip = false

export const EventBus = new Vue();

new Vue({
    model: 'hash',
    render: h => h(App),
}).$mount('#app')