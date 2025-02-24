<template>
	<div class="c">
		<ul>
			<li><a href="#">home</a></li>
			<li><a href="#">product</a></li>
			<li><a href="#">order</a></li>
			<li><a href="#">categories</a></li>
			<li><a href="#">about</a></li>
		</ul>
	</div>
</template>

<style lang="scss" scoped>
// https://picular.co/bluemoon
@import url("https://fonts.googleapis.com/css?family=Raleway:400,400i,700");
.c {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #1A1E23;
  text-align: center;
}
ul {
  display: flex;
  list-style-type: none;
  flex-direction: column;
  align-items: start;
  li {
    padding: 6px 0;
    a {
      --fill-color: #198CE6;
      display: block;
      position: relative;
      padding: 4px 0;
      background: linear-gradient(var(--fill-color) 0 100%) left / 0 no-repeat;
      background-clip:text;
      background-clip: text;
      text-decoration: none;
      -webkit-text-stroke: 2px var(--fill-color);
      text-transform: uppercase;
      font: 700 3rem Raleway, sans-serif;
      color: transparent;
      transition: 0.5s linear;
      &:hover {
        background-size: 100%;
      }
    }
  }
}
</style>

<script>
export default {
	name: "hover-fill-text"
};
</script>