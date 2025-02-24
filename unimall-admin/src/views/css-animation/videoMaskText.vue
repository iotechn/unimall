<template>
	<div class="c">
		<video autoplay muted loop preload poster="https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/oceanshot.jpg">
			<source src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/ocean-small.webm" />
			<source src="http://thenewcode.com/assets/videos/ocean-small.mp4" />
		</video>
		<h1>ocean</h1>
	</div>
</template>

<style lang="scss" scoped>
@font-face {
  src: url("https://s3-us-west-2.amazonaws.com/s.cdpn.io/4273/biko-black.woff");
  font-family: Biko;
}
.c {
  display: flex;
  overflow: hidden;
  justify-content: center;
  align-items: center;
  height: 100vh;
}
video,
h1 {
  position: absolute;
  left: 0;
  top: 0;
  margin: 0;
  width: 100%;
  height: 100%;
}
video {
  object-fit: cover;
}
h1 {
  background: white;
  mix-blend-mode: screen;
  line-height: 100vh;
  text-align: center;
  text-transform: uppercase;
  font-family: Biko, sans-serif;
  font-weight: 700;
  font-size: 20vw;
}

</style>

<script>
export default {
	name: "vide-mask-text"
};
</script>