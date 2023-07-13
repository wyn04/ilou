<template>
  <div :style="{ padding: '24px', background: '#fff', minHeight: '360px' }">
    <a-row justify="center" align="top" :style="{ margin: '0px 0px 23px' }">
      <a-col :span="20">
        <a-input v-model:value="searchValue" placeholder="输入名字" />
        <a-select
            v-model:value="labelValue"
            mode="multiple"
            style="width: 100%"
            placeholder="选择标签"
            :options="tagList"
        ></a-select>
      </a-col>

      <a-col span="2">
        <a-button type="primary" size="large" @click="handleSearch" block="true" style="height: 65px">
          <template #icon><SearchOutlined /></template>
        </a-button>
      </a-col>
    </a-row>
    <a-list
        :grid="{ gutter: 16, xs: 2, sm: 2, md: 3, lg: 3, xl: 6, xxl: 6, xxxl: 6 }"
        :data-source="pictureList"
        :style="{ margin: '0px 0px 23px' }"
    >
      <template #renderItem="{ item }" >
        <a-card hoverable style="padding: 10px">
          <template #cover>
            <img
                alt="example"
                :src="item.url"
            />
          </template>
          <template #actions>
            <download-outlined @click="doDownload(item)"/>
            <like-outlined v-if="!item.isLiked" @click="()=>{item.isLiked=true}"/>
            <like-filled v-if="item.isLiked" @click="()=>{item.isLiked=false}"/>
            <wechat-outlined @click="copyUrl(item.url)"/>
          </template>
          <a-card-meta :title="item.name">
            <template #description>
              <a-tag v-for="tag in item.tags" plain type="primary">{{tag}}</a-tag>
            </template>
          </a-card-meta>
        </a-card>
      </template>
    </a-list>
    <a-pagination
        v-model:current="current"
        v-model:page-size="pageSize"
        :total="total"
        show-quick-jumper
        style="text-align: center"
        @change="doPageChange"
    />
    <a-back-top/>
  </div>
</template>

<script setup lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import { SearchOutlined, CloudUploadOutlined } from '@ant-design/icons-vue';
import IndexPage from "../pages/IndexPage.vue";
import {message, SelectProps} from "ant-design-vue";
import { LikeFilled, DownloadOutlined, WechatOutlined, LikeOutlined } from '@ant-design/icons-vue';
import EmojiReviewStatusEnum from "../constant/EmojiReviewStatusEnum.js";
import {searchEmojis} from "../services/emoji.js";
import {saveAs} from 'file-saver';
import {getAllTags} from "../services/tag";


const searchValue = ref<string>('');
const labelValue = ref([]);
const loading = ref<boolean>(false);

const current = ref(1);
const pageSize = ref(12);
const total = ref(0);

const tagList = ref<SelectProps['options']>([]);

const copyContent = ref("");

// const mockPicture = {
//   name: "loulou",
//   url: 'https://www.qubiaoqing.cn/pic/2020/08/27/wojlzmmqcxq.jpg',
//   tags: ['java', 'c++', 'emo', 'cute'],
//   isLiked: false,
// };
//
// const mockPicture2 = {
//   name: "loulou2",
//   url: 'https://www.qubiaoqing.cn/pic/2020/08/27/wojlzmmqcxq.jpg',
//   tags: ['cute'],
// };

let pictureList = ref([]);

onMounted(async () => {
  loading.value = true;
  current.value = 1;
  const emojiData = await searchEmojis({
    name: searchValue.value,
    tags: labelValue.value,
    reviewStatus: EmojiReviewStatusEnum.REVIEW_PASS,
    pageSize: pageSize.value,
    current: current.value,
  });
  if (emojiData) {
    pictureList.value = emojiData.records;
    current.value = emojiData.current;
    total.value = emojiData.total;
  } else {
    alert('获取表情失败，请重试');
  }
  loading.value = false;
  const tagData = await getAllTags();
  if (tagData) {
    for (let i = 0; i < tagData.length; i++) {
      tagList.value.push({
        value: tagData[i],
        label: tagData[i],
      })
    }
  } else {
    console.log('获取标签失败，请重试');
  }
})

const handleSearch = async () => {
  loading.value = true;
  current.value = 1;
  const data = await searchEmojis({
    name: searchValue.value,
    tags: labelValue.value,
    reviewStatus: EmojiReviewStatusEnum.REVIEW_PASS,
    pageSize: pageSize.value,
    current: current.value,
  });
  if (data) {
    pictureList.value = data.records;
    current.value = data.current;
    total.value = data.total;
  } else {
    alert('获取表情失败，请重试');
  }
  loading.value = false;
};

const doPageChange = async () => {
  loading.value = true;
  const data = await searchEmojis({
    name: searchValue.value,
    tags: labelValue.value,
    reviewStatus: EmojiReviewStatusEnum.REVIEW_PASS,
    pageSize: pageSize.value,
    current: current.value,
  });
  if (data) {
    pictureList.value = data.records;
    current.value = data.current;
    total.value = data.total;
  } else {
    alert('获取表情失败，请重试');
  }
  loading.value = false;
}

const doDownload = (picture: object) => {
  // // 首先创建一个a元素
  // let a = document.createElement('a')
  // // 让a元素的href地址为图片地址
  // a.href = picture.url
  // // 设置图片名
  // a.download = picture.name+'.jpg'
  // document.body.appendChild(a) // 添加a标签到body下
  // a.click() // 触发a标签点击事件
  // document.body.removeChild(a) //  完成后删除a标签
  if (picture?.url) {
    saveAs(picture.url, picture.name+".jpg");
  }
};

// const copyUrl = (url : string) => {
//   this.$copyText()
// };

const handleCopy = (e: ClipboardEvent) => {
  // clipboardData 可能是 null
  e.clipboardData && e.clipboardData.setData('text/plain', copyContent.value);
  e.preventDefault();
  // removeEventListener 要传入第二个参数
  document.removeEventListener('copy', handleCopy);
};

const copyUrl= (url : string) => {
  copyContent.value = url;
  document.addEventListener('copy', handleCopy);
  document.execCommand('copy');
  message.success("图片链接已复制，快去粘贴给好友！")
}

</script>

<style scoped>

</style>