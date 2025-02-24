<template>
  <el-color-picker
    v-model="theme"
    class="theme-picker"
    popper-class="theme-picker-dropdown"
  />
</template>

<script setup>
import { ref, watch } from "vue";
import { ElMessage } from "element-plus"; // 假设使用 ElementPlus
import pkg from "element-plus/package.json"; // 获取 ElementPlus 版本

const version = pkg.version;
const ORIGINAL_THEME = "#409EFF"; // default color

// 定义响应式数据
const chalk = ref(""); // content of theme-chalk css
const theme = ref(ORIGINAL_THEME);

// 监听 theme 的变化
watch(theme, (val, oldVal) => {
  if (typeof val !== "string") return;
  const themeCluster = getThemeCluster(val.replace("#", ""));
  const originalCluster = getThemeCluster(oldVal.replace("#", ""));
  console.log(themeCluster, originalCluster);

  const getHandler = (variable, id) => {
    return () => {
      const originalCluster = getThemeCluster(ORIGINAL_THEME.replace("#", ""));
      const newStyle = updateStyle(chalk.value, originalCluster, themeCluster);

      let styleTag = document.getElementById(id);
      if (!styleTag) {
        styleTag = document.createElement("style");
        styleTag.setAttribute("id", id);
        document.head.appendChild(styleTag);
      }
      styleTag.innerText = newStyle;
    };
  };

  const chalkHandler = getHandler("chalk", "chalk-style");

  if (!chalk.value) {
    const url = `https://unpkg.com/element-plus@${version}/dist/index.css`; // ElementPlus 样式文件路径
    getCSSString(url, chalkHandler, "chalk");
  } else {
    chalkHandler();
  }

  const styles = Array.from(document.querySelectorAll("style")).filter(
    (style) => {
      const text = style.innerText;
      return (
        new RegExp(oldVal, "i").test(text) && !/Chalk Variables/.test(text)
      );
    }
  );
  styles.forEach((style) => {
    const { innerText } = style;
    if (typeof innerText !== "string") return;
    style.innerText = updateStyle(innerText, originalCluster, themeCluster);
  });

  ElMessage({
    message: "换肤成功",
    type: "success",
  });
});

// 更新样式
const updateStyle = (style, oldCluster, newCluster) => {
  let newStyle = style;
  oldCluster.forEach((color, index) => {
    newStyle = newStyle.replace(new RegExp(color, "ig"), newCluster[index]);
  });
  return newStyle;
};

// 获取 CSS 字符串
const getCSSString = (url, callback, variable) => {
  const xhr = new XMLHttpRequest();
  xhr.onreadystatechange = () => {
    if (xhr.readyState === 4 && xhr.status === 200) {
      chalk.value = xhr.responseText.replace(/@font-face{[^}]+}/, "");
      callback();
    }
  };
  xhr.open("GET", url);
  xhr.send();
};

// 获取主题色集群
const getThemeCluster = (theme) => {
  const tintColor = (color, tint) => {
    let red = parseInt(color.slice(0, 2), 16);
    let green = parseInt(color.slice(2, 4), 16);
    let blue = parseInt(color.slice(4, 6), 16);

    if (tint === 0) {
      return [red, green, blue].join(",");
    } else {
      red += Math.round(tint * (255 - red));
      green += Math.round(tint * (255 - green));
      blue += Math.round(tint * (255 - blue));

      red = red.toString(16);
      green = green.toString(16);
      blue = blue.toString(16);

      return `#${red}${green}${blue}`;
    }
  };

  const shadeColor = (color, shade) => {
    let red = parseInt(color.slice(0, 2), 16);
    let green = parseInt(color.slice(2, 4), 16);
    let blue = parseInt(color.slice(4, 6), 16);

    red = Math.round((1 - shade) * red);
    green = Math.round((1 - shade) * green);
    blue = Math.round((1 - shade) * blue);

    red = red.toString(16);
    green = green.toString(16);
    blue = blue.toString(16);

    return `#${red}${green}${blue}`;
  };

  const clusters = [theme];
  for (let i = 0; i <= 9; i++) {
    clusters.push(tintColor(theme, Number((i / 10).toFixed(2))));
  }
  clusters.push(shadeColor(theme, 0.1));
  return clusters;
};
</script>

<style>
.theme-picker .el-color-picker__trigger {
  vertical-align: middle;
}

.theme-picker-dropdown .el-color-dropdown__link-btn {
  display: none;
}
</style>
