<template>
  <div class="app-container">
    <el-row :gutter="30">
      <el-col :span="9" >
        <div style="min-height: 36px;" />
      </el-col>
      <el-col :span="6" style="padding-bottom: 30px;">
        <el-card body-style="padding: 0px">
          <canvas id="canvas" class="el-icon-view" style="width: 100%;" />
        </el-card>
      </el-col>
      <el-col :span="9" />
    </el-row>
  </div>
</template>

<script>

import {
  bindUniNotify
} from '@/api/admin'
import QRCode from 'qrcode'

export default {
  name: 'ChangePassword',
  data() {
    return {
      imgUrl: undefined
    }
  },
  created() {
    bindUniNotify()
      .then(res => {
        var canvas = document.getElementById('canvas')
        QRCode.toCanvas(canvas, res.data.data, function(error) {
          if (error) console.error(error)
        })
      })
  },
  methods: {

  }
}
</script>
