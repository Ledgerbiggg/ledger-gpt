<template>
  <div class="drawer">
    <div class="drawerBox">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="配置参数" name="first">
          <div class="first">
            <div class="title">
              <div class="mode"><b>模型:</b></div>
              <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                  {{ model[option]
                  }}<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item icon="el-icon-s-comment"
                    ><span @click="option = 0"
                      >对话模式3.5(推荐)</span
                    ></el-dropdown-item
                  >
                  <el-dropdown-item icon="el-icon-chat-dot-round"
                    ><span @click="option = 1"
                      >对话模式3.0</span
                    ></el-dropdown-item
                  >
                  <el-dropdown-item icon="el-icon-picture"
                    ><span @click="option = 2"
                      >图片生成1.0</span
                    ></el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <div class="parameter">
              <p><b>参数配置:</b></p>
              <br /><br />
              <div class="parameters">
                <div class="picNumber" v-show="option == 2">
                  <div class="pic">图片数量参数:</div>
                  <el-slider v-model="params.n" :step="1" :max="3" :min="1">
                  </el-slider>
                </div>
                <div class="temperature" v-show="option == 0 || option == 1">
                  <div class="temperatureParam">
                    随机性程度参数&nbsp;&nbsp;(值越大回答随机性越高):
                  </div>
                  <el-slider
                    v-model="params.temperature"
                    :step="0.1"
                    :min="0.7"
                    :max="1.0"
                    :show-tooltip="false"
                  >
                  </el-slider>
                </div>
                <div
                  class="presence_penalty"
                  v-show="option == 0 || option == 1"
                >
                  <div class="presence_penalty_param">
                    避免上下文重复参数&nbsp;&nbsp;(值越大，上下文文本的重复率越低):
                  </div>
                  <el-slider
                    v-model="params.presence_penalty"
                    :step="0.1"
                    :min="0.3"
                    :max="1.0"
                    :show-tooltip="false"
                  >
                  </el-slider>
                </div>
                <div
                  class="frequency_penalty"
                  v-show="option == 0 || option == 1"
                >
                  <div class="frequency_penalty_param">
                    避免同一文重复参数&nbsp;&nbsp;(值越大，一条回答中文本的重复率越低):
                  </div>
                  <el-slider
                    v-model="params.frequency_penalty"
                    :step="0.1"
                    :max="1"
                    :show-tooltip="false"
                  >
                  </el-slider>
                </div>
                <div class="top_p" v-show="option == 0 || option == 1">
                  <div class="top_p_param">
                    概率分布中选择可能结果的阈值大小&nbsp;&nbsp;(值越大，筛选的回答标准越严格):
                  </div>
                  <el-slider
                    v-model="params.top_p"
                    :step="0.05"
                    :min="0.8"
                    :max="1.0"
                    :show-tooltip="false"
                  >
                  </el-slider>
                </div>
                <div class="formatSpeed" v-show="option == 0 || option == 1">
                  <div class="formatSpeed_param">文字的生成速度</div>
                  <el-slider
                    v-model="formatSpeed"
                    :min="5"
                    :max="20"
                    :show-tooltip="false"
                  >
                  </el-slider>
                </div>
              </div>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="还没做的模块" name="second" ref="user">
          商务合作加vx：ledgerbiggg
          <img
            style="width: 100%"
            src="https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/202305082045015.jpg"
            alt=""
          />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
// 在组件中发送消息

export default {
  name: "DrawerSetting",
  data() {
    return {
      params: {
        n: 1,
        temperature: 0.9,
        presence_penalty: 0.8,
        frequency_penalty: 0.9,
        top_p: 1.0,
      },
      formatSpeed: 18,
      activeName: "first",
      model: ["对话模式3.5(推荐)", "对话模式3.0", "图片生成1.0"],
      option: 0,
    };
  },
  mounted() {
    this.showUser();
  },
  methods: {
    showUser() {
      console.log(this.$refs.user);
      setTimeout(() => {
        this.$refs.user.$el.click();
      }, 200);
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
  },
  watch: {
    option(option) {
      this.$emit("changeModel", option, this.params, this.formatSpeed);
    },
    params: {
      handler(params) {
        this.$emit("changeModel", this.option, params, this.formatSpeed);
      },
      deep: true,
    },
    formatSpeed(formatSpeed) {
      this.$emit("changeModel", this.option, this.params, 25 - formatSpeed);
    },
  },
};
</script>

<style>
* {
  text-decoration: none !important;
}
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
.demonstration {
  display: block;
  color: #8492a6;
  font-size: 14px;
  margin-bottom: 20px;
}
.drawer {
  position: relative;
  width: 300px;
  font-family: "LiHei Pro", "LiSong Pro", "华文细黑", serif;
}
.drawerBox {
  position: relative;
  z-index: 9999;
}
.first {
  padding: 0 20px;
}
.title {
  padding: 0 30px;
  display: flex;
  margin: 30px 0;
}
.title .mode {
  width: 30%;
}
.model {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  margin: 30px 0;
}

.model .mode {
  width: 20%;
}

@media screen and (max-width: 768px) {
  .drawer {
    width: 300px;
  }
}
@media screen and (min-width: 768px) {
  .drawer {
    width: 600px;
  }
}
</style>