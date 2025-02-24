import path from "path-browserify";

/**
 * 筛选出可供搜索的路由对象
 * @param routes 路由表
 * @param basePath 基础路径，默认为 /
 * @param prefixTitle
 */
export const generateRoutes = (routes, basePath = "/", prefixTitle = []) => {
	// 创建 result 数据
	let res = [];
	// 循环 routes 路由
	for (const route of routes) {
		// 创建包含 path 和 title 的 item
		const data = {
			path: path.resolve(basePath, route.path),
			title: [...prefixTitle]
		};
		// 动态路由不允许被搜索
		// 匹配动态路由的正则
		// 不显示在左侧菜单栏的路由要过滤掉
		const re = /.*\/:.*/;
		if (
			route.meta
        && route.meta.title
        && !route.hidden
        && !re.exec(route.path)
        && !res.find(item => item.path === data.path)
		) {
			data.title = [...data.title, route.meta.title];
			res.push(data);
		}

		// 存在 children 时，迭代调用
		if (route.children) {
			const tempRoutes = generateRoutes(route.children, data.path, data.title);
			if (tempRoutes.length >= 1) {
				res = [...res, ...tempRoutes];
			}
		}
	}
	return res;
};