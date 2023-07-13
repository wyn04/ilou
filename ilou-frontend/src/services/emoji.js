import myAxios from "../plugins/myAxios";
import qs from "qs";

/**
 * 搜索图片
 * @param {*} searchParams
 * @returns
 */
export const searchEmojis = (searchParams = {}) => {
    return myAxios.get('/emoji/search', {
        params: searchParams,
        paramsSerializer: {
            serialize: params => {
                return qs.stringify(params, { indices: false })
            }
        }
    })
        .then(res => {
            console.log('searchEmojis succeed', res);

            for (let i = 0; i < res.data.data.records.length; i++) {
                res.data.data.records[i].tags = JSON.parse(res.data.data.records[i].tags);
            }
            return res.data.data;
        })
        .catch(function (error) {
            console.error('searchEmojis error', error);
            return null;
        });
}

/**
 * 搜索图片
 * @param {*} searchParams
 * @returns
 */
export const searchAllEmojis = () => {
    return myAxios.get('/emoji/searchAll', {})
        .then(res => {
            console.log('searchEmojis succeed', res);

            for (let i = 0; i < res.data.data.length; i++) {
                res.data.data[i].tags = JSON.parse(res.data.data[i].tags);
            }
            return res.data.data;
        })
        .catch(function (error) {
            console.error('searchEmojis error', error);
            return null;
        });
}

/**
 * 增加图片
 * @param {*} emoji
 */
export const addEmoji = (emoji) => {
    if (!emoji) {
        return false;
    }
    if (!emoji.name || !emoji.url) {
        return false;
    }
    if (emoji.tags) {
        emoji.tags = JSON.stringify(emoji.tags);
    }
    return myAxios.post('/emoji/add', emoji)
        .then(res => {
            console.log('addEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('addEmoji error', error);
            return false;
        });
}

/**
 * 上传表情文件
 *
 * @param file
 * @returns {Promise<AxiosResponse<FormData> | boolean>}
 */
export const uploadEmoji = (file) => {
    const formData = new FormData();
    formData.append('file', file);
    return myAxios.post('/emoji/upload', formData)
        .then(res => {
            console.log('uploadEmoji succeed', res);
            return res.data.data;
        })
        .catch(function (error) {
            console.error('uploadEmoji error', error);
            return false;
        });
}

/**
 * 审核图片
 * @param {*} id
 * @param reviewStatus
 * @returns
 */
export const reviewEmoji = (id, reviewStatus) => {
    if (id <= 0 || reviewStatus < 0) {
        return false;
    }
    return myAxios.post('/emoji/review', {
        id,
        reviewStatus,
    })
        .then(res => {
            console.log('reviewEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('reviewEmoji error', error);
            return false;
        });
}

/**
 * 删除图片
 * @param {*} id
 * @returns
 */
export const deleteEmoji = (id) => {
    return myAxios.post('/emoji/delete', {
        id,
    })
        .then(res => {
            console.log('deleteEmoji succeed', res);
            return res.data;
        })
        .catch(function (error) {
            console.error('deleteEmoji error', error);
            return false;
        });
}