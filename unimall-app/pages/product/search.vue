<template>
  <view class="content">
    <view class="search-box">
      <!-- mSearch组件 如果使用原样式，删除组件元素-->
      <mSearch v-model="keyword" class="mSearch-input-box" :mode="2" button="inside" :placeholder="defaultKeyword" @search="doSearch(false)" @input="inputChange" @confirm="doSearch(false)" />
    </view>
    <view class="search-keyword" @touchstart="blur">
      <scroll-view v-show="isShowKeywordList" class="keyword-list-box" scroll-y>
        <view v-for="row in keywordList" :key="row.keyword" class="keyword-entry" hover-class="keyword-entry-tap">
          <view class="keyword-text" @tap="doSearch(row.keyword)">
            <rich-text :nodes="row.htmlStr" />
          </view>
          <view class="keyword-img" @tap="setkeyword(row)">
            <image src="/static/HM-search/back.png" />
          </view>
        </view>
      </scroll-view>
      <scroll-view v-show="!isShowKeywordList" class="keyword-box" scroll-y>
        <view v-if="oldKeywordList.length>0" class="keyword-block">
          <view class="keyword-list-header">
            <view>历史搜索</view>
            <view>
              <image src="/static/HM-search/delete.png" @tap="oldDelete" />
            </view>
          </view>
          <view class="keyword">
            <view v-for="(oneKeyword,index) in oldKeywordList" :key="index" @tap="doSearch(oneKeyword)">
              {{ oneKeyword }}
            </view>
          </view>
        </view>
      </scroll-view>
    </view>
  </view>
</template>

