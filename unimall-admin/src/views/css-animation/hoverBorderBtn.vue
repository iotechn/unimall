<template>
	<div class="c">
		<button
			data-text="嘿嘿嘿"
			class="btn btn-primary btn-ghost btn-border-stroke  btn-text-float-up"
		>
			<div class="btn-borders">
				<div class="border-top"></div>
				<div class="border-right"></div>
				<div class="border-bottom"></div>
				<div class="border-left"></div>
			</div>
			<span class="btn-text">hover me</span>
		</button>
	</div>
</template>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css?family=Lato");
.c {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 85vh;
  background: #1A1E23;
  text-align: center;
}
.btn {
  --hue: 190;
  --ease-in-duration: 0.25s;
  --ease-in-exponential: cubic-bezier(0.95, 0.05, 0.795, 0.035);
  --ease-out-duration: 0.65s;
  --ease-out-delay: var(--ease-in-duration);
  --ease-out-exponential: cubic-bezier(0.19, 1, 0.22, 1);
  overflow: hidden;
  position: relative;
  padding: 1rem 3rem;
  border: 1px solid hsl(var(--hue), 100%, 41%);
  outline: transparent;
  background-color: hsl(var(--hue), 100%, 41%);
  cursor: pointer;
  user-select: none;
  line-height: 1.5;
  text-decoration: none;
  font-size: 1rem;
  color: white;
  white-space: nowrap;
  transition: 0.25s;
  &:hover {
    background: hsl(var(--hue), 100%, 31%);
  }
  &-primary {
    --hue: 171;
  }
  &-ghost {
    border-color: hsl(var(--hue), 100%, 41%);
    background-color: transparent;
    color: hsl(var(--hue), 100%, 41%);
    &:hover {
      color: white;
    }
  }
  &-border-stroke {
    border-color: hsla(var(--hue), 100%, 41%, 0.35);
    .btn-borders {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      .border-top {
        position: absolute;
        top: 0;
        width: 100%;
        height: 1px;
        background: hsl(var(--hue), 100%, 41%);
        transform: scaleX(0);
        transform-origin: left;
      }
      .border-right {
        position: absolute;
        right: 0;
        width: 1px;
        height: 100%;
        background: hsl(var(--hue), 100%, 41%);
        transform: scaleY(0);
        transform-origin: bottom;
      }
      .border-bottom {
        position: absolute;
        bottom: 0;
        width: 100%;
        height: 1px;
        background: hsl(var(--hue), 100%, 41%);
        transform: scaleX(0);
        transform-origin: left;
      }
      .border-left {
        position: absolute;
        left: 0;
        width: 1px;
        height: 100%;
        background: hsl(var(--hue), 100%, 41%);
        transform: scaleY(0);
        transform-origin: bottom;
      }

      // when unhover, ease-out left, bottom; ease-in right, top
      .border-left {
        transition: var(--ease-out-duration) var(--ease-out-delay)
        var(--ease-out-exponential);
      }
      .border-bottom {
        transition: var(--ease-out-duration) var(--ease-out-delay)
        var(--ease-out-exponential);
      }
      .border-right {
        transition: var(--ease-in-duration) var(--ease-in-exponential);
      }
      .border-top {
        transition: var(--ease-in-duration) var(--ease-in-exponential);
      }
    }
    &:hover {
      background: transparent;
      color: hsl(var(--hue), 100%, 41%);
      .border-top,
      .border-bottom {
        transform: scaleX(1);
      }
      .border-left,
      .border-right {
        transform: scaleY(1);
      }

      // when hover, ease-in left, bottom; ease-out right, top
      .border-left {
        transition: var(--ease-in-duration) var(--ease-in-exponential);
      }
      .border-bottom {
        transition: var(--ease-in-duration) var(--ease-in-exponential);
      }
      .border-right {
        transition: var(--ease-out-duration) var(--ease-out-delay)
        var(--ease-out-exponential);
      }
      .border-top {
        transition: var(--ease-out-duration) var(--ease-out-delay)
        var(--ease-out-exponential);
      }
    }
  }
  &-text-float-up {
    &::after {
      display: flex;
      position: absolute;
      left: 0;
      top: 0;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 100%;
      opacity: 0;
      content: attr(data-text);
      transform: translateY(35%);
      transition: 0.25s ease-out;
    }

    // when hover, ease-in top-text; ease-out bottom-text
    .btn-text {
      display: block;
      transition: 0.75s 0.1s var(--ease-out-exponential);
    }
    &:hover {
      // when hover, ease-in bottom-text; ease-out top-text
      .btn-text {
        opacity: 0;
        transform: translateY(-25%);
        transition: 0.25s ease-out;
      }
      &::after {
        opacity: 1;
        transform: translateY(0);
        transition: 0.75s 0.1s var(--ease-out-exponential);
      }
    }
  }
}

</style>

<script>
export default {
	name: "page-transition"
};
</script>