<template>
	<div class="content">
		<div class="stars">
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
			<div class="star"></div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@mixin sp-layout {
  @media screen and (width <= 750px) {
    @content;
  }
}
.content {
  display: flex;
  overflow: hidden;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  //background: radial-gradient(ellipse at bottom, #0d1d31 0%, #0c0d13 100%);
  background-image: url("../../assets/star.jpg");
  background-size: cover;
}
@function random_range($min, $max) {
  $rand: random();
  $random_range: $min + floor($rand * (($max - $min) + 1));
  @return $random_range;
}
.stars {
  position: fixed;
  left:400px;
  top: -300px;
  width: 100%;
  height: 120%;
  transform: rotate(-45deg);
}
.star {
  $star-count: 50;
  --star-color: var(--primary-color);
  --star-tail-length: 6em;
  --star-tail-height: 2px;
  --star-width: calc(var(--star-tail-length) / 6);
  --fall-duration: 9s;
  --tail-fade-duration: var(--fall-duration);
  position: absolute;
  left: 0;
  top: var(--top-offset);
  border-radius: 50%;
  width: var(--star-tail-length);
  height: var(--star-tail-height);
  background:white;
  filter: drop-shadow(0 0 6px currentcolor);
  color: var(--star-color);
  transform: translate3d(104em, 0, 0);
  animation: fall var(--fall-duration) var(--fall-delay) linear infinite, tail-fade var(--tail-fade-duration) var(--fall-delay) ease-out infinite;
  @include sp-layout {
    animation: fall var(--fall-duration) var(--fall-delay) linear infinite;
  }
  @for $i from 1 through $star-count {
    &:nth-child(#{$i}) {
      --star-tail-length: #{random_range(500em, 750em) / 100};
      --top-offset: #{random_range(0vh, 10000vh) / 100};
      --fall-duration: #{random_range(6000, 12000s) / 1000};
      --fall-delay: #{random_range(0, 10000s) / 1000};
    }
  }
  &::before, &::after {
    position: absolute;
    left: calc(var(--star-width) / -2);
    top: 0;
    border-radius: inherit;
    width: var(--star-width);
    height: 100%;
    background:white;
    content: '';
    animation: blink 2s linear infinite;
  }
  &::before {
    transform: rotate(45deg);
  }
  &::after {
    transform: rotate(-45deg);
  }
}
@keyframes fall {
  to {
    transform: translate3d(-30em, 0, 0);
  }
}
@keyframes tail-fade {
  0%, 50% {
    width: var(--star-tail-length);
    opacity: 1;
  }
  70%, 80% {
    width: 0;
    opacity: 0.4;
  }
  100% {
    width: 0;
    opacity: 0;
  }
}
@keyframes blink {
  50% {
    opacity: 0.6;
  }
}
</style>
<script setup>

</script>