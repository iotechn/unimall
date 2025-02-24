<template>
	<el-dialog
		:title="title"
		:model-value="modelValue"
		width="30%"
		align-center
		draggable
		@close="onClose"
	>
		<el-upload
			ref="uploadRef"
			:limit="1"
			accept=".xlsx, .xls"
			:show-file-list="false"
			:action="url"
			:on-success="handleFileSuccess"
			:before-upload="handleFileBefore"
			drag
		>
			<el-icon class="el-icon--upload">
				<upload-filled />
			</el-icon>
			<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			<template #tip>
				<div class="el-upload__tip text-center">
					<span>仅允许导入xls、xlsx格式文件。</span>
					<el-link
						type="primary" :underline="false" style="vertical-align: baseline;font-size:12px;"
						@click="importTemplate"
					>
						下载模板
					</el-link>
				</div>
			</template>
		</el-upload>
	</el-dialog>
</template>

<style lang="scss" scoped></style>

<script setup>
import { defineProps, defineEmits, ref, watch, nextTick } from "vue";
import { ElMessageBox, ElMessage } from "element-plus";

const props = defineProps({
	modelValue: {
		type: Boolean,
		required: true
	},
	title: {
		type: String,
		default: "文件上传"
	},
	url: {
		type: String,
		default: ""
	}
});
const emits = defineEmits(["update:modelValue", "onSuccess", "onDownTemplate"]);

/** 文件上传成功处理 */
const handleFileSuccess = (res, file) => {
	console.log("handleFileSuccess==", file);
	if (res.status.error_code == 0) {
		emits("onSuccess");
	} else {
		ElMessage.error(res.status.error_msg);
	}
};
/** 文件上传之前 */
const handleFileBefore = (file) => {
	console.log("handleFileBefore==", file);
};
/** 下载模板 */
const importTemplate = () => {
	emits("onDownTemplate");
};

/**
 * 关闭
 */
const onClose = () => {
	emits("update:modelValue", false);
};
</script>