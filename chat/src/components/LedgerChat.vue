<template>
  <div>
    <div class="home">
      <div class="left">
        <div class="new-chat" @click="addChat">
          <img
            src="https://cdn-icons-png.flaticon.com/128/9312/9312231.png"
            alt=""
          />
          <p>新会话</p>
        </div>
        <!-- 导航模块 -->
        <div class="chats">
          <ul ref="bar">
            <li ref="barLi" v-for="(chat, key) in barList" :key="key">
              <button
                ref="barBtn"
                :class="{ selected: key == current, barBtn: true }"
                @click="changeBar(key)"
              >
                <div class="chat">
                  <img
                    src="https://cdn-icons-png.flaticon.com/128/9256/9256389.png"
                    alt=""
                  />
                  <p ref="barp" class="barp">
                    {{ chat }}
                  </p>
                  <font-awesome-icon
                    class="delete"
                    :icon="['fas', 'trash-can']"
                    style="color: #f8f9fc"
                    @click="deleteChat(key)"
                  />
                  <font-awesome-icon
                    style="color: #f8f9fc"
                    :icon="['fas', 'pen-to-square']"
                    class="edit"
                    @click="edit(key, barList[key])"
                  />
                </div>
              </button>
            </li>
          </ul>
        </div>
        <div class="line"></div>
        <div class="features">
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/3405/3405244.png"
              alt=""
            />
            <p @click="clearChatLength">清除所有聊天记录</p>
          </div>
          <div class="feature" @click="showPammentCode">
            <img
              class="user"
              src="https://cdn-icons-png.flaticon.com/128/2815/2815428.png"
              alt=""
            />
            <p>激励开发者</p>
          </div>
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/9580/9580357.png"
              alt=""
            />
            <p @click="changeMode">夜间模式</p>
          </div>
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/3405/3405255.png"
              alt=""
            />
            <p @click="drawerRight = true">选择模式</p>
            <div class="new">NEW</div>
          </div>
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/9297/9297112.png"
              alt=""
            />
            <p>退出</p>
          </div>
        </div>
      </div>

      <!-- chat模块 -->
      <div class="right" ref="mode">
        <div style="margin-top: 3vh" v-show="isPhone" class="bar">
          <div>
            <font-awesome-icon
              :icon="['fas', 'message']"
              @click="drawerLeft = true"
            />
          </div>
          <div>
            <font-awesome-icon :icon="['fas', 'user']" @click="showUser" />
          </div>
        </div>
        <ul
          style="margin-top: 3vh"
          ref="messages"
          v-show="
            chatLength[current] != current &&
            chatLength[current] != null &&
            chatLength[current].length > 1 &&
            chat
          "
          class="chatul"
          id="chatul"
        >
          <li
            ref="chatCode"
            :style="{
              backgroundColor:
                mode == 'light'
                  ? index % 2 == 1
                    ? '#FFFFFF'
                    : '#F7F7F8'
                  : index % 2 == 0
                  ? '#343541'
                  : '#444654',
            }"
            class="chatli"
            v-for="(chat, index) in chatLength[current]"
            :key="index"
            :v-pre="index <= chatLength[current].length"
          >
            <img v-if="index % 2 == 0" class="userImage" :src="avatar" alt="" />
            <img
              v-else
              class="userImage"
              src="https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/202305020044278.png"
              alt=""
            />
            <div class="dialogue" v-html="chat"></div>
          </li>
        </ul>
        <h1 v-if="!haveChat" class="ChatGPT">ChatGPT</h1>
        <InitPageVue v-show="!haveChat" @setExample="setExample" />
        <payment-code v-if="havePammentCode == 1" />
        <!-- footer -->
        <div class="input-text" ref="inputtext">
          <div class="stopIcon" @click="stopLoad" v-show="showStopIcon">
            停止生成
          </div>
          <div class="oneLine">
            <font-awesome-icon
              :icon="['fas', 'bars']"
              v-show="isPhone"
              class="option"
              @click="drawerRight = true"
            />
            <textarea
              ref="input"
              type="text"
              v-model="msg"
              @keydown.enter="submit"
              placeholder="发送消息"
            />
            <font-awesome-icon
              :icon="['fas', 'paper-plane']"
              @click="submit"
              class="send"
            />
          </div>
          <p>
            商业合作请联系微信：Ledgerbiggg&nbsp;&nbsp;未经本人允许，禁止用作其他商用活动！
          </p>
        </div>
      </div>
    </div>
    <el-drawer :visible.sync="drawerRight" :with-header="false" size="700">
      <!-- 右边设置栏 -->
      <Drawer @changeModel="changeModel" ref="rightDrawer" />
    </el-drawer>
    <el-drawer
      :visible.sync="drawerLeft"
      :with-header="false"
      direction="ltr"
      size="700"
    >
      <div class="leftBar" style="max-width: 300px">
        <div class="new-chat" @click="addChat">
          <img
            src="https://cdn-icons-png.flaticon.com/128/9312/9312231.png"
            alt=""
          />
          <p>新会话</p>
        </div>
        <!-- 导航模块 -->
        <div class="chats">
          <ul ref="bar">
            <li ref="barLi" v-for="(chat, key) in barList" :key="key">
              <button
                ref="barBtn"
                :class="{ selected: key == current, barBtn: true }"
                @click="changeBar(key)"
              >
                <div class="chat">
                  <img
                    src="https://cdn-icons-png.flaticon.com/128/9256/9256389.png"
                    alt=""
                  />
                  <p ref="barp" class="barp">
                    {{ chat }}
                  </p>
                  <font-awesome-icon
                    class="delete"
                    :icon="['fas', 'trash-can']"
                    style="color: #f8f9fc"
                    @click="deleteChat(key)"
                  />
                  <font-awesome-icon
                    style="color: #f8f9fc"
                    :icon="['fas', 'pen-to-square']"
                    class="edit"
                    @click="edit(key, barList[key])"
                  />
                </div>
              </button>
            </li>
          </ul>
        </div>
        <div class="line"></div>
        <div class="features">
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/3405/3405244.png"
              alt=""
            />
            <p @click="clearChatLength">清除所有聊天记录</p>
          </div>
          <div class="feature" @click="showPammentCode">
            <img
              class="user"
              src="https://cdn-icons-png.flaticon.com/128/2815/2815428.png"
              alt=""
            />
            <p>激励开发者</p>
          </div>
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/9580/9580357.png"
              alt=""
            />
            <p @click="changeMode">夜间模式</p>
          </div>
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/3405/3405255.png"
              alt=""
            />
            <p @click="drawerRight = true">选择模式</p>
            <div class="new">NEW</div>
          </div>
          <div class="feature">
            <img
              src="https://cdn-icons-png.flaticon.com/128/9297/9297112.png"
              alt=""
            />
            <p>退出</p>
          </div>
        </div>
      </div>
    </el-drawer>
    <el-drawer
      title="我是标题"
      :visible.sync="userInfo"
      :with-header="false"
      size="700"
    >
      <UserInfo @changeAvatar="changeAvatar" />
    </el-drawer>
  </div>
