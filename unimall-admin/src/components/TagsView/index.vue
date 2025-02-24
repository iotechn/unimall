<template>
	<div class="tags-view-container">
		<el-scrollbar class="tags-view-wrapper">
			<router-link
				v-for="(tag, index) in $store.getters.tagsViewList"
				:key="tag.fullPath"
				class="tags-view-item"
				:class="isActive(tag) ? 'active' : ''"
				:style="{
					backgroundColor: isActive(tag) ? $store.getters.cssVar.menuBg : '',
					borderColor: isActive(tag) ? $store.getters.cssVar.menuBg : ''
				}"
				:to="{ path: tag.fullPath }"

				@contextmenu.prevent="openMenu($event, index)"
			>
				{{tag.title}}
				<template v-if="!isAffiix(tag)">
					<Close class="el-icon-close" @click.prevent.stop="onCloseClick(index,tag)" />
				</template>
			</router-link>
		</el-scrollbar>
		<context-menu
			v-show="visible"
			:style="menuStyle"
			:index="selectIndex"
		></context-menu>
	</div>
</template>

<style lang="scss" scoped>
.tags-view-container {
  border-bottom: 1px solid #d8dce5;
  width: 100%;
  height: 34px;
  background: #fff;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
  .tags-view-item {
    display: inline-block;
    position: relative;
    margin-left: 5px;
    margin-top: 4px;
    padding: 0 8px;
    border: 1px solid #d8dce5;
    height: 26px;
    background: #fff;
    cursor: pointer;
    line-height: 26px;
    font-size: 12px;
    color: #495060;
    &:first-of-type {
      margin-left: 15px;
    }
    &:last-of-type {
      margin-right: 15px;
    }
    &.active {
      color: #fff;
      &::before {
        display: inline-block;
        position: relative;
        margin-right: 4px;
        border-radius: 50%;
        width: 8px;
        height: 8px;
        background: #fff;
        content: '';
      }
    }

    // close 按钮
    .el-icon-close {
      border-radius: 50%;
      width: 1em;
      height: 1em;
      line-height: 10px;
      vertical-align: -2px;
      text-align: center;
      transform-origin: 100% 50%;
      transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
      &::before {
        display: inline-block;
        vertical-align: -3px;
        transform: scale(0.6);
      }
      &:hover {
        background-color: #b4bccc;
        color: #fff;
      }
    }

  }
}
</style>

<script setup>
import { Close } from "@element-plus/icons";

import ContextMenu from "./ContextMenu.vue";
import { ref, reactive, watch, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
// import store from '@/store'

const route = useRoute();

onMounted(() => {
	console.log(store.getters.cssVar);
});

/**
 * 是否被选中
 */
const isActive = tag => {
	return tag.path === route.path;
};
const isAffiix = tag => {
	return tag.meta && tag.meta.affix;
};
// contextMenu 相关
const selectIndex = ref(0);
const visible = ref(false);
const menuStyle = reactive({
	left: 0,
	top: 0
});
/**
 * 展示 menu
 */
const openMenu = (e, index) => {
	const { x, y } = e;
	menuStyle.left = x + "px";
	menuStyle.top = y + "px";
	selectIndex.value = index;
	visible.value = true;
};

/**
 * 关闭 tag 的点击事件
 */
const store = useStore();
const router = useRouter();

const onCloseClick = (index, tag) => {
	store.commit("app/removeTagsView", {
		type: "index",
		index
	});

	// 如果删除的是当前页面，自动定位到上一个页面
	if (isActive(tag)) {
		const tagsViewList = store.getters.tagsViewList;
		if (index == 0 && tagsViewList.length >= 1) {
			const pre_index = 0;
			const pre_page = tagsViewList[pre_index];
			router.push(pre_page.fullPath);
		} else if (tagsViewList.length == 0) { // 如果是最后一个，定位到首页
			router.push("/");
		} else {
			const pre_index = index - 1;
			const pre_page = tagsViewList[pre_index];
			router.push(pre_page.fullPath);
		}
	}
};

/**
 * 关闭 menu
 */
const closeMenu = () => {
	visible.value = false;
};

/**
 * 监听变化
 */
watch(visible, val => {
	if (val) {
		document.body.addEventListener("click", closeMenu);
	} else {
		document.body.removeEventListener("click", closeMenu);
	}
},
{
	immediate: true
});

</script>