<template>
  <a-modal :visible="visible" title="表情上传" ok-text="提交" cancel-text="取消" @cancel="onClose" @ok="handleSubmit">
    <a-form :model="emoji" hide-required-mark :colon="false" label-align="left"
            :label-col="{ style: { width: '60px' } }">
      <a-form-item label="名称">
        <a-input v-model:value="emoji.name"/>
      </a-form-item>
      <a-form-item label="标签">
        <a-select
            v-model:value="emoji.tags"
            mode="multiple"
            style="width: 100%"
            placeholder="选择标签"
            :options="tagOptions"
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
          <img v-if="emoji.url" :src="emoji.url" alt="avatar" style="width: 100%"/>
          <div v-else>
            <loading-outlined v-if="loading"/>
            <plus-outlined v-else/>
          </div>
        </a-upload>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import {ref, toRefs} from "vue";

const props = defineProps({
  visible: Boolean,
})
const visible = toRefs(props)
const emoji = ref({});
const onClose = ref(function () {

});

const handleSubmit = (e: MouseEvent) => {
  console.log(e);
  visible.visible.value = false;
};

</script>

<style scoped>

</style>