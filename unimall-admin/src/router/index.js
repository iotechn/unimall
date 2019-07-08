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
      },
      {
        path: 'orgapply',
        component: () => import('@/views/user/orgapply'),
        name: 'orgapply',
        meta: {
          perms: ['admin:orgapply:list', 'admin:orgapply:onekey', 'admin:orgapply:audit'],
          title: '用户表单',
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
        component: () => import('@/views/mall/order'),
        name: 'order',
        meta: {
          perms: ['admin:order:list', 'admin:order:detail', 'admin:order:ship'],
          title: '订单管理',
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
        path: 'create',
        component: () => import('@/views/goods/create'),
        name: 'goodsCreate',
        meta: {
          perms: ['admin:goods:create'],
          title: '商品上架',
          noCache: true
        }
      },
      {
        path: 'category',
        component: () => import('@/views/goods/category'),
        name: 'goodsCategory',
        meta: {
          perms: ['admin:category:create','admin:category:list','admin:category:update','admin:category:delete'],
          title: '商品类目',
          noCache: true
        }
      },
      {
        path: 'edit',
        component: () => import('@/views/goods/edit'),
        name: 'goodsEdit',
        meta: {
          perms: ['admin:goods:detail', 'admin:goods:update'],
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
        path: 'merchant',
        component: () => import('@/views/promotion/merchant'),
        name: 'merchant',
        meta: {
          perms: ['admin:nearby:detail', 'admin:nearby:upsert'],
          title: '商铺管理',
          noCache: true
        }
      },
      {
        path: 'merchantad',
        component: () => import('@/views/promotion/merchantad'),
        name: 'merchantad',
        meta: {
          perms: ['admin:nearby:adlist', 'admin:nearby:adcreate', 'admin:nearby:adupdate', 'admin:nearby:addelete'],
          title: '商铺广告',
          noCache: true
        }
      },
      {
        path: 'nearbyform',
        component: () => import('@/views/promotion/nearbyform'),
        name: 'nearbyform',
        meta: {
          perms: ['admin:nearbyform:list', 'admin:nearbyform:complete'],
          title: '预申请表',
          noCache: true
        }
      },
    ]
  },
  {
    path: '/tenement',
    component: Layout,
    redirect: 'noredirect',
    alwaysShow: true,
    name: 'tenementManage',
    meta: {
      title: '校区管理',
      icon: 'people'
    },
    children: [
      {
        path: 'ad',
        component: () => import('@/views/tenement/ad'),
        name: 'ad',
        meta: {
          perms: ['admin:ad:list', 'admin:ad:create', 'admin:ad:update', 'admin:ad:delete'],
          title: '广告管理',
          noCache: true
        }
      },
      {
        path: 'qq',
        component: () => import('@/views/tenement/qq'),
        name: 'qq',
        meta: {
          perms: ['admin:qq:list', 'admin:qq:create', "admin:qq:delete", "admin:qq:update"],
          title: 'QQ群推广',
          noCache: true
        }
      },
      {
        path: 'apply',
        component: () => import('@/views/tenement/apply'),
        name: 'apply',
        meta: {
          perms: ['admin:apply:list', 'admin:apply:handle'],
          title: '联校申请',
          noCache: true
        }
      },
      {
        path: 'topic',
        component: () => import('@/views/tenement/topic'),
        name: 'topic',
        meta: {
          perms: ['admin:article:list', 'admin:article:create', 'admin:article:update', 'admin:article:delete', 'admin:article:status'],
          title: '文章管理',
          noCache: true
        }
      },
      {
        path: 'life',
        component: () => import('@/views/tenement/life'),
        name: 'life',
        meta: {
          perms: ['admin:life:list'],
          title: '大学生活',
          noCache: true
        }
      },
      // {
      //   path: 'merch',
      //   component: () => import('@/views/tenement/merch'),
      //   name: 'merch',
      //   meta: {
      //     perms: ['admin:merch:list'],
      //     title: '商户管理',
      //     noCache: true
      //   }
      // },
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