</template>

<script>
import { renderByMathjax } from "mathjax-vue";
import http from "../api/http";
import InitPageVue from "@/page/InitPage.vue";
import Drawer from "@/page/DrawerSetting.vue";
import UserInfo from "@/page/UserInfo.vue";
// import LeftChatsVue from "@/page/LeftChats.vue";
//mk语法
import { marked } from "marked";
// import MarkdownIt from "markdown-it";
//高亮
import hljs from "highlight.js/lib/core";
import "highlight.js/styles/github.css";
//剪切板
import ClipboardJS from "clipboard";
//java高亮
import java from "highlight.js/lib/languages/java";
//字体图标
import PaymentCode from "@/page/PaymentCode.vue";
import Vue from "vue";

// 创建自定义的渲染器
const renderer = new marked.Renderer();
renderer.paragraph = function (text) {
  // 将段落的文本直接返回，而不添加 <p> 标签
  return text;
};

hljs.registerLanguage("javascript", java);
marked.setOptions({
  renderer: new marked.Renderer(),
  gfm: true,
  tables: true,
  breaks: false,
  pedantic: false,
  sanitize: false,
  smartLists: true,
  smartypants: false,
  highlight: function (code, lang) {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(lang, code, true).value;
    } else {
      return hljs.highlightAuto(code).value;
    }
  },
});

