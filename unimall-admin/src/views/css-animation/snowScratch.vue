<template>
	<div class="c">
		<p>程序员三千</p>
		<div class="card">
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
			<div class="snow"></div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css?family=Lato");
.c {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: black;
  text-align: center;
}
.card {
  $snow-size: 48px;
  display: flex;
  flex-wrap: wrap;
  padding: 10em;
  width: 880px;
  height: 880px;
  background: black;
  filter: blur(15px) contrast(30);
.snow {
  position: relative;
  border-radius: 100%;
  width: $snow-size;
  height: $snow-size;
&::after {
   position: absolute;
   left: 0;
   width: inherit;
   height: inherit;
   background: white;
   content: "";
   transition: 30s cubic-bezier(0.19, 1, 0.22, 1);
 }
&:hover::after {
   transform: scale(0);
   transition-duration: 0.3s;
 }
}
}
p {
  position: absolute;
  left: 50%;
  top: 50%;
  z-index: 9999;
  filter: blur(1px) contrast(20);
  pointer-events: none;
  line-height: 1em;
  font-family: Lato, sans-serif;
  font-size: 4em;
  color: white;
  letter-spacing: 0.1em;
  transform: translate(-47%, -160%);
}

</style>

<script>
export default {
	name: "snow-scratch"
};
</script>