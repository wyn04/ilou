import myAxios from "../plugins/myAxios";

/**
 * 获取所以标签
 * @param
 * @returns
 */
export const getAllTags = () => {
    return myAxios.get('/tag/getAll', {
    })
        .then(res => {
            console.log('getAllTags succeed', res);
            return res.data.data;
        })
        .catch(function (error) {
            console.error('getAllTags error', error);
            return null;
        });
}
