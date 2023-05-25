<template></template>

<script>
export default {
  name: "LeftChats",
  data() {
    return {};
  },
  methods: {},
};
</script>

<style>
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
</style>