<template>
  <a-table :columns="columns" :data-source="data">
    <template #bodyCell="{ column, record }">
      <template v-if="column.key === 'url'">
        <a-image
            :width="150"
            :src="record.url"
        />
      </template>
      <template v-else-if="column.key === 'tags'">
        <span>
          <a-tag
              v-for="tag in record.tags"
              :key="tag"
              :color="'green'"
          >
        {{ tag.toUpperCase() }}
      </a-tag>
        </span>
      </template>
      <template v-else-if="column.key === 'reviewStatus'">
        <div v-if="record.reviewstatus === 0">
          待审核
        </div>
        <div v-if="record.reviewstatus === 1">
          通过
        </div>
        <div v-if="record.reviewstatus === 2">
          拒绝
        </div>
      </template>
      <template v-else-if="column.key === 'action'">
        <span>
          <a-button type="link" @click="doReviewPass(record.id)">通过</a-button>
          <a-divider type="vertical"/>
          <a-button type="link" @click="doReviewReject(record.id)">拒绝</a-button>
          <a-divider type="vertical"/>
          <a-button type="link" @click="doDelete(record.id)">删除</a-button>
        </span>
      </template>
    </template>
<!--    <template #picture="{text}">-->
<!--      <a-image-->
<!--          :width="150"-->
<!--          :src="text"-->
<!--      />-->
<!--    </template>-->
<!--    <template #tag="{record}">-->
<!--      <a-tag-->
<!--          v-for="tag in record.tags"-->
<!--          :key="tag"-->
<!--          :color="'green'"-->
<!--      >-->
<!--        {{ tag.toUpperCase() }}-->
<!--      </a-tag>-->
<!--    </template>-->
<!--    <template #reviewStatus="{record}">-->
<!--      <div v-if="record.reviewstatus === 0">-->
<!--        待审核-->
<!--      </div>-->
<!--      <div v-if="record.reviewstatus === 1">-->
<!--        通过-->
<!--      </div>-->
<!--      <div v-if="record.reviewstatus === 2">-->
<!--        拒绝-->
<!--      </div>-->
<!--    </template>-->
<!--    <template #action="{ record }">-->
<!--      <span>-->
<!--        <a-button type="link" @click="doReviewPass(record.id)">通过</a-button>-->
<!--        <a-divider type="vertical"/>-->
<!--        <a-button type="link" @click="doReviewReject(record.id)">拒绝</a-button>-->
<!--        <a-divider type="vertical"/>-->
<!--        <a-button type="link" @click="doDelete(record.id)">删除</a-button>-->
<!--      </span>-->
<!--    </template>-->
  </a-table>
</template>
<script setup>
import {defineComponent, onMounted, ref} from 'vue';
import {message} from 'ant-design-vue';
import {deleteEmoji, reviewEmoji, searchAllEmojis, searchEmojis} from "../../services/emoji";
import EmojiReviewStatusEnum from "../../constant/EmojiReviewStatusEnum.js";
// import EmojiReviewStatusEnum from "@/constant/EmojiReviewStatusEnum";

const columns = [
  {
    title: 'id',
    dataIndex: 'id',
    key: 'id',
  },
  {
    title: '名称',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '图片',
    dataIndex: 'url',
    key: 'url',
  },
  {
    title: '标签',
    key: 'tags',
    dataIndex: 'tags',
  },
  {
    title: '上传者',
    key: 'userId',
    dataIndex: 'userid',
  },
  {
    title: '状态',
    key: 'reviewStatus',
    dataIndex: 'reviewstatus',
  },
  {
    title: '操作',
    key: 'action',
    fixed: 'right',
  },
];
const data = ref([]);
const total = ref(0);

onMounted(async () => {
  const res = await searchAllEmojis();
  data.value = res;
})

/**
 * 审核通过
 * @param id
 */
const doReviewPass = async (id) => {
  const res = await reviewEmoji(id, EmojiReviewStatusEnum.REVIEW_PASS);
  if (res) {
    message.success('操作成功');
    const res = await searchAllEmojis();
    data.value = res;
  } else {
    message.error('操作失败');
  }
}

/**
 * 审核拒绝
 * @param id
 */
const doReviewReject = async (id) => {
  const res = await reviewEmoji(id, EmojiReviewStatusEnum.REVIEW_REJECT);
  if (res) {
    message.success('操作成功');
    const res = await searchAllEmojis();
    data.value = res;
  } else {
    message.error('操作失败');
  }
}

/**
 * 删除
 * @param id
 * @returns {Promise<void>}
 */
const doDelete = async (id) => {
  const res = await deleteEmoji(id);
  if (res) {
    message.success('操作成功')
    const res = await searchAllEmojis();
    data.value = res;
  } else {
    message.error('操作失败');
  }
}
</script>