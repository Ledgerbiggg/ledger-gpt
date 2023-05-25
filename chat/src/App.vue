<template>
  <div id="app">
    <LedgerChat />
  </div>
</template>

<script>
import LedgerChat from "./components/LedgerChat.vue";

export default {
  name: "App",
  components: {
    LedgerChat,
  },
};
</script>

<style>
@media screen and (min-width: 768px) {
  html {
    height: 100vh;
    overflow: hidden;
    /* transform: translate(0, 24px); */
  }
}
</style>
