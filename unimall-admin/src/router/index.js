import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/views/layout/Layout'

/** note: Submenu only appear when children.length>=1
 *  detail see  https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 **/

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    perms: ['GET /aaa','POST /bbb']     will control the page perms (you can set multiple perms)
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
    noCache: true                if true ,the page will no be cached(default is false)
  }
**/
export const constantRouterMap = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
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
    redirect: 'dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: { title: 'dashboard', icon: 'dashboard', noCache: true }
      }
    ]
  }
]

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

export const asyncRouterMap = [
  {
    path: '/user',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'userManage',
    meta: {
      title: '用户管理',
      icon: 'user'
    },
    children: [
      {
        path: 'user',
        component: () => import('@/views/user/user'),
        name: 'user',
        meta: {
          perms: ['admin:user:list', 'admin:user:create'],
          title: '会员管理',
          noCache: true
        }
      }
    ]
  },

  {
    path: '/mall',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'mallManage',
    meta: {
      title: '运营管理',
      icon: 'chart'
    },
    children: [
      {
        path: 'order',
        component: () => import('@/views/operation/order'),
        name: 'order',
        meta: {
          perms: ['admin:order:list', 'admin:order:detail', 'admin:order:ship'],
          title: '订单管理',
          noCache: true
        }
      },
      {
        path: 'appraise',
        component: () => import('@/views/operation/appraise'),
        name: 'appraise',
        meta: {
          perms: ['admin:order:list', 'admin:order:detail', 'admin:order:ship'],
          title: '评论管理',
          noCache: true
        }
      },
      {
        path: 'freight',
        component: () => import('@/views/operation/freight'),
        name: 'freight',
        meta: {
          perms: ['admin:freight:list', 'admin:freight:detail', 'admin:freight:ship'],
          title: '运费模板管理',
          noCache: true
        }
      }
    ]
  },

  {
    path: '/goods',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'goodsManage',
    meta: {
      title: '商品管理',
      icon: 'shopping'
    },
    children: [
      {
        path: 'list',
        component: () => import('@/views/goods/list'),
        name: 'goodsList',
        meta: {
          perms: ['admin:goods:list', 'admin:goods:delete'],
          title: '商品列表',
          noCache: true
        }
      },
      {
        path: 'category',
        component: () => import('@/views/goods/category'),
        name: 'goodsCategory',
        meta: {
          perms: ['admin:category:create', 'admin:category:list', 'admin:category:update', 'admin:category:delete'],
          title: '商品类目',
          noCache: true
        }
      },
      {
        path: 'upsert',
        component: () => import('@/views/goods/upsert'),
        name: 'goodsUpsert',
        meta: {
          perms: ['operation:goods:create', 'operation:goods:edit'],
          title: '商品编辑',
          noCache: true
        },
        hidden: true
      }
    ]
  },
  {
    path: '/promotion',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'promotionManage',
    meta: {
      title: '推广管理',
      icon: 'tree'
    },
    children: [
      {
        path: 'merchantad',
        component: () => import('@/views/promotion/merchantad'),
        name: 'merchantad',
        meta: {
          perms: ['promote:advertisement:create', 'promote:advertisement:delete', 'promote:advertisement:update', 'promote:advertisement:query'],
          title: '商铺广告',
          noCache: true
        }
      },
      {
        path: 'coupon',
        component: () => import('@/views/promotion/coupon'),
        name: 'coupon',
        meta: {
          perms: ['promote:coupon:create', 'promote:coupon:delete', 'promote:coupon:update', 'promote:coupon:query'],
          title: '优惠卷管理',
          noCache: true
        }
      },
      {
        path: 'merchant',
        component: () => import('@/views/promotion/merchant'),
        name: 'merchant',
        meta: {
          perms: ['promote:coupon:create', 'promote:coupon:delete', 'promote:coupon:update', 'promote:coupon:query'],
          title: '商铺信息',
          noCache: true
        }
      }
    ]
  },
  {
    path: '/sys',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'sysManage',
    meta: {
      title: '系统管理',
      icon: 'chart'
    },
    children: [
      {
        path: 'admin',
        component: () => import('@/views/sys/admin'),
        name: 'admin',
        meta: {
          perms: ['admin:admin:list', 'admin:admin:create', 'admin:admin:update', 'admin:admin:delete'],
          title: '管理员',
          noCache: true
        }
      },
      {
        path: 'role',
        component: () => import('@/views/sys/role'),
        name: 'role',
        meta: {
          perms: ['admin:role:list', 'admin:role:create', 'admin:role:delete', 'admin:role:update', 'admin:role:permissionList', 'admin:permission:list'],
          title: '角色管理',
          noCache: true
        }
      }
    ]
  },
  {
    path: '/profile',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    children: [
      {
        path: 'password',
        component: () => import('@/views/profile/password'),
        name: 'password',
        meta: { title: '修改密码', noCache: true }
      }
    ],
    hidden: true
  },

  { path: '*', redirect: '/404', hidden: true }
]
