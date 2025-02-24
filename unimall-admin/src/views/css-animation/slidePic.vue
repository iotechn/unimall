<template>
	<div class="c">
		<main class="container">
			<section id="slide1" class="slide sticky">
				<h1>鼠标滑轮向下滚动</h1>
			</section>
			<section id="slide2" class="slide sticky">
				<h1>鼠标滑轮向下（上）滚动</h1>
			</section>
			<section id="slide3" class="slide sticky">
				<h1>鼠标滑轮向下（上）滚动</h1>
			</section>
			<section id="slide4" class="slide sticky">
				<h1>End</h1>
			</section>
		</main>
	</div>
</template>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css?family=Lora:400,400i,700");
.c {
  margin: 0;
  padding: 0;
}
.container {
  overflow-x: hidden;
  height: 80vh;
  font-family: Lora, serif;
}
.slide {
  display: flex;
  position: sticky;
  top: 0;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-position: center;
  background-size: cover;
  color: white;
  letter-spacing: 0.2em;
  h1 {
    text-shadow: 0 2px 2px black;
    font-size: 250%;
  }
}
#slide1 {
  background-image: url("https://i.loli.net/2019/11/23/cnKl1Ykd5rZCVwm.jpg");
}
#slide2 {
  background-image: url("https://i.loli.net/2019/10/18/uXF1Kx7lzELB6wf.jpg");
}
#slide3 {
  background-image: url("https://i.loli.net/2019/11/16/FLnzi5Kq4tkRZSm.jpg");
}
#slide4 {
  background-image: url("https://i.loli.net/2019/10/18/buDT4YS6zUMfHst.jpg");
}

</style>

<script>
export default {
	name: "slide-pic"
};
</script>