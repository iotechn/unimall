import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// import path from 'path-browserify'
import path from "path";
console.log(path,"11111111111111111")
import { viteMockServe } from "vite-plugin-mock";
import vitePluginGitInfo from "./src/plugins/vite-plugin-git-info.js";

// svg-icon插件
import { createSvgIconsPlugin } from "vite-plugin-svg-icons";
export default defineConfig({
	base: "./", // 打包路径s
	plugins: [
		vue(),
		createSvgIconsPlugin({
			// 指定要缓存的图标文件夹
			iconDirs: [path.resolve("./src/icons/svg")],
			// 执行icon name的格式
			symbolId: "icon-[name]"
		}),
		viteMockServe({
			enable: false,
			logger: true,
			mockPath: "./src/mock/",
			supportTs: false

		}),
		vitePluginGitInfo()

	],
	css: {
    preprocessorOptions: {
      scss: {
        // additionalData: `@use "@/styles/variables.scss";` // 全局引入变量文件
      }
    }
  },
	resolve: {
		alias: {
			"@": path.resolve("./src")
		},
		extensions: [".mjs", ".js", ".ts", ".jsx", ".tsx", ".json", ".vue"]
	},
	server: {
		cors: true, // 允许跨域
		host: "0.0.0.0",
		open: true, // 服务启动时是否自动打开浏览器
		port: 9999, // 服务端口号
		proxy: {
			"/api": {
				changeOrigin: true,
				rewrite: (path) => path.replace(/^\/api/, ""),
				target: "http://127.0.0.1:9999/"
				// target: "http://localhost:8080/"

			}
		}

	}

});