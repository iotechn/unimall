<template>
	<div>
		<el-tooltip content="功能引导">
			<svg-icon id="guide-start" icon="guide" @click="onClick" />
		</el-tooltip>
	</div>
</template>

<style scoped></style>

<script setup>
import Driver from "driver.js";
import "driver.js/dist/driver.min.css";
import { onMounted } from "vue";
import step from "./step";

let driver = null;
onMounted(() => {
	driver = new Driver({
		// 禁止点击蒙版关闭
		allowClose: false,
		animate: false,
		opacity: 0.75,
		padding: 10,
		overlayClickNext: false,
		doneBtnText: "完成",
		stageBackground: "#ffffff",
		closeBtnText: "关闭",
		nextBtnText: "下一个",
		prevBtnText: "上一个"
	});
});

const onClick = () => {
	driver.defineSteps(step);
	driver.start();
};
</script>