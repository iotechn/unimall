<template>
	<div class="c">
		<div class="clock-loader"></div>
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
  justify-content: center;
  align-items: center;
  margin: 0;
  min-height: 85vh;
  background: black;
}
.clock-loader {
  --clock-color: var(--primary-color);
  --clock-width: 4rem;
  --clock-radius: calc(var(--clock-width) / 2);
  --clock-minute-length: calc(var(--clock-width) * 0.4);
  --clock-hour-length: calc(var(--clock-width) * 0.2);
  --clock-thickness: 0.2rem;
  display: flex;
  position: relative;
  justify-content: center;
  align-items: center;
  border: 3px solid var(--clock-color);
  border-radius: 50%;
  width: var(--clock-width);
  height: var(--clock-width);
  &::before,
  &::after {
    position: absolute;
    top: calc(var(--clock-radius) * 0.25);
    border-radius: 10px;
    width: var(--clock-thickness);
    background: var(--clock-color);
    content: "";
    transform-origin: center calc(100% - calc(var(--clock-thickness) / 2));
    animation: spin infinite linear;
  }
  &::before {
    height: var(--clock-minute-length);
    animation-duration: 2s;
  }
  &::after {
    top: calc(var(--clock-radius) * 0.25 + var(--clock-hour-length));
    height: var(--clock-hour-length);
    animation-duration: 15s;
  }
}
@keyframes spin {
  to {
    transform: rotate(1turn);
  }
}

</style>

<script>
export default {
	name: "clock"
};
</script>