<script>
import mSearch from '@/components/mehaotian-search-revision.vue'
export default {
  components: {
    mSearch
  },
  data() {
    return {
      defaultKeyword: '',
      keyword: '',
      oldKeywordList: [],
      hotKeywordList: [],
      keywordList: [],
      forbid: '',
      isShowKeywordList: false
    }
  },
  onLoad() {
    this.init()
  },
  methods: {
    init() {
      this.loadDefaultKeyword()
      this.loadOldKeyword()
      // this.loadHotKeyword();
    },
    blur() {
      uni.hideKeyboard()
    },
    // 加载默认搜索关键字
    loadDefaultKeyword() {
      // 定义默认搜索关键字，可以自己实现ajax请求数据再赋值,用户未输入时，以水印方式显示在输入框，直接不输入内容搜索会搜索默认关键字
      this.defaultKeyword = '默认关键字'
    },
    // 加载历史搜索,自动读取本地Storage
    loadOldKeyword() {
      uni.getStorage({
        key: 'OldKeys',
        success: (res) => {
          var OldKeys = JSON.parse(res.data)
          this.oldKeywordList = OldKeys
        }
      })
    },
    // 高亮关键字
    drawCorrelativeKeyword(keywords, keyword) {
      var len = keywords.length
      var keywordArr = []
      for (var i = 0; i < len; i++) {
        var row = keywords[i]
        // 定义高亮#9f9f9f
        var html = row[0].replace(keyword, "<span style='color: #9f9f9f;'>" + keyword + '</span>')
        html = '<div>' + html + '</div>'
        var tmpObj = {
          keyword: row[0],
          htmlStr: html
        }
        keywordArr.push(tmpObj)
      }
      return keywordArr
    },
    // 顶置关键字
    setkeyword(data) {
      this.keyword = data.keyword
    },
    // 清除历史搜索
    oldDelete() {
      uni.showModal({
        content: '确定清除历史搜索记录？',
        success: (res) => {
          if (res.confirm) {
            console.log('用户点击确定')
            this.oldKeywordList = []
            uni.removeStorage({
              key: 'OldKeys'
            })
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
    },
    // 热门搜索开关
    hotToggle() {
      this.forbid = this.forbid ? '' : '_forbid'
    },
    // 执行搜索
    doSearch(key) {
      key = key || (this.keyword ? this.keyword : this.defaultKeyword)
      this.keyword = key
      this.saveKeyword(key) // 保存为历史
      uni.navigateTo({
        url: `/pages/product/list?keyword=${key}`
      })
    },
    // 保存关键字到历史记录
    saveKeyword(keyword) {
      uni.getStorage({
        key: 'OldKeys',
        success: (res) => {
          console.log(res.data)
          var OldKeys = JSON.parse(res.data)
          var findIndex = OldKeys.indexOf(keyword)
          if (findIndex === -1) {
            OldKeys.unshift(keyword)
          } else {
            OldKeys.splice(findIndex, 1)
            OldKeys.unshift(keyword)
          }
          // 最多10个纪录
          OldKeys.length > 10 && OldKeys.pop()
          uni.setStorage({
            key: 'OldKeys',
            data: JSON.stringify(OldKeys)
          })
          this.oldKeywordList = OldKeys // 更新历史搜索
        },
        fail: (e) => {
          var OldKeys = [keyword]
          uni.setStorage({
            key: 'OldKeys',
            data: JSON.stringify(OldKeys)
          })
          this.oldKeywordList = OldKeys // 更新历史搜索
        }
      })
    }
  }
}
</script>
<style>
	view{display:block;}
	.search-box {width:100%;background-color:rgb(242,242,242);padding:15upx 2.5%;display:flex;justify-content:space-between;}
	.search-box .mSearch-input-box{width: 100%;}
	.search-box .input-box {width:85%;flex-shrink:1;display:flex;justify-content:center;align-items:center;}
	.search-box .search-btn {width:15%;margin:0 0 0 2%;display:flex;justify-content:center;align-items:center;flex-shrink:0;font-size:28upx;color:#fff;background:linear-gradient(to right,#ff9801,#ff570a);border-radius:60upx;}
	.search-box .input-box>input {width:100%;height:60upx;font-size:32upx;border:0;border-radius:60upx;-webkit-appearance:none;-moz-appearance:none;appearance:none;padding:0 3%;margin:0;background-color:#ffffff;}
	.placeholder-class {color:#9e9e9e;}
	.search-keyword {width:100%;background-color:rgb(242,242,242);}
	.keyword-list-box {height:calc(100vh - 110upx);padding-top:10upx;border-radius:20upx 20upx 0 0;background-color:#fff;}
	.keyword-entry-tap {background-color:#eee;}
	.keyword-entry {width:94%;height:80upx;margin:0 3%;font-size:30upx;color:#333;display:flex;justify-content:space-between;align-items:center;border-bottom:solid 1upx #e7e7e7;}
	.keyword-entry image {width:60upx;height:60upx;}
	.keyword-entry .keyword-text,.keyword-entry .keyword-img {height:80upx;display:flex;align-items:center;}
	.keyword-entry .keyword-text {width:90%;}
	.keyword-entry .keyword-img {width:10%;justify-content:center;}
	.keyword-box {height:calc(100vh - 110upx);border-radius:20upx 20upx 0 0;background-color:#fff;}
	.keyword-box .keyword-block {padding:10upx 0;}
	.keyword-box .keyword-block .keyword-list-header {width:94%;padding:10upx 3%;font-size:27upx;color:#333;display:flex;justify-content:space-between;}
	.keyword-box .keyword-block .keyword-list-header image {width:40upx;height:40upx;}
	.keyword-box .keyword-block .keyword {width:94%;padding:3px 3%;display:flex;flex-flow:wrap;justify-content:flex-start;}
	.keyword-box .keyword-block .hide-hot-tis {display:flex;justify-content:center;font-size:28upx;color:#6b6b6b;}
	.keyword-box .keyword-block .keyword>view {display:flex;justify-content:center;align-items:center;border-radius:60upx;padding:0 20upx;margin:10upx 20upx 10upx 0;height:60upx;font-size:28upx;background-color:rgb(242,242,242);color:#6b6b6b;}
</style>
