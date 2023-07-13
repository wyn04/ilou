<template>
  <a-layout>
    <a-page-header
        title="iLou"
        class="site-page-header"
        sub-title="表情包"
        :avatar="{ src: 'http://qiniu.yinan.fun/icon.jpg', size: 'large' }"
    >
      <!--      <template #tags>-->
      <!--        <a-tag color="blue">ilou</a-tag>-->
      <!--      </template>-->
      <template #extra>
        <a-button type="primary" @click="doShowCreateModal">
          <template #icon>
            <CloudUploadOutlined/>
          </template>
          上传表情
        </a-button>
      </template>
    </a-page-header>
    <a-layout-content :style="{ margin: '0px 16px 0' }">
<!--      <IndexPage/>-->
      <router-view/>
    </a-layout-content>
    <a-layout-footer style="text-align: center">
      iLou ©2023 Created by YiNan
    </a-layout-footer>
  </a-layout>
  <a-modal :visible="showCreateModal" title="表情上传" ok-text="提交" cancel-text="取消" @cancel="doCloseCreateModal" @ok="handleSubmit">
    <a-form :model="emoji" hide-required-mark :colon="false" label-align="left"
            :label-col="{ style: { width: '60px' } }">
      <a-form-item label="名称">
        <a-input v-model:value="emoji_name"/>
      </a-form-item>
      <a-form-item label="标签">
        <a-select
            v-model:value="emoji_tags"
            mode="multiple"
            style="width: 100%"
            placeholder="选择标签"
            :options="tagList"
        >
        </a-select>
      </a-form-item>
      <a-form-item label="图片">
        <a-upload
            v-model:file-list="fileList"
            name="avatar"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :before-upload="beforeUpload"
            :customRequest="doCustomUpload"
        >
          <img v-if="emoji_url" :src="emoji_url" alt="avatar" style="width: 100%"/>
          <div v-else>
            <loading-outlined v-if="imgLoading"/>
            <plus-outlined v-else/>
          </div>
        </a-upload>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { CloudUploadOutlined, PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
import IndexPage from "../pages/IndexPage.vue";
import CreateEmoji from "../components/CreateEmojiModal.vue";
import {onMounted, ref} from "vue";
import {message, SelectProps} from "ant-design-vue";
import {addEmoji, searchEmojis, uploadEmoji} from "../services/emoji.js";
import {getAllTags} from "../services/tag.js"

const showCreateModal = ref<Boolean>(false);
const emoji = ref({});
const emoji_name = ref("");
const emoji_url = ref("");
const emoji_tags = ref([])

const imgLoading = ref(false);

const fileList = ref([])

const tagList = ref<SelectProps['options']>([]);

onMounted(async () => {
  const data = await getAllTags();
  if (data) {
    for (let i = 0; i < data.length; i++) {
      tagList.value.push({
        value: data[i],
        label: data[i],
      })
    }
  } else {
    console.log('获取标签失败，请重试');
  }
})

const doShowCreateModal = () => {
  showCreateModal.value = true;
}
const doCloseCreateModal = () => {
  showCreateModal.value = false;
}

const doCommitCreateModal = () => {
  showCreateModal.value = false;
}

const handleSubmit = async () => {
  if (!emoji_name.value) {
    message.error('请输入图片名称');
    return;
  }
  if (!emoji_url.value) {
    message.error('请上传图片');
    return;
  }
  const res = await addEmoji({
    name: emoji_name.value,
    url: emoji_url.value,
    tags: emoji_tags.value,
  });
  if (res) {
    message.success('提交成功，审核后可见');
    doCloseCreateModal();
    // onSuccess && onSuccess();
  } else {
    message.error('提交失败');
  }
};
const beforeUpload = (file : object) => {
  const isFileTypeValid = ['image/jpeg', 'image/png', 'image/gif'].includes(file.type);
  if (!isFileTypeValid) {
    message.error('只支持上传 jpg / png / gif 格式的图片');
    return false;
  }
  const isLt1M = file.size / 1024 / 1024 < 1;
  if (!isLt1M) {
    message.error('图片大小不能超过 1M');
    return false;
  }
  return true;
};
const doCustomUpload = async (file : object) => {
  imgLoading.value = true;
  const data = await uploadEmoji(file.file);
  if (data) {
    emoji_url.value = data;
  } else {
    message.error('图片上传失败');
  }
  imgLoading.value = false;
};
</script>

<style scoped>

</style>