const whiteList = ["/login", "/import", "/404", "/401"];

/**
 * path 是否需要被缓存 ,404这些界面都不需要被保存
 * @param {*} path
 * @returns
 */
export function isTags(path) {
	return !whiteList.includes(path);
}