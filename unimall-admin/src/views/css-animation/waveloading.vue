<template>
	<div class="c">
		<div class="loading">
			<div class="dot"></div>
			<div class="dot"></div>
			<div class="dot"></div>
			<div class="dot"></div>
			<div class="dot"></div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
.c {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80vh;
  background: #222;
}
.loading {
  $colors: #7ef9ff, #89cff0, #4682b4, #0f52ba, #000080;
  display: flex;
  .dot {
    position: relative;
    margin: 0.8em;
    border-radius: 50%;
    width: 2em;
    height: 2em;
    &::before {
      position: absolute;
      border-radius: inherit;
      width: 100%;
      height: 100%;
      background: inherit;
      content: "";
      animation: wave 2s ease-out infinite;
    }
    @for $i from 1 through 5 {
      &:nth-child(#{$i}) {
        background: nth($colors, $i);
        &::before {
          animation-delay: $i * 0.2s;
        }
      }
    }
  }
}
@keyframes wave {
  50%,
  75% {
    transform: scale(2.5);
  }
  80%,
  100% {
    opacity: 0;
  }
}

</style>

<script>
export default {
	name: "waveloading"
};
</script>