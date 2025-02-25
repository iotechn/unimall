<template>
	<div class="wscn-http404-container">
		<div class="wscn-http404">
			<div class="bullshit">
				<div class="bullshit__oops">401</div>
				<div class="bullshit__headline">{{message}}</div>
				<div class="bullshit__info">
					<p>1、请检查您输入的URL是否正确</p>
					<p>2、单击下面按钮返回主页</p>
				</div>
				<a href="" class="bullshit__return-home">回到主页</a>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
  .wscn-http404-container {
    position: absolute;
    left: 50%;
    top: 40%;
    transform: translate(-50%, -50%);
  }
  .wscn-http404 {
    overflow: hidden;
    position: relative;
    margin-top: 200px;
    padding: 0 50px;
    width: 1200px;
    .bullshit {
      overflow: hidden;
      position: relative;
      margin:0 auto;
      padding: 30px 0;
      width: 300px;
      &__oops {
        margin-bottom: 20px;
        opacity: 0;
        line-height: 40px;
        font-weight: bold;
        font-size: 32px;
        color: #1482f0;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-fill-mode: forwards;
      }
      &__headline {
        margin-bottom: 10px;
        opacity: 0;
        line-height: 24px;
        font-weight: bold;
        font-size: 20px;
        color: #222;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-delay: 0.1s;
        animation-fill-mode: forwards;
      }
      &__info {
        margin-bottom: 30px;
        opacity: 0;
        line-height: 21px;
        font-size: 13px;
        color: grey;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-delay: 0.2s;
        animation-fill-mode: forwards;
      }
      &__return-home {
        display: block;
        float: left;
        border-radius: 100px;
        width: 110px;
        height: 36px;
        background: #1482f0;
        opacity: 0;
        cursor: pointer;
        line-height: 36px;
        text-align: center;
        font-size: 14px;
        color: #fff;
        animation-name: slideUp;
        animation-duration: 0.5s;
        animation-delay: 0.3s;
        animation-fill-mode: forwards;
      }
      @keyframes slideUp {
        0% {
          opacity: 0;
          transform: translateY(60px);
        }
        100% {
          opacity: 1;
          transform: translateY(0);
        }
      }
    }
  }
</style>

<script setup>
import { computed } from "vue";
const message = computed(() => {
	return "您暂无权限查看该页面！";
});
</script>