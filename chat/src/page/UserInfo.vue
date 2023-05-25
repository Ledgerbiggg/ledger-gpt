<template>
  <div class="userInfo">
    <div class="userInfo-header">用户信息</div>
    <div class="userInfo-content">
      <div
        class="user-Icon"
        :style="{ backgroundImage: 'url(' + userInfo.backgroundImageUrl + ')' }"
      >
        <img :src="userInfo.avatarUrl" alt="" />
      </div>
      <div class="info">
        <div class="info-name ion">
          <b>姓名:</b>
          <span>{{ userInfo.name }}</span>
          <font-awesome-icon
            :icon="['fas', 'pen-to-square']"
            v-show="isEdit"
            class="editPen"
            @click="editName"
          />
        </div>
        <div class="info-phone ion">
          <b>性别:</b>
          <span style="color: #ff4ede" v-show="userInfo.gender == 0"
            ><font-awesome-icon :icon="['fas', 'venus']"
          /></span>
          <span style="color: #146aff" v-show="userInfo.gender == 1"
            ><font-awesome-icon :icon="['fas', 'mars']"
          /></span>
          <font-awesome-icon
            :icon="['fas', 'pen-to-square']"
            v-show="isEdit"
            class="editPen"
            @click="editGender"
          />
        </div>
        <div class="info-email ion">
          <b>年龄:</b>
          <span>{{ userInfo.age }}</span>
          <font-awesome-icon
            :icon="['fas', 'pen-to-square']"
            v-show="isEdit"
            class="editPen"
            @click="editAge"
          />
        </div>
        <div class="info-description ion">
          <b style="width: 40px">简介:</b>
          <div class="description">{{ userInfo.description }}</div>
          <font-awesome-icon
            :icon="['fas', 'pen-to-square']"
            v-show="isEdit"
            class="editPen"
            @click="editDescription"
          />
        </div>
        <div class="info-description ion" v-show="isEdit">
          <b style="width: 40px; margin-right: 15px">背景图片</b>
          <div class="background">
            <el-upload
              class="avatar-uploader"
              :action="dataObj.host"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeUpload"
              :data="dataObj"
              :multiple="false"
              :on-error="handleAvatarError"
              list-type="picture"
            >
              <img
                v-if="backgroundImageUrl"
                :src="backgroundImageUrl"
                class="avatar"
              />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
        </div>
        <div class="info-description ion" v-show="isEdit">
          <b style="width: 40px; margin-right: 15px">头像</b>
          <div class="background">
            <el-upload
              class="avatar-uploader"
              :action="dataObj.host"
              :show-file-list="false"
              :on-success="handleAvatarSuccess1"
              :before-upload="beforeUpload1"
              :data="dataObj"
              :multiple="false"
              :on-error="handleAvatarError"
              list-type="picture"
            >
              <img v-if="avatarUrl" :src="avatarUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </div>
        </div>
      </div>
      <div class="edit">
        <button class="btnled17" @click="edit">
          {{ isEdit ? "完成" : "修改资料" }}
        </button>
        <button class="btnled17" @click="isEdit = false" v-if="isEdit">
          取消
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import "@/assets/css/button.css";
import http from "@/api/http";
export default {
  name: "UserInfo",
  data() {
    return {
      userInfo: {
        name: "ledger",
        age: 18,
        gender: 1,
        description: "ledgerbiggg",
        avatarUrl: "",
        backgroundImageUrl: "",
      },
      isEdit: false,
      dataObj: {
        policy: "",
        signature: "",
        key: "",
        OSSAccessKeyId: "",
        dir: "",
        host: "",
      },
      backgroundImageUrl: "",
      avatarUrl: "",
    };
  },
  created() {
    http.get("/user/getInfo").then((res) => {
      this.userInfo = res.data.data;
    });
  },
  methods: {
    handleAvatarError() {
      this.$message.error("上传失败，请联系管理员vx：Ledgerbiggg");
    },
    edit() {
      if (this.isEdit == true) {
        //没有上传头像或者背景就直接提示
        // if (this.avatarUrl == "" || this.backgroundImageUrl == "") {
        //   this.$message({
        //     type: "waring",
        //     message: "请上传头像或者背景图片",
        //   });
        //   return;
        // }
        this.$message({
          type: "success",
          message: "修改信息成功",
        });
        if (this.avatarUrl != "") {
          this.userInfo.avatarUrl = this.avatarUrl;
        }
        if (this.backgroundImageUrl != "") {
          this.userInfo.backgroundImageUrl = this.backgroundImageUrl;
        }
        this.avatarUrl = "";
        this.backgroundImageUrl = "";
        this.$emit("changeAvatar", this.userInfo.avatarUrl);
        http.post("/user/editInfo", this.userInfo).then((res) => {
          console.log(res);
        });
      }
      this.isEdit = !this.isEdit;
    },
    editName() {
      this.$prompt("输入新的名字", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputValue: this.userInfo.name,
        inputPattern: /^.{3,10}$/,
        inputErrorMessage: "名称在3-10字符",
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "修改成功",
          });
          this.userInfo.name = value;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
    editGender() {
      this.userInfo.gender = 1 - this.userInfo.gender;
    },
    editAge() {
      this.$prompt("输入新的年龄", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^(?:[1-9][0-9]?|1[01][0-9]|120)$/,
        inputValue: this.userInfo.age,
        inputErrorMessage: "年龄错误",
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "修改成功",
          });
          this.userInfo.age = value;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
    editDescription() {
      this.$prompt("输入新的描述", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^.{10,30}$/,
        inputValue: this.userInfo.description,
        inputErrorMessage: "10-30字符",
      })
        .then(({ value }) => {
          this.$message({
            type: "success",
            message: "修改成功",
          });
          this.userInfo.description = value;
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
    },
    beforeUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      if (!isJPG && !isPNG) {
        this.$message.error("只能上传JPG或PNG图像文件");
        return false;
      }
      return new Promise((resolve, reject) => {
        http
          .get("/oss/getSign/" + 0)
          .then((res) => {
            // this.dataObj = res.data.data;
            this.dataObj.policy = res.data.data.policy;
            this.dataObj.signature = res.data.data.signature;
            this.dataObj.dir = res.data.data.dir;
            this.dataObj.host = res.data.data.host;
            this.dataObj.OSSAccessKeyId = res.data.data.accessid;
            this.dataObj.key = res.data.data.dir + `${file.name}`;
            resolve(true);
          })
          .catch((e) => {
            console.log(e);
            reject(false);
          });
      });
    },
    handleAvatarSuccess(res, file) {
      console.log(res);
      console.log(file);
      let url = this.dataObj.host + "/" + this.dataObj.key;
      this.backgroundImageUrl = url;
    },
    beforeUpload1(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      if (!isJPG && !isPNG) {
        this.$message.error("只能上传JPG或PNG图像文件");
        return false;
      }
      return new Promise((resolve, reject) => {
        http
          .get("/oss/getSign/" + 1)
          .then((res) => {
            // this.dataObj = res.data.data;
            this.dataObj.policy = res.data.data.policy;
            this.dataObj.signature = res.data.data.signature;
            this.dataObj.dir = res.data.data.dir;
            this.dataObj.host = res.data.data.host;
            this.dataObj.OSSAccessKeyId = res.data.data.accessid;
            this.dataObj.key = res.data.data.dir + `${file.name}`;
            resolve(true);
          })
          .catch((e) => {
            console.log(e);
            reject(false);
          });
      });
    },
    handleAvatarSuccess1(res, file) {
      console.log(res);
      console.log(file);
      let url = this.dataObj.host + "/" + this.dataObj.key;
      this.avatarUrl = url;
    },
  },
};
</script>

<style scoped>
.userInfo {
  overflow: hidden;
  width: 300px;
  z-index: 9999;
}
.userInfo-header {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 12vw;
}
.userInfo-content {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
.user-Icon {
  position: relative;
  display: flex;
  align-content: center;
  justify-content: center;
  width: 100%;
  height: 20vh;
  background-position: center;
  background-size: cover;
}
.user-Icon img {
  position: absolute;
  width: 23vw;
  height: 23vw;
  border-radius: 50%;
  bottom: -40px;
  right: 10px;
}
.description {
  margin-left: 15px;
  display: block;
  width: 50%;
  word-wrap: break-word;
}
.info {
  margin-top: 6vw;
  width: 100%;
  height: 60vh;
  overflow: auto;
  overflow-x: hidden;
}
.info .editPen {
  position: absolute;
  right: 73px;
  top: 0;
}
.el-icon-plus:before {
  display: inline-block;
  border: solid #ccc 3px;
  border-style: dashed;
  padding: 30px;
}
.ion {
  position: relative;
  display: flex;
  align-content: center;
  justify-content: flex-start;
  margin-top: 10vw;
  width: 100%;
  padding-left: 13vw;
  width: 300px;
}
.ion b {
  display: block;
  width: 15vw;
}
.edit {
  display: flex;
  justify-content: space-around;
  align-content: center;
  position: absolute;
  bottom: 7vw;
}
.background {
  width: 300px;
}
.background img {
  width: 100px;
}
</style>