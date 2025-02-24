<template>
	<div class="c">
		<button class="btn btn-primary btn-ghost btn-shine">
			hover me
		</button>
	</div>
</template>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css?family=Lato");
.c {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #1A1E23;
}
.btn {
  --hue: 190;
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
  text-transform: uppercase;
  font-size: 1rem;
  color: white;
  white-space: nowrap;
  transition: 0.25s;
&:hover {
   background: hsl(var(--hue), 100%, 31%);
 }
&-primary {
   --hue: 187;
 }
&-ghost {
   border-color: hsl(var(--hue), 100%, 41%);
   background-color: transparent;
   color: hsl(var(--hue), 100%, 41%);
&:hover {
   color: white;
 }
}
&-shine {
   color: white;
&::before {
   position: absolute;
   left: 0;
   top: 0;
   width: 100%;
   height: 100%;
   background: linear-gradient(
       120deg,
       transparent,
       hsla(var(--hue), 100%, 41%, 0.5),
       transparent
   );
   content: "";
   transform: translateX(-100%);
   transition: 0.6s;
 }
&:hover {
   background: transparent;
   box-shadow: 0 0 20px 10px hsla(var(--hue), 100%, 41%, 0.5);
 }
&:hover::before {
   transform: translateX(100%);
 }
}
}

</style>

<script>
export default {
	name: "hover-shining-btn"
};
</script>