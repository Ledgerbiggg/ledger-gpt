<template>
  <div class="examples" v-if="!haveChat">
    <div class="example" v-show="isPc">
      <div class="example-img">
        <img
          src="https://static.thenounproject.com/png/1167484-200.png"
          alt=""
        />
        <p>能力</p>
      </div>
      <button>记住用户之前在对话中所说的话</button>
      <button>允许用户提供后续更正请求的对话内容继续请求</button>
      <button>接受过拒绝不当请求的培训</button>
    </div>
    <div class="example">
      <div class="example-img">
        <img
          src="https://cdn-icons-png.flaticon.com/128/606/606795.png"
          alt=""
        />
        <p>例子</p>
      </div>

      <button @click="setExample" style="background: #fc6746">
        写一段java的helloworld的代码并解释
      </button>
      <button @click="setExample" style="background: #fc6746">
        如何在JavaScript中发出HTTP请求
      </button>
      <button @click="setExample" style="background: #fc6746">
        我们来斗表情吧，我先来🤣🤣🤣
      </button>
    </div>
    <div class="example down" v-show="isPc">
      <div class="example-img">
        <img
          src="https://cdn-icons-png.flaticon.com/128/159/159469.png"
          alt=""
        />
        <p>局限性</p>
      </div>
      <button>可能偶尔生成不正确的信息</button>
      <button>可能偶尔产生有害说明或有偏见的内容</button>
      <button>对2021年后的世界和事件的了解有限</button>
    </div>
  </div>
</template>

<script>
export default {
  name: "InitPage",
  data() {
    return {
      isPc: true,
    };
  },
  methods: {
    setExample(e) {
      this.$emit("setExample", { message: e.target.textContent });
    },
  },
  mounted() {
    const screenWidth = window.innerWidth;
    if (screenWidth <= 768) {
      this.isPc = false;
    }
  },
};
</script>

<style>
.examples {
  margin-left: -20px;
  display: flex;
  align-items: flex-start;
  justify-content: center;
}
.example {
  display: flex;
  align-items: flex-start;
}
.examples {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  margin-top: 45px;
}

.example {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  gap: 14px;
  max-width: 238px;
  margin-left: 15px;
  border-radius: 10px;
  height: 100%;
}

.example img {
  width: 1.5rem;
  height: 1.5rem;
}

.example button {
  border: none;
  background-color: #f7f7f8;
  color: #343541;
  padding: 12px;
  border-radius: 5.5px;
  line-height: 20.5px;
  font-size: 14px;
  letter-spacing: 0.2px;
}

.example p {
  font-size: 18px;
  color: #343541;
}

.down {
  /* margin-top: 22px; */
}

.example-img {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  gap: 9px;
  margin-bottom: 4px;
}
</style>