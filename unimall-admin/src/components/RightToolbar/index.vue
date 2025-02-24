<template>
	<div class="c-toolbar">
		<el-row :gutter="10">
			<el-col v-if="haveAdd" :span="1.5">
				<el-button
					type="primary"
					plain
					icon="Plus"
					@click="handleAdd"
				>
					新增
				</el-button>
			</el-col>

			<el-col v-if="haveImport" :span="1.5">
				<el-button
					type="info"
					plain
					icon="Upload"
					@click="handleImport"
				>
					导入
				</el-button>
			</el-col>
			<el-col v-if="haveOut" :span="1.5">
				<el-button
					type="warning"
					plain
					icon="Download"
					@click="handleOut"
				>
					导出
				</el-button>
			</el-col>
		</el-row>

		<div class="top-right-btn" :style="style">
			<el-row>
				<el-tooltip v-if="search" class="item" effect="dark" :content="showSearch ? '隐藏搜索' : '显示搜索'" placement="top">
					<el-button circle icon="Search" @click="toggleSearch()" />
				</el-tooltip>
				<el-tooltip class="item" effect="dark" content="刷新" placement="top">
					<el-button circle icon="Refresh" @click="refresh()" />
				</el-tooltip>
				<el-tooltip v-if="columns" class="item" effect="dark" content="显隐列" placement="top">
					<el-button circle icon="Menu" @click="showColumn()" />
				</el-tooltip>
			</el-row>
			<el-dialog v-model="open" :title="title" append-to-body>
				<el-transfer
					v-model="value"
					:titles="['显示', '隐藏']"
					:data="columns"
					@change="dataChange"
				></el-transfer>
			</el-dialog>
		</div>
	</div>
</template>

<style lang='scss' scoped>

.c-toolbar {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}
:deep(.el-transfer__button) {
  display: block;
  margin-left: 0;
  border-radius: 50%;
}
:deep(.el-transfer__button:first-child) {
  margin-bottom: 10px;
}
.my-el-transfer {
  text-align: center;
}
</style>

<script setup>

import { ref, computed } from "vue";

const props = defineProps({
	showSearch: {
		type: Boolean,
		default: true
	},
	columns: {
		type: Array
	},
	search: {
		type: Boolean,
		default: true
	},
	gutter: {
		type: Number,
		default: 10
	},
	haveAdd: {
		type: Boolean,
		default: true
	},
	haveImport: {
		type: Boolean,
		default: true
	},
	haveOut: {
		type: Boolean,
		default: true
	}
});

const emits = defineEmits(["update:showSearch", "queryTable", "onImportClick", "onOutClick", "onAddClick"]);

// 显隐数据
const value = ref([]);
// 弹出层标题
const title = ref("显示/隐藏");
// 是否显示弹出层
const open = ref(false);

const style = computed(() => {
	const ret = {};
	if (props.gutter) {
		ret.marginRight = `${props.gutter / 2}px`;
	}
	return ret;
});

// 搜索
function toggleSearch() {
	emits("update:showSearch", !props.showSearch);
}

// 刷新
function refresh() {
	emits("queryTable");
}

// 右侧列表元素变化
function dataChange(data) {
	for (const item in props.columns) {
		const key = props.columns[item].key;
		props.columns[item].visible = !data.includes(key);
	}
}

// 打开显隐列dialog
function showColumn() {
	open.value = true;
}

// 显隐列初始默认隐藏列
for (const item in props.columns) {
	if (props.columns[item].visible === false) {
		value.value.push(parseInt(item));
	}
}
const handleAdd = () => {
	emits("onAddClick");
};
const handleImport = () => {
	emits("onImportClick");
};
const handleOut = () => {
	emits("onOutClick");
};
</script>