import Vue from 'vue'
import Router from 'vue-router'
// in development env not use Lazy Loading,because Lazy Loading too many pages will cause webpack hot update too slow.so only in production use Lazy Loading
/* layout */
import Layout from '../views/layout/Layout'
import { resolve } from 'url'

const _import = require('./_import_' + process.env.NODE_ENV)
Vue.use(Router)
export const constantRouterMap = [
  { path: '/login', component: _import('login/index'), hidden: true },
  { path: '/404', component: _import('404'), hidden: true },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: '首页',
    hidden: true,
    children: [{
      path: 'dashboard', component: _import('dashboard/index')
    }]
  }
]
export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
export const asyncRouterMap = [
  {
    path: '/system',
    component: Layout,
    redirect: '/system/article',
    name: '功能模块',
    meta: { title: '功能模块', icon: 'tree' },
    children: [
      {
        path: 'article',
        name: '文章',
        component: _import('article/article'),
        meta: { title: '文章', icon: 'example' },
        menu: 'article'
      },
      {
        path: 'businessInformation',
        name: '戶主',
        component: _import('business/businessInformation'),
        meta: { title: '戶主', icon: 'example' },
        menu: 'businessInformation'
      },
    ]
  },
  {
    path: '/business',
    component: Layout,
    redirect: '/business/businessInformation',
    name: '商家模块',
    meta: { title: '商家模块', icon: 'tree' },
    children: [
      {
        path: 'businessInformation',
        name: '商家信息',
        component: _import('business/businessInformation'),
        meta: { title: '商家信息', icon: 'user' },
        menu: 'businessInformation'
      },
      {
        path: 'commodityInformation',
        name: '商品信息管理',
        component: _import('business/commodityInformation'),
        meta: { title: '商品信息管理', icon: 'example' },
        menu: 'commodityInformation'
      },
      {
        path: 'categoryInformation',
        name: '商品分类管理',
        component: _import('business/categoryInformation'),
        meta: { title: '商品分类管理', icon: 'example' },
        menu: 'categoryInformation'
      },
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/user',
    name: '用户权限',
    meta: { title: '用户权限', icon: 'table' },
    children: [
      {
        path: 'user',
        name: '用户列表',
        component: _import('user/user'),
        meta: { title: '用户列表', icon: 'user' },
        menu: 'user'
      },
      {
        path: 'role',
        name: '权限管理',
        component: _import('user/role'),
        meta: { title: '权限管理', icon: 'password' },
        menu: 'role'
      },
      {
        path: 'article1',
        name: '权限配置',
        component: _import('page/article1'),
        meta: { title: '权限配置', icon: 'password' },
        menu: 'article1'
      },
    ]
  },
  {
    path: '/person',
    component: Layout,
    redirect: '/person/personInformation',
    name: '个人模块',
    meta: { title: '个人模块', icon: 'table' },
    children: [
      {
        path: 'personInformation',
        name: '个人信息',
        component: _import('person/personInformation'),
        meta: { title: '个人信息', icon: 'user' },
        menu: 'personInformation'
      },
      {
        path: 'myWallet',
        name: '我的钱包',
        component: _import('person/myWallet'),
        meta: { title: '我的钱包', icon: 'password' },
        menu: 'myWallet'
      },
    ]
  },
  {
    path: '/supermarket',
    component: Layout,
    redirect: '/supermarket/supermarket',
    name: '购物商城',
    meta: { title: '购物商城', icon: 'table' },
    children: [
      {
        path: 'supermarket',
        name: 'maetS商城',
        component: _import('supermarket/supermarket'),
        meta: { title: 'maetS商城', icon: 'user' },
        menu: 'supermarket'
      },
      {
        path: 'shoppingCart',
        name: "购物车",
        component: _import('supermarket/shoppingCart'),
        meta: { title: '购物车', icon: 'user' },
        menu: 'shoppingCart'
      },
      {
        path: 'commodityDetails',
        name: "商品详情",
        component: _import('supermarket/commodityDetails'),
        meta: { title: '商品详情', icon: 'user' },
        hidden: true
      },
      {
        path: 'settlement',
        name: "结算页",
        component: _import('supermarket/settlement'),
        meta: { title: '结算页', icon: 'user' },
        hidden: true
      },
      {
        path: 'submitSuccess',
        name: "提交成功！",
        component: _import('supermarket/submitSuccess'),
        meta: { title: '提交成功！', icon: 'user' },
        hidden: true
      },
      {
        path: 'singleOrder',
        name: "查看订单",
        component: _import('supermarket/singleOrder'),
        meta: { title: '查看订单', icon: 'user' },
        hidden: true
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true },

]
