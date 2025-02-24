<template>
	<div class="c">
		<ul class="menu-hover-fill">
			<li><a href="#" data-text="home">home</a></li>
			<li><a href="#" data-text="archives">archives</a></li>
			<li><a href="#" data-text="tags">tags</a></li>
			<li><a href="#" data-text="categories">categories</a></li>
			<li><a href="#" data-text="about">about</a></li>
		</ul>
	</div>
</template>

<style lang="scss" scoped>

.c {
  --grey-color: #7f8c8d;
  --primary-color: hsl(196deg, 78%, 61%);
  --secondary-color: hsl(217deg, 15%, 83%);
  --success-color: hsl(165deg, 58%, 55%);
  --info-color: hsl(214deg, 79%, 65%);
  --warning-color: hsl(43deg, 100%, 66%);
  --danger-color: hsl(354deg, 81%, 63%);
  --primary-color-darker: hsl(196deg, 68%, 54%);
  --secondary-color-darker: hsl(215deg, 13%, 70%);
  --success-color-darker: hsl(165deg, 55%, 48%);
  --info-color-darker: hsl(214deg, 68%, 58%);
  --warning-color-darker: hsl(39deg, 97%, 62%);
  --danger-color-darker: hsl(354deg, 67%, 56%);
  --primary-color-lighter: hsl(196deg, 78%, 81%);
  --secondary-color-lighter: hsl(214deg, 16%, 92%);
  --success-color-lighter: hsl(165deg, 58%, 75%);
  --info-color-lighter: hsl(214deg, 79%, 85%);
  --warning-color-lighter: hsl(43deg, 100%, 86%);
  --danger-color-lighter: hsl(354deg, 81%, 83%);
  --secondary-color-darkest: hsl(215deg, 11%, 30%);
  --secondary-color-lightest: hsl(220deg, 1%, 98%);
  --grey-color: #7f8c8d;
  display: flex;
  position: fixed;
  z-index: 0;
  padding-left: 300px;
  padding-top: 100px;
  width: 100%;
  min-height: 100vh;
  background: black;
}
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}
a {
  height: 100px;
  text-decoration: none;
}

$menu-link-active-colors: var(--primary-color), var(--info-color),
var(--success-color), var(--warning-color), var(--danger-color);
.menu-hover-fill {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  line-height: 1;
  text-transform: uppercase;
  font-size: 1.5rem;
li {
  position: relative;
  margin-top: 20px;
&::before {
   position: absolute;
   left: -1rem;
   top: 0;
   width: 0.25rem;
   height: 100%;
   background: var(--menu-link-active-color);
   content: "";
   transition: 0.6s;
 }
a {
  --menu-link-color:  var(--grey-color);
  position: relative;
  background: linear-gradient(var(--menu-link-active-color) 0 100%) left / 0
  no-repeat;
  background-clip: text;
  background-clip: text;
  color: transparent;
  transition: background-size 0.45s 0.04s;
&::before {
   position: absolute;
   z-index: -1;
   color: var(--grey-color);
   content: attr(data-text);
 }
}
@for $i from 1 through length($menu-link-active-colors) {
&:nth-child(#{$i}) {
  --menu-link-active-color: #{nth($menu-link-active-colors, $i)};
}
}
&:hover {
&::before {
   left: calc(100% + 1rem);
 }
a {
  background-size: 100%;
}
}
}
}

</style>

<script>
export default {
	name: "hover-slide-menu"
};
</script>