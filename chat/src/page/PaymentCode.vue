<template>
  <div class="inspried">
    <h1>激励开发者</h1>
    <div class="pay">
      <img
        class="vxpay"
        src="https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/202305031548763.jpg"
        alt=""
      />
      <img
        class="alipay"
        src="https://image-bed-for-ledgerhhh.oss-cn-beijing.aliyuncs.com/image/202305031548750.jpg"
        alt=""
      />
    </div>
    <p class="detail">
      网站开发不易，服务器费用昂贵，接口费用昂贵，如果觉得做的还可以的话，可以帮我买一瓶冰可乐,每人每个月三元，我就能一直做下去，并且开发出更好的服务
    </p>
    <p class="detail">谢谢你光临我的网站</p>
    <footer>
      <div>
        禁止发布、传播任何违法、违规内容，使用本网站，视您接受并同意
        <a
          href="https://docs.qq.com/doc/DR3JxU1F5YUJ2TUpF?u=7dc188e301c2436dbff680f7fb3a7655"
          >《免责声明》</a
        >
      </div>
    </footer>
  </div>
</template>

<script>
export default {
  name: "PaymentCode",
};
</script>

<style>
.inspried {
  position: relative;
  height: 100%;
}
.pay {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}
.vxpay,
.alipay {
  width: 15%;
}
footer,
.detail {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 3vh;
}
footer {
  width: 100%;
  position: absolute;
  bottom: 15vh;
}
</style>