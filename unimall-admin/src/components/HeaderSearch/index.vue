<template>
	<div :class="{ show: isShow }" class="header-search">
		<svg-icon
			id="guide-search"
			class-name="search-icon"
			icon="search"
			@click.stop="onShowClick"
		/>
		<el-select
			ref="headerSearchSelectRef"
			v-model="search"
			class="header-search-select"
			filterable
			default-first-option
			remote
			placeholder="请输入页面（路由）名字"
			:remote-method="querySearch"
			@change="onSelectChange"
		>
			<el-option
				v-for="option in searchOptions"
				:key="option.item.path"
				:label="option.item.title.join(' > ')"
				:value="option.item"
			></el-option>
		</el-select>
	</div>
</template>

<style lang="scss" scoped>
.header-search {
  font-size: 0 !important;
  .search-icon {
    cursor: pointer;
    vertical-align: middle;
    font-size: 18px;
  }
  .header-search-select {
    display: inline-block;
    overflow: hidden;
    border-radius: 0;
    width: 0;
    background: transparent;
    vertical-align: middle;
    font-size: 18px;
    transition: width 0.2s;
    ::v-deep .el-input__inner {
      padding-left: 0;
      padding-right: 0;
      border: 0;
      border-bottom: 1px solid #d9d9d9;
      border-radius: 0;
      box-shadow: none !important;
      vertical-align: middle;
    }
  }
  &.show {
    .header-search-select {
      margin-left: 10px;
      width: 210px;
    }
  }
}
</style>

<script setup>
import { computed, ref, watch } from "vue";
import { generateRoutes } from "./FuseData";
import Fuse from "fuse.js";
import { filterRouters } from "@/utils/route";
import { useRouter } from "vue-router";

// 控制 search 显示
const isShow = ref(false);
// el-select 实例
const headerSearchSelectRef = ref(null);
const onShowClick = () => {
	isShow.value = !isShow.value;
	headerSearchSelectRef.value.focus();
};

// search 相关
const search = ref("");
// 搜索结果
const searchOptions = ref([]);
// 搜索方法
const querySearch = query => {
	if (query !== "") {
		searchOptions.value = fuse.search(query);
	} else {
		searchOptions.value = [];
	}
};
// 选中回调
const onSelectChange = val => {
	router.push(val.path);
	onClose();
};

// 检索数据源
const router = useRouter();
const searchPool = computed(() => {
	const filterRoutes = filterRouters(router.getRoutes());
	console.log("f", filterRoutes);
	return generateRoutes(filterRoutes);
});
console.log("s", searchPool);
/**
 * 搜索库相关
 */
let fuse;
const initFuse = searchPool => {
	fuse = new Fuse(searchPool, {
		// 是否按优先级进行排序
		shouldSort: true,
		// 匹配算法放弃的时机， 阈值 0.0 需要完美匹配（字母和位置），阈值 1.0 将匹配任何内容。
		threshold: 0.4,
		// 匹配长度超过这个值的才会被认为是匹配的
		minMatchCharLength: 1,
		// 将被搜索的键列表。 这支持嵌套路径、加权搜索、在字符串和对象数组中搜索。
		// name：搜索的键
		// weight：对应的权重
		keys: [
			{
				name: "title",
				weight: 0.7
			},
			{
				name: "path",
				weight: 0.3
			}
		]
	});
};
initFuse(searchPool.value);

/**
 * 关闭 search 的处理事件
 */
const onClose = () => {
	headerSearchSelectRef.value.blur();
	isShow.value = false;
	searchOptions.value = [];
};
/**
 * 监听 search 打开，处理 close 事件
 */
watch(isShow, val => {
	if (val) {
		document.body.addEventListener("click", onClose);
	} else {
		document.body.removeEventListener("click", onClose);
	}
});
</script>