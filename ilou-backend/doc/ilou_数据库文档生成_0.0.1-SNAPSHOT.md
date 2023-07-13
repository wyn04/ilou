# 数据库文档

**数据库名：** ilou

**文档版本：** 0.0.1-SNAPSHOT

**文档描述：** 数据库文档生成
| 表名                  | 说明       |
| :-------------------- | :--------- |
| [emoji](#emoji) | 表情 |
| [tag](#tag) | 标签 |
**表名：** <a id="emoji">emoji</a>

**说明：** 表情

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | url |   varchar   | 4096 |   0    |    Y     |  N   |       |   |
|  3   | name |   varchar   | 512 |   0    |    Y     |  N   |       |   |
|  4   | userId |   int   | 10 |   0    |    Y     |  N   |   0    | 上传用户id  |
|  5   | tags |   varchar   | 1024 |   0    |    Y     |  N   |       | 标签数组json  |
|  6   | reviewStatus |   int   | 10 |   0    |    N     |  N   |   0    | 0-待审核,1-通过,2-拒绝  |
|  7   | createTime |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    |   |
|  8   | isDelete |   tinyint   | 3 |   0    |    N     |  N   |   0    | 是否删除0-未删除1-已删除  |
|  9   | updateTime |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    |   |
**表名：** <a id="tag">tag</a>

**说明：** 标签

**数据列：**

| 序号 | 名称 | 数据类型 |  长度  | 小数位 | 允许空值 | 主键 | 默认值 | 说明 |
| :--: | :--- | :------: | :----: | :----: | :------: | :--: | :----: | :--: |
|  1   | id |   int   | 10 |   0    |    N     |  Y   |       |   |
|  2   | name |   varchar   | 512 |   0    |    N     |  N   |       | 标签名  |
|  3   | createTime |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    |   |
|  4   | isDelete |   tinyint   | 3 |   0    |    N     |  N   |   0    | 是否删除0-未删除1-已删除  |
|  5   | updateTime |   datetime   | 19 |   0    |    Y     |  N   |   CURRENT_TIMESTAMP    |   |
