import router from './router';
import store from './store';
import { ElMessage } from 'element-plus';
import NProgress from 'nprogress'; // progress bar
import 'nprogress/nprogress.css';// progress bar style
import { getToken } from '@/utils/auth'; // getToken from cookie

NProgress.configure({ showSpinner: false });// NProgress Configuration

// permission judge function
function hasPermission(perms, permissions) {
  if (perms.indexOf('*') >= 0) return true; // admin permission passed directly
  if (!permissions) return true;
  return perms.some(perm => permissions.indexOf(perm) >= 0);
}

const whiteList = ['/login', '/auth-redirect'];// no redirect whitelist

router.beforeEach(async (to, from, next) => {
  NProgress.start(); // start progress bar
  const token = getToken();
console.log(token,"1213233");
  if (token) {
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' });
      NProgress.done(); // if current page is dashboard will not trigger	afterEach hook, so manually handle it
    } else {
      if (store.getters.perms.length === 0) {
        try {
          const res = await store.dispatch('GetUserInfo');
          const perms = res.data.data.perms; // note: perms must be a array! such as: ['GET /aaa','POST /bbb']
          await store.dispatch('GenerateRoutes', { perms });

          // 使用 addRoute 动态添加路由
          const addRouters = store.getters.addRouters;
					console.log(addRouters,"q111");

          addRouters.forEach(route => {
            router.addRoute(route);
          });


          next({ ...to, replace: true }); // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
        } catch (err) {
          await store.dispatch('FedLogOut');
          ElMessage.error(err || 'Verification failed, please login again');
          next({ path: '/' });
        }
      } else {
        // 没有动态改变权限的需求可直接next() 删除下方权限判断 ↓
        if (hasPermission(store.getters.perms, to.meta.perms)) {
          next();
        } else {
          next({ path: '/401', replace: true, query: { noGoBack: true } });
        }
        // 可删 ↑
      }
    }
  } else {
    /* has no token*/
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    } else {
      next(`/login?redirect=${to.path}`);
      NProgress.done();
    }
  }
});

router.afterEach(() => {
  NProgress.done(); // finish progress bar
});