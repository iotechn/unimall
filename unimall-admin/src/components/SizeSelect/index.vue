<template>
  <el-dropdown trigger="click" @command="handleSetSize">
    <div>
      <svg-icon class-name="size-icon" icon-class="size" />
    </div>
    <template v-slot:dropdown>
      <el-dropdown-menu>
        <el-dropdown-item :disabled="size === 'medium'" command="medium"
          >Medium</el-dropdown-item
        >
        <el-dropdown-item :disabled="size === 'small'" command="small"
          >Small</el-dropdown-item
        >
        <el-dropdown-item :disabled="size === 'mini'" command="mini"
          >Mini</el-dropdown-item
        >
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script>
export default {
  computed: {
    size() {
      return this.$store.getters.size
    },
  },
  methods: {
    handleSetSize(size) {
      // this.$ELEMENT.size = size
      this.$store.dispatch('setSize', size)
      this.refreshView()
      this.$message({
        message: 'Switch Size Success',
        type: 'success',
      })
    },
    refreshView() {
      // In order to make the cached page re-rendered
      this.$store.dispatch('delAllCachedViews', this.$route)

      const { fullPath } = this.$route
      console.log(fullPath, '11111111111')
      this.$nextTick(() => {
        this.$router.replace({
          path: '/redirect' + fullPath,
        })
      })
    },
  },
}
</script>

<style scoped>
.size-icon {
  font-size: 20px;
  cursor: pointer;
  vertical-align: -4px !important;
}
</style>
