import { createRouter, createWebHistory } from 'vue-router';
import routeJs from './route.js';

import Layout from '@/layout'


export const constantRouterMap = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:pathMatch(.*)*',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/authredirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/errorPage/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/errorPage/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
      }
    ]
  }
];


export const asyncRouterMap = routeJs



// const parseJson = () => {
//   const mapArray = routeJson;
//   for (let i = 0; i < mapArray.length; i++) {
//     const item = mapArray[i];
//     item.component = Layout;
//     if (item.children && item.children.length > 0) {
//       for (let j = 0; j < item.children.length; j++) {
//         const childrenItem = item.children[j];
//         childrenItem.component = () => import(`@/views${childrenItem.page}`);
//       }
//     }
//   }
//   return mapArray;
// };

// export const asyncRouterMap = parseJson();

const router = createRouter({
  history: createWebHistory(),
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
});

export default router;