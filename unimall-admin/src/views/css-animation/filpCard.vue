<template>
	<div class=".c">
		<div class="scene">
			<div class="card">
				<div class="card__face card__face--front">
					<img src="https://img2.baidu.com/it/u=954168163,2870833311&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=717" />
				</div>
				<div class="card__face card__face--back">
					<img src="https://img1.baidu.com/it/u=3890478742,1055137025&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=730" />
				</div>
			</div>
			<div class="card">
				<div class="card__face card__face--front">
					<img src="https://img2.baidu.com/it/u=954168163,2870833311&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=717" />
				</div>
				<div class="card__face card__face--back">
					<img src="https://img1.baidu.com/it/u=2274324656,1188253596&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=727" />
				</div>
			</div>
			<div class="card">
				<div class="card__face card__face--front">
					<img src="https://img2.baidu.com/it/u=954168163,2870833311&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=717" />
				</div>
				<div class="card__face card__face--back">
					<img src="https://pic.rmb.bdstatic.com/bjh/news/a87e84e97d936f6e84db4f78432bc068.jpeg" />
				</div>
			</div>
		</div>
	</div>
</template>

<style lang="scss" scoped>
// Thanks to https://3dtransforms.desandro.com/card-flip
.c {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: black;
}
.scene {
  display: flex;
  justify-content: space-between;
  width: 1000px;
  perspective: 800px;
  .card {
    position: relative;
    width: 240px;
    height: 300px;
    cursor: pointer;
    color: white;
    transform-style: preserve-3d;
    transition: 1s ease-in-out;
    &:hover {
      transform: rotateY(0.5turn);
    }
    .card__face {
      position: absolute;
      left: 0;
      top: 0;
      width: 100%;
      height: 100%;
      -webkit-box-reflect: below 0
      linear-gradient(transparent, transparent, rgba(0, 0, 0, 0.4));
      backface-visibility: hidden;
      transition: 1s ease-in-out;
      img {
        width: 240px;
        object-fit: cover;
      }
      &--back {
        transform: rotateY(0.5turn);
      }
    }
  }
}

</style>

<script>
export default {
	name: "filp-card"
};
</script>