<template>
  <div class="members-frame" ref="memberFrame">
    <div class="left-arrow" v-show="arrowstatus" v-on:click="left"><img src="./iconMyteamArrowLeft.png"></div>
    <div class="teamMembers" ref="members">
      <slot></slot>
    </div>
    <div class="right-arrow" v-show="arrowstatus" v-on:click="right"><img src="./icoMyteamArrowright.png"></div>
  </div>
</template>
<script>
export default {
  props: {
    listLength: Number,
    numOfOnce: Number,
    unitLength: Number,
    unitMargin: Number
  },
  data () {
    return {
      listWidth: this.listLength * (this.unitLength + this.unitMargin) - this.unitMargin,
      index: 0,
      arrowstatus: false,
      max: '',  // (this.listLength * (this.unitLength + this.unitMargin) - this.unitMargin - document.body.clientWidth + this.offset),
      framewidth: ''  // document.body.clientWidth
    }
  },
  computed: {
  },
  created () {
  },
  mounted () {
    this.$nextTick(() => {
      this.$refs.members.style.width = this.$refs.memberFrame.offsetWidth
      this.framewidth = parseInt(this.$refs.members.offsetWidth)
      if (this.max > 0) {
        this.arrowstatus = true
      }
      const that = this
      window.addEventListener('resize', function () {
        if (that.$refs.members != null) {
          that.$refs.members.style.width = that.$refs.memberFrame.offsetWidth
          that.framewidth = parseInt(that.$refs.members.offsetWidth)
        }
      })
    })
  },
  methods: {
    onClick (id) {
      this.$emit('memberClick', id)
    },
    left () {
      if (this.index <= (this.unitLength + this.unitMargin) * this.numOfOnce) {
        this.index = 0
        this.firstMemberLeftMargin()
      } else {
        this.index = this.index - (this.unitLength + this.unitMargin) * this.numOfOnce
        this.firstMemberLeftMargin()
      }
    },
    right () {
      if (this.index < this.max) {
        if ((this.max - this.index) >= 0) {
          this.index = this.index + (this.unitLength + this.unitMargin) * this.numOfOnce
          this.firstMemberLeftMargin()
        } else {
          this.index = this.max
          this.firstMemberLeftMargin()
        }
      }
    },
    firstMemberLeftMargin () {
      if (this.$refs.members.getElementsByTagName('div')[0] != null) {
        this.$refs.members.getElementsByTagName('div')[0].style.marginLeft = (-this.index) + 'px'
      }
    }
  },
  watch: {
    framewidth () {
      this.max = (this.listWidth - this.framewidth)
      if (!this.timer) {
        this.timer = true
        let that = this
        if (that.max <= 0) {
          this.arrowstatus = false
          this.index = 0
          this.firstMemberLeftMargin()
        } else {
          this.arrowstatus = true
          if (this.index > that.max) {
            this.index = ((this.unitLength + this.unitMargin) - that.max % (this.unitLength + this.unitMargin)) + that.max
            this.firstMemberLeftMargin()
          }
        }
        setTimeout(function () {
        // that.framewidth = that.$store.state.canvasWidth
        // console.log(that.framewidth)
        // console.log(that.max)
        // that.init()
          that.timer = false
        }, 400)
      }
    },
    listLength () {
      this.listWidth = this.listLength * (this.unitLength + this.unitMargin) - this.unitMargin
      this.max = (this.listWidth - this.framewidth)
      if (this.max > 0) {
        this.arrowstatus = true
        if (this.index > this.max) {
          this.index = ((this.unitLength + this.unitMargin) - this.max % (this.unitLength + this.unitMargin)) + this.max
          this.firstMemberLeftMargin()
        }
      } else {
        this.arrowstatus = false
        this.index = 0
        this.firstMemberLeftMargin()
      }
    }
  },
  components: {
  }
}
</script>

<style scoped>
  .members-frame{
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .teamMembers{ 
    width: 100%;
    overflow-x: hidden;
    display: flex;
    background-color: rgb(245,246,248);
    align-items: center;
  }
  .member{
    margin-right: 12px;
  }
  .left-arrow{
    display: flex;
    justify-content: center;
    cursor: pointer;
    width: 38px;
    height: 38px;
    float: left;
    margin-left: -38px;
  }
  .right-arrow{
    display: flex;
    justify-content: center;
    cursor: pointer;
    width: 38px;
    height: 38px;
    float: right;
    margin-right: -38px
  }
  img{
    height: 38px;
    width: 18px;
  }
</style>
