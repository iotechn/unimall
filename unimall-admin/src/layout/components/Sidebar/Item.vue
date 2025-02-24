<template>
  <div>
    <svg-icon v-if="icon" :icon-class="icon" />
    <span v-if="title" slot="title">{{ title }}</span>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps({
  icon: {
    type: String,
    default: "",
  },
  title: {
    type: String,
    default: "",
  },
});

const { icon, title } = props;
</script>
