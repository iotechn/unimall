<template>
	<div class="c">
		<div class="tabs">
			<ul class="tabs__links">
				<li>
					<button class="tabs__link">
						<span class="tabs__link__text" data-text="Tab 1">Tab 1</span>
					</button>
				</li>
				<li>
					<button class="tabs__link">
						<span class="tabs__link__text" data-text="Tab 2">Tab 2</span>
					</button>
				</li>
				<li>
					<button class="tabs__link">
						<span class="tabs__link__text" data-text="Tab 3">Tab 3</span>
					</button>
				</li>
				<li>
					<button class="tabs__link">
						<span class="tabs__link__text" data-text="Tab 4">Tab 4</span>
					</button>
				</li>
			</ul>
			<ul class="tabs__contents">
				<li>
					<div class="tabs__content">
						tab1的内容
					</div>
				</li>
				<li>
					<div class="tabs__content">
						tab2的内容
					</div>
				</li>
				<li>
					<div class="tabs__content">
						tab3的内容
					</div>
				</li>
				<li>
					<div class="tabs__content">
						tab4的内容
					</div>
				</li>
			</ul>
		</div>
	</div>
</template>

<style lang="scss" scoped>
// gradient: https://webgradients.com
@import url("https://fonts.googleapis.com/css?family=Lato:400,300");
@mixin sp-layout {
  @media screen and (width <= 750px) {
    @content;
  }
}
.c {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #1A1E23;
}
.tabs {
  --tab-links-height: 80px;
  --tab-links-bg: linear-gradient(135deg, #e5eaf1 0%, #b9c5d8 100%);
  --tab-links-overlay: linear-gradient(
          135deg,
          #2cd8d5 0%,
          #6b8dd6 48%,
          #8e37d7 100%
  );
  --tab-link-bg: linear-gradient(
          90deg,
          transparent 70%,
          rgba(255, 255, 255, 0.2) 100%
  );
  width: 100%;
  min-width: 300px;
  max-width: 600px;
  font-family: Lato, sans-serif;
  .tabs__links {
    display: flex;
    list-style-type: none;
    justify-content: space-between;
    align-items: center;
    margin: 0;
    padding: 0;
    height: var(--tab-links-height);
    background: var(--tab-links-bg);
    @include sp-layout {
      height: calc(var(--tab-links-height) / 2);
    }
    li {
      flex: 1;
      .tabs__link {
        overflow: hidden;
        position: relative;
        width: 100%;
        height: var(--tab-links-height);
        background: var(--tab-link-bg);
        cursor: pointer;
        transition: 0.3s;
        all: unset;
        @include sp-layout {
          height: calc(var(--tab-links-height) / 2);
          font-size: 12px;
        }
        &::before {
          position: absolute;
          left: 0;
          top: 0;
          z-index: 2;
          width: 100%;
          height: 100%;
          background: var(--tab-links-overlay);
          content: "";
          transform: scaleY(0);
          transform-origin: top;
          transition: 0.3s ease-in-out;
        }
        .tabs__link__text {
          display: flex;
          position: relative;
          z-index: 2;
          justify-content: center;
          align-items: center;
          opacity: 0.6;
          transition: 0.5s;

          // big-text
          &::before {
            position: absolute;
            top: 160%;
            z-index: -1;
            opacity: 0.1;
            font-weight: 600;
            font-size: 40px;
            color: white;
            content: attr(data-text);
            transition: 1.2s ease-out;
            @include sp-layout {
              display: none;
            }
          }
        }
        &:hover {
          &::before {
            transform: scaleY(1);
          }
          .tabs__link__text {
            opacity: 1;
            color: white;
            &::before {
              transform: translateY(-300%);
            }
          }
        }
        &.active {
          background: white;
          transform: scaleY(1.4);
          &::before {
            transform: scaleY(0.05);
          }
          .tabs__link__text {
            transform: scaleY(0.714);
          }
        }
        &.active .tabs__link__text,
        &.active:hover .tabs__link__text {
          background: var(--tab-links-overlay);
          background-clip:text;
          background-clip: text;
          opacity: 1;
          color: transparent;
        }
      }
    }
  }
  .tabs__contents {
    position: relative;
    list-style-type: none;
    margin-top: 0;
    padding: 40px 60px;
    background: white;
    transition: 1s;

    // bottom-line
    &::after {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      height: 5px;
      background: var(--tab-links-overlay);
      content: "";
    }
    .tabs__content {
      display: none;
      min-height: 200px;
      opacity: 0;
      line-height: 1.8;
      font-weight: 300;
      transform: translateY(30%);
      animation: fadeIn 0.6s forwards;
      &.active {
        display: block;
      }
    }
  }
}
@keyframes fadeIn {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

</style>
<script>
</script>

<script setup>
import { ref, onMounted } from "vue";
export default {
	name: "tabs"
};

onMounted(() => {
	const tabLinks = document.querySelectorAll(".tabs__link");
	const tabContents = document.querySelectorAll(".tabs__content");
	tabLinks[0].classList.add("active");
	tabContents[0].classList.add("active");
	tabLinks.forEach((tabLink, i) => {
		tabLink.addEventListener("click", () => {
			tabLinks.forEach(tabLink => tabLink.classList.remove("active"));
			tabContents.forEach(tabContent => tabContent.classList.remove("active"));
			tabLink.classList.add("active");
			tabContents[i].classList.add("active");
		});
	});
});

</script>