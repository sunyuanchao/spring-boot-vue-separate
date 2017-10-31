import Vue from 'vue'
import {
    Button,
    Select,
    Row,
    Col,
    Pagination,
    Table,
    TableColumn,
    Form,
    FormItem,
    Input,
    Dialog,
    Option
} from 'element-ui'
import App from './App.vue'
import VueRouter from "vue-router";
import VueResource from 'vue-resource';
import 'element-ui/lib/theme-default/index.css'
import lang from 'element-ui/lib/locale/lang/zh-CN'
import locale from 'element-ui/lib/locale'

// more grace import third package !
import moment from 'moment'
import axios from 'axios'
import curvejs from 'curvejs'

Object.defineProperty(Vue.prototype, '$moment', { value: moment });
Object.defineProperty(Vue.prototype, '$axios', { value: axios });
Object.defineProperty(Vue.prototype, '$curvejs', { value: curvejs });

Vue.use(VueResource);
Vue.use(VueRouter);
Vue.http.options.emulateJSON = true;

Vue.use(Button);
Vue.use(Select);
Vue.use(Row);
Vue.use(Col);
Vue.use(Pagination);
Vue.use(Table);
Vue.use(TableColumn);
Vue.use(Form);
Vue.use(FormItem);
Vue.use(Input);
Vue.use(Dialog);
Vue.use(Option);

locale.use(lang);



// eslint-disable-next-line no-new
new Vue({
    el: '#app',
    render: h => h(App)
});

//var vm =new Vue({
//    el: '#app',
//    router:new VueRouter({
//    	routes:[
//    		{ path: '/', redirect: 'persons' },
//    		{ path: '/persons', name:'persons',  component: app},
//    	]
//    })
//});