export default {
  name: "LedgerChat",
  components: {
    InitPageVue,
    PaymentCode,
    Drawer,
    UserInfo,
  },
  data: function () {
    return {
      isPhone: false,
      //当前聊天的索引
      current: 0,
      //聊天记录
      chatLength: {},
      //皮肤模式
      mode: "light",
      //消息
      msg: "",
      //点点
      dot: ".",
      //显示点点的计时器
      timer: null,
      //是否存在聊天
      haveChat: true,
      //付款码
      havePammentCode: 0,
      chat: true,
      showStopIcon: false,
      renderTimer: [],
      stopLoadFlag: false,
      //自增key变量，保证id唯一
      maxKey: 0,
      barList: [],
      //右边的弹框
      drawerRight: false,
      //左边的弹框
      drawerLeft: false,
      //user
      userInfo: false,
      model: 0,
      params: {
        n: 1,
        temperature: 0.9,
        presence_penalty: 0.8,
        frequency_penalty: 0.9,
        top_p: 1.0,
      },
      formatSpeed: 7,
      avatar: "",
      isLoading: false,
    };
  },
  beforeMount() {
    this.isPhone = window.innerWidth <= 798;
    this.getInfo();
    this.$confirm(
      "换域名啦，换域名啦，以后这就没啦，来这里http://ledgerhhh-ai.top/啊，别走丢了",
      "提示",
      {
        confirmButtonText: "😅",
        cancelButtonText: "🤩",
        type: "warning",
      }
    );
  },
  mounted() {
    Vue.observable(this.chatLength);
  },
  methods: {
    //修改头像
    changeAvatar(avatarUrl) {
      this.avatar = avatarUrl;
    },
    //展示用户信息
    showUser() {
      this.userInfo = true;
    },
    getInfo() {
      http.get("/chatInfo/getInfo").then((res) => {
        if (res.data.data != null) {
          let data = res.data.data;
          if (data.barList != null) {
            this.barList = data.barList;
          }
          this.chat = data.chat;
          this.haveChat = data.haveChat;
          this.havePammentCode = data.havePammentCode;
          this.maxKey = data.maxKey;
          this.msg = data.msg;
          if (data.chatLength != null) {
            this.chatLength = data.chatLength;
          }
          this.current = data.current;
        }
      });
      http.get("/user/getAvatar").then((res) => {
        this.avatar = res.data.data;
      });
    },
    //改变模式
    changeModel(model, params, formatSpeed) {
      this.model = model;
      this.params = params;
      this.formatSpeed = formatSpeed;
    },
    //编辑bar
    edit(index, v) {
      if (this.isLoading) {
        return;
      }
      this.$prompt("输入对话名称", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputValue: v,
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "修改成功",
          });
          //名字不能一样
          for (let i = 0; i < this.barList.length; i++) {
            if (this.barList[i] == value) {
              this.$message({
                title: "提示",
                type: "warning",
                message: "有重复命名",
              });
              return;
            }
          }
          http
            .put(`/barList/edit/${this.barList[index]}/${value}`)
            .then((res) => {
              console.log(res);
            });
          this.barList[index] = value;

          this.$forceUpdate();
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消修改",
          });
        });
    },
    stopLoad() {
      this.showStopIcon = false;
      this.$refs.input.readOnly = false;
      this.$refs.input.placeholder = "发送消息";
      //不要开计时器了
      this.stopLoadFlag = true;
      for (let i = 0; i < this.renderTimer.length; i++) {
        clearTimeout(this.renderTimer[i]);
        this.renderTimer[i] = null;
      }
      //更新滑轮位置
      this.$refs.messages.scrollTop = this.$refs.messages.scrollHeight;
    },
    changeBar(key) {
      if (this.isLoading) {
        return;
      }
      this.chat = true;
      this.havePammentCode = 0;
      this.current = key;
      this.haveChat = this.chatLength[this.current].length == 0 ? false : true;
      const _self = this;
      async function renderMathSymbol() {
        return new Promise((resolve) => {
          _self.$forceUpdate();
          resolve();
        }).then(() => {
          renderByMathjax(document.getElementById("chatul"));
        });
      }
      renderMathSymbol();
    },
    setExample(data) {
      this.msg = data.message;
      this.submit();
    },
    showPammentCode() {
      if (this.isLoading) {
        return;
      }
      this.havePammentCode = 1;
      this.haveChat = true;
      this.chat = false;
    },
    submit() {
      if (this.msg.length == 0) return;
      //没页码就加一个
      if (this.current == 0 && !Array.isArray(this.chatLength[this.current])) {
        this.addChat();
      }
      //input只读
      this.$refs.input.readOnly = true;
      //信息
      this.$refs.input.placeholder = "暂不可以发送消息";
      //取消付款码
      this.havePammentCode = 0;
      //有聊天
      this.chat = true;
      //取消初始化页面
      this.haveChat = true;
      //停止flag关闭
      this.stopLoadFlag = false;
      console.log(this.chatLength[this.current]);
      this.$set(this.chatLength, this.current, [
        ...this.chatLength[this.current],
        this.msg,
      ]);
      //发请求过程，防止其他操作
      this.isLoading = true;

      const _self = this;
      //防止最初没有bar的时候，防止出现先渲染页面异步执行
      async function renderBar() {
        return new Promise((resolve) => {
          _self.$forceUpdate();
          resolve();
        }).then(() => {
          _self.$refs.barBtn.forEach((item) => {
            item.disabled = true;
            item.style.cursor = "not-allowed";
          });
          _self.$refs.barp.forEach((item) => {
            item.style.cursor = "not-allowed";
          });
        });
      }
      renderBar();
      if (this.$refs.messages && this.$refs.messages.scrollHeight) {
        setTimeout(() => {
          //等待渲染页面完毕之后调整滑轮位置
          this.$refs.messages.scrollTop = this.$refs.messages.scrollHeight;
        }, 90);
      }

      // const arr = [...this.chatLength[this.current]];
      this.chatLength[this.current].push("");
      this.timer = setInterval(() => {
        if (this.dot.length >= 3) {
          this.dot = "";
        }
        this.dot = this.dot + ".";
        // this.chatLength[this.current][
        //   this.chatLength[this.current].length - 1
        // ] = arr + this.dot;
        // Vue.set(
        //   this.chatLength[this.current],
        //   this.chatLength[this.current].length,
        //   this.dot
        // );

        this.chatLength[this.current].pop();
        this.chatLength[this.current].push(this.dot);
        this.$forceUpdate();

        // this.chatLength[this.current] = [...arr, this.dot];

        //   this.chatLength[this.current][
        //     this.chatLength[this.current].length - 1
        //   ] = this.dot;
      }, 300);

      if (this.model == 0 || this.model == 1) {
        let res;
        if (this.model == 0) {
          let msg = this.chatLength[this.current].slice(
            0,
            this.chatLength[this.current].length - 1
          );
          res = http.post("/api/getAnsThreeDotFive", {
            question: msg,
            params: this.params,
          });
        } else {
          let msg;
          if (this.chatLength[this.current].length > 3) {
            msg = this.chatLength[this.current]
              .slice(
                this.chatLength[this.current].length - 3,
                this.chatLength[this.current].length - 1
              )
              .join("###");
          } else {
            msg = this.msg;
          }
          res = http.post("/api/getAnsThree", {
            prompt: msg,
            params: this.params,
          });
        }
        res.then((res) => {
          clearInterval(this.timer);
          this.timer = null;
          this.dot = "";
          //把点点点的显示删除掉
          this.chatLength[this.current].pop();
          //循环添加文字实现闪现效果
          // MarkdownIt.render(res.data.data);
          let pageInfo = marked(res.data.data);
          this.chatLength[this.current].push("");
          const _self = this;
          //停止图标出现
          this.showStopIcon = true;
          //炫酷渲染页面
          async function asyncForEach() {
            return new Promise(() => {
              for (let i = 0; i < pageInfo.length; i++) {
                if (_self.stopLoadFlag) return;
                _self.renderTimer.push(
                  setTimeout(() => {
                    if (_self.stopLoadFlag) return;
                    let arr = _self.chatLength[_self.current];
                    arr = arr[arr.length - 1] += pageInfo.charAt(i);
                    _self.chatLength[_self.current].pop();
                    _self.chatLength[_self.current].push(arr);
                    // _self.$set(
                    //   _self.chatLength,
                    //   _self.current,
                    //   _self.chatLength[_self.current].concat(arr)
                    // );
                    _self.$forceUpdate();
                    // _self.chatLength[_self.current] = [arr];

                    if (i === pageInfo.length - 1) {
                      _self.isLoading = false;
                      _self.showStopIcon = false;
                      _self.$refs.input.readOnly = false;
                      _self.$refs.input.placeholder = "发送消息";
                      //更新滑轮位置
                      _self.$refs.messages.scrollTop =
                        _self.$refs.messages.scrollHeight;
                      //将bar改变为可用
                      _self.$refs.barBtn.forEach((item) => {
                        item.disabled = false;
                        item.style.cursor = "pointer";
                      });
                      _self.$refs.barp.forEach((item) => {
                        item.style.cursor = "pointer";
                      });
                      _self.$nextTick(function () {
                        renderByMathjax(document.getElementById("chatul"));
                        //获取页面上面pre标签添加复制的标签
                        const pres = document.getElementsByTagName("pre");
                        console.log(pres);
                        for (let i = 0; i < pres.length; i++) {
                          const pre = pres[i];
                          console.log(pre);
                          pre.setAttribute("id", "pre" + i);
                          // 添加复制按钮
                          const div = document.createElement("div");
                          const button = document.createElement("button");
                          const span = document.createElement("span");
                          div.setAttribute("class", "copy-button-container");
                          div.appendChild(button);
                          button.className = "copy-button";
                          button.setAttribute(
                            "data-clipboard-target",
                            "#pre" + i
                          );
                          span.className = "iconfont";
                          span.style.zIndex = 99;
                          span.style.textDecoration = "none";
                          span.innerHTML = "&#xe8c9;";
                          button.appendChild(span);
                          pre.parentNode.insertBefore(div, pre);
                          // 实现复制功能
                          new ClipboardJS(button);
                        }
                      });

                      _self.sumbitData();
                    }
                  }, i * _self.formatSpeed)
                );
              }
            }).then(() => {});
          }
          asyncForEach();
        });
      } else if (this.model == 2) {
        let res = http.post("/api/getImage", {
          prompt: this.msg,
          params: this.params,
        });
        res.then((res) => {
          clearInterval(this.timer);
          this.timer = null;
          this.dot = "";
          //把点点点的显示删除掉
          this.chatLength[this.current].pop();
          this.$forceUpdate();
          //循环添加文字实现闪现效果
          let imgUrls = res.data.data;
          let imgUrl = "";
          for (let i = 0; i < imgUrls.length; i++) {
            imgUrl +=
              '<img src="' +
              imgUrls[i] +
              '"  class="pic" alt="" @load="adjustScreen"/>';
          }
          this.chatLength[this.current].push(imgUrl);
          _self.$refs.input.readOnly = false;
          _self.$refs.input.placeholder = "发送消息";
          //更新滑轮位置
          _self.$refs.messages.scrollTop = _self.$refs.messages.scrollHeight;
          //将bar改变为可用
          _self.$refs.barBtn.forEach((item) => {
            item.disabled = false;
            item.style.cursor = "pointer";
          });
          _self.$refs.barp.forEach((item) => {
            item.style.cursor = "pointer";
          });
          this.sumbitData();
          setTimeout(() => {
            //更新滑轮位置
            this.$refs.messages.scrollTop = this.$refs.messages.scrollHeight;
          }, 2000);
        });
      }
      this.msg = "";
    },
    sumbitData() {
      let _self = this;
      //修改当前聊天的页面状态
      http.put("/chatInfo/updatePage", {
        current: _self.current,
        maxKey: _self.maxKey,
      });
      //将bar的信息提交(前提是bar是数字)
      if (typeof _self.barList[_self.current] == "number") {
        //添加bar如果是数字就改了)
        //先遍历前面的看看有没有重复的
        let num = "";
        let initName = _self.chatLength[_self.current][0];
        let name = initName;
        let i = 0;
        let j = 0;
        let flag = true;
        //遍历和前面的比，看看有没有名字
        while (flag) {
          //超出数组就退出
          if (i >= _self.barList.length) {
            flag = false;
          }
          //比较
          if (initName == _self.barList[i]) {
            //num是数字，不能拼接字符串
            num = j + 2;
            initName = name + `(${num})`;
            //有重名的就要重置i
            i = 0;
            //j+1
            j++;
          }
          //没有重名的就比下一个
          i++;
        }
        _self.barList[_self.current] = initName;
        http
          .post("/barList/add", {
            barList: _self.barList[_self.current],
            current: _self.current,
          })
          .then((res) => {
            console.log(res);
            //将产生的新对话提交
            let data = {};
            let keys = Object.keys(_self.chatLength);
            let lastTwoKey = keys.slice(-2);
            let lastTwoObj = {};
            lastTwoKey.forEach((key) => {
              lastTwoObj[key] = _self.chatLength[key];
            });
            data.chatLength = lastTwoObj;
            data.current = _self.current;
            http
              .post("/chatLength/saveChat", {
                ...data,
              })
              .then((res) => {
                console.log(res);
              });
          });
        return;
      }
      //将产生的新对话提交
      let data = {};
      let keys = Object.keys(_self.chatLength);
      let lastTwoKey = keys.slice(-2);
      let lastTwoObj = {};
      lastTwoKey.forEach((key) => {
        lastTwoObj[key] = _self.chatLength[key];
      });
      data.chatLength = lastTwoObj;
      data.current = _self.current;
      http
        .post("/chatLength/saveChat", {
          ...data,
        })
        .then((res) => {
          console.log(res);
        });
    },
    addChat: function () {
      if (this.isLoading) {
        return;
      }
      this.chat = true;
      this.havePammentCode = 0;
      this.haveChat = false;
      this.maxKey++;
      //当前选项加一
      this.current = Object.keys(this.chatLength).length;
      //让当前这个数组成为这个数组索引(标记初始化聊天记录)
      this.chatLength[this.current] = [];
      this.barList.push(this.maxKey);
    },
    clearChatLength() {
      if (this.isLoading) {
        return;
      }
      this.$confirm("此操作将永久删除您的所有对话内容, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.chatLength = {};
          this.current = 0;
          this.haveChat = false;
          this.barList = [];
          this.havePammentCode = 0;
          http.delete("/chatLength/deleteAll").then((res) => {
            console.log(res);
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    changeMode() {
      this.mode = this.mode === "light" ? "dark" : "light";
      if (this.mode === "dark") {
        this.$refs.mode.classList.add("dark-mode");
      } else {
        this.$refs.mode.classList.remove("dark-mode");
      }
    },
    //删除聊天记录
    deleteChat(key) {
      if (this.isLoading) {
        return;
      }
      const _self = this;
      //先更新页面再看是不是有聊天，显示聊天或初识页面
      async function checkHaveChatBefore() {
        return new Promise((resolve) => {
          _self.barList.splice(key, 1);
          _self.$forceUpdate();
          resolve();
        }).then(() => {
          //页面初始化（前一个是空或者前一个长度为零）
          if (key == 0 || _self.chatLength[key - 1].length > 0) {
            _self.haveChat = true;
            return;
          }
          _self.haveChat = false;
        });
      }
      this.$confirm("此操作将永久删除该条记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          http
            .delete(`/chatLength/delete/${this.barList[key]}/${key}`)
            .then((res) => {
              console.log(res);
            });
          this.current--;
          //判断前一个选项是不是有值;
          for (let i = 0; i < Object.keys(this.chatLength).length; i++) {
            if (i >= key) {
              this.chatLength[i] = this.chatLength[i + 1];
            }
          }
          //删除最后一个
          checkHaveChatBefore();
          delete this.chatLength[Object.keys(this.chatLength).length - 1];
          this.$forceUpdate();
          this.$message({
            type: "success",
            message: "删除成功",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
  },
};
</script>

<style>
@font-face {
  font-family: "iconfont";
  src: url("@/assets/fonts/iconfont.woff2?t=1683095443083") format("woff2"),
    url("@/assets/fonts/iconfont.woff?t=1683095443083") format("woff"),
    url("@/assets/fonts/iconfont.ttf?t=1683095443083") format("truetype");
}
.iconfont {
  font-family: "iconfont" !important;
  font-size: 16px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
::-webkit-scrollbar {
  display: none;
}

* {
  padding: 0;
  margin: 0;
  font-family: system-ui;
  list-style: none;
}
.selected {
  background: #696969 !important;
}
.home {
  display: flex;
  height: 100vh;
}

.left {
  width: 260px;
  background-color: #202123;
  min-height: 100vh;
}
.leftBar {
  width: 260px;
  background-color: #202123;
  min-height: 100vh;
  overflow: hidden;
}

.new-chat {
  color: white;
  display: flex;
  border: 1px solid hsla(0, 0%, 100%, 0.2);
  font-size: 14px;
  padding: 0.75rem;
  margin: 8px 8px;
  border-radius: 0.375rem;
  line-height: 1.25rem;
  gap: 0.75rem;
  align-items: center;
  cursor: pointer;
}

.new-chat img {
  filter: invert(1);
  width: 15px;
  margin-left: 1px;
}

.chats {
  display: flex;
  flex-direction: column;
  overflow: auto;
  height: 60vh;
  gap: 18px;
  color: white;
}
.chats li {
  height: 7vh;
}
.chats li button {
  margin: 0%;
  padding: 0;
  padding-left: 30px;
  width: 100%;
  height: 100%;
  border: none;
  font: inherit;
  cursor: pointer;
  outline: none;
  background: #202123;
}
.chats li p {
  box-sizing: border-box;
  padding-right: 0px;
  text-align: left;
  overflow: hidden;
  width: 60%;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.chat {
  display: flex;
  align-items: center;
  gap: 12px;
  cursor: pointer;
  color: white;
}
.barp {
  justify-content: space-between;
}

.barp .edit {
  margin-right: 15px;
}
pre {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  padding-top: 4vh;
  padding-bottom: 4vh;
  padding-right: 3vh;
  padding-left: 3vh;
  overflow-x: scroll;
  background-color: #cacaca;
  border-radius: 10px;
  font-family: consolas, Menlo, "PingFang SC", "Microsoft YaHei", monospace;
}
table {
  width: 100%;
  border-collapse: collapse;
  font-family: Arial, sans-serif;
  font-size: 14px;
}

table th,
table td {
  padding: 8px;
  border: 1px solid #ccc;
}

table th {
  background-color: #f0f0f0;
  font-weight: bold;
}

table tr:nth-child(even) {
  background-color: #f9f9f9;
}

table tr:hover {
  background-color: #e0e0e0;
}

pre span {
  position: relative;
  text-decoration: none !important;
}

.copy-button-container {
  position: relative;
}
.copy-button-container .copy-button {
  display: flex;
  align-items: center;
  justify-content: center;
  position: absolute;
  color: #fff;
  font-weight: bold;
  font-size: 13px;
  right: 0;
  top: 100%;
  background-color: #9742ff;
  color: #000000;
  border-radius: 0 0 0 4px;
  cursor: pointer;
  font-size: 12px;
  width: 4vh;
  height: 3.5vh;
  z-index: 99;
}

.chat p {
  font-size: 14px;
  color: #ececf1;
}
.dialogue {
  margin-top: 2vh;
  width: 100%;
}
.chatul {
  margin-top: 0 !important;
  height: 96vh;
  overflow: auto;
  width: 100%;
  margin: 0% auto;
  padding-bottom: 10vh;
}
.chatli {
  font-weight: 700;
  display: flex;
  margin: 0% auto;
  font-family: "Verdana", "Georgia";
  white-space: pre-line;
  position: relative;
  box-sizing: border-box;
  padding-right: 10vw;
  padding-left: 10vw;
  background: #343541;
  padding-top: 7.5vh;
  padding-bottom: 7.5vh;
  align-items: center;
  width: 100%;
  height: auto;
  word-wrap: break-word;
  word-break: break-all;
  overflow-x: auto;
}
.chatli .pic {
  width: 50%;
}
.chatli .iconfont {
  position: absolute;
  right: 15%;
}
.chat img {
  filter: invert(1);
  width: 1rem;
  height: 1rem;
}

.line {
  width: 250px;
  height: 0.9px;
  background-color: rgb(71, 71, 71);
  margin-left: 10px;
  margin-top: 10px;
}

.features {
  font-size: 14px;
  display: flex;
  flex-direction: column;
  gap: 11px;
  color: white;
}

.feature {
  display: flex;
  align-items: center;
  margin-left: 20px;
  margin-top: 16px;
  gap: 10px;
  cursor: pointer;
}

.feature img {
  filter: invert(1);
  width: 1.05rem;
  height: 1.05rem;
}

.new {
  margin-left: 35px;
  background-color: #f7e39c;
  color: rgb(52, 53, 65);
  border-radius: 0.375rem;
  padding: 0.125rem 0.375rem;
  font-size: 0.75rem;
  line-height: 1rem;
  font-weight: 500;
}

.user {
  width: 1.5rem !important;
  height: 1.5rem !important;
  margin-left: -4px;
}

.right h1 {
  font-size: 36px;
  color: #343541;
  font-weight: 600;
}

.example button {
  display: block;
}

.userImage {
  width: 1.5rem;
  height: 1.5rem;
  position: absolute;
  border-radius: 10%;
  left: 10%;
  top: 4vh;
}
.right {
  display: flex;
  position: relative;
  flex-direction: column;
  width: 100%;
  height: 100vh;
}
.bar {
  padding: 0 20px;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.dark-mode {
  background-color: #1c1c1d;
  color: white;
}

.home .dark-mode h1 {
  color: white;
}
.home .dark-mode .example button {
  background: #a7a7a7;
  color: white;
}
.home .dark-mode p {
  color: #ffffff;
}
.right h1 {
  margin-top: 5rem;
  width: 100%;
  text-align: center;
}

.input-text {
  display: flex;
  position: absolute;
  bottom: 0vh;
  width: 100%;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  margin-bottom: 15px;
}
.input-text .oneLine {
  display: flex;
  width: 100%;
  max-width: 800px;
  justify-content: center;
  align-items: center;
}
.send,
.option {
  margin: 0 15px;
}

.stopIcon {
  display: flex;
  cursor: pointer;
  font-size: 9px;
  padding: 0 5px;
  height: 30px;
  width: 60px;
  background: #9e9e9e;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
}
.input-text textarea {
  line-height: 24px;
  padding-right: 10vw;
  width: 85%;
  padding: 10px 15px;
  height: 24px;
  border: 1px solid rgb(233, 233, 233);
  border-radius: 0.375rem;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
  outline: none;
  resize: none;
}
textarea::-webkit-scrollbar {
  display: none; /* 隐藏滚动条 */
}

.input-text input:focus {
  outline: none;
}

.input-text p {
  font-size: 12px;
  margin-top: -5px;
  color: rgb(135, 135, 135);
  text-align: center;
}

span {
  text-decoration: underline;
}
/* 移动端 */
@media (max-width: 700px) {
  .left {
    display: none;
    overflow: auto;
  }
  .ChatGPT,
  .examples {
    text-align: center;
    transform: translate(0, -30px);
  }
  .right {
    padding: 0;
    margin: 0;
    margin-top: -30px;
    height: 110vh;
    overflow: hidden;
  }
  .home {
    flex-direction: column;
  }
  .examples {
    flex-direction: column;
  }
  .example {
    max-width: 500px;
  }
  input {
    min-width: 90%;
    overflow: hidden;
  }
  .input-text p {
    line-height: 17px;
    margin-bottom: 15px;
  }
  .input-text .oneLine {
    padding-bottom: 10px;
    transform: translate(0, -6px);
  }
  textarea {
    width: 80%;
  }
  .input-text {
    bottom: -7vh;
  }
  .example-img {
    margin-top: 20px;
  }
  .el-message {
    min-width: 300px !important;
  }
  .el-message-box {
    width: 300px !important;
  }
  .el-dialog__wrapper .el-dialog {
    width: 300px !important;
  }
  .el-dialog__body {
    padding: 10px 20px !important;
  }
  .el-form-item__label {
    width: 68px !important;
  }
  .el-select,
  .el-input {
    width: 180px !important;
  }
  .el-input__inner {
    width: 145% !important;
    /* word-wrap: break-word !important; */
  }
}
/* pc端 */
@media (min-width: 800px) {
  .input-text textarea {
    padding-right: 30px;
  }
  .input-text img {
    left: 15px;
  }
  .input-text p {
    margin-top: 18px;
  }
  .chatli img {
    max-width: 250px;
  }
  .line {
    /* transform: translate(0px, -5vh); */
  }
  .el-input input {
    width: 100%;
  }
  .feature {
    /* transform: translate(0, -5vh); */
  }
}
</style>