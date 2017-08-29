prompt PL/SQL Developer import file
prompt Created on 2015年10月13日 星期二 by Administrator
set feedback off
set define off

prompt Loading SYS_CODE...
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (200, 'sys', 1, '系统域名', null, 'http://www.hbctd.com:8888');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (213, 'weixin_message', 2, '微信消息内容模板', null, '%1$s \n %2$tF %3$tT \n 更新了项目：\n %4$s \n 更新内容： \n %5$s');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (107, 'weixin_error', 43010, null, null, '需要处于回调模式');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (110, 'weixin_error', 44001, null, null, '多媒体文件为空');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (113, 'weixin_error', 44004, null, null, '文本消息内容为空');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (116, 'weixin_error', 45003, null, null, '标题字段超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (119, 'weixin_error', 45006, null, null, '图片链接字段超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (122, 'weixin_error', 45009, null, null, '接口调用超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (125, 'weixin_error', 45016, null, null, '系统分组，不允许修改');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (128, 'weixin_error', 45024, null, null, '账号数量超过上限');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (131, 'weixin_error', 45027, null, null, 'mpnews每天只能发送100次');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (134, 'weixin_error', 46001, null, null, '不存在媒体数据');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (137, 'weixin_error', 46004, null, null, '不存在的成员');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (139, 'weixin_error', 48001, null, null, 'Api未授权');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (141, 'weixin_error', 48003, null, null, 'suitetoken无效');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (144, 'weixin_error', 50002, null, null, '成员不在权限范围');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (147, 'weixin_error', 50005, null, null, '企业已禁用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (149, 'weixin_error', 60002, null, null, '部门层级深度超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (152, 'weixin_error', 60005, null, null, '不允许删除有成员的部门');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (156, 'weixin_error', 60009, null, null, '部门名称含有非法字符');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (159, 'weixin_error', 60012, null, null, '不允许删除默认应用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (162, 'weixin_error', 60015, null, null, '不允许修改默认应用可见范围');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (166, 'weixin_error', 60020, null, null, '访问ip不在白名单之中');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (169, 'weixin_error', 60103, null, null, '手机号码不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (171, 'weixin_error', 60105, null, null, '邮箱不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (174, 'weixin_error', 60108, null, null, '微信号已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (176, 'weixin_error', 60110, null, null, '用户同时归属部门超过20个');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (180, 'weixin_error', 60114, null, null, '性别不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (182, 'weixin_error', 60116, null, null, '扩展属性已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (185, 'weixin_error', 60120, null, null, '成员已禁用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (187, 'weixin_error', 60122, null, null, '邮箱已被外部管理员使用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (190, 'weixin_error', 60125, null, null, '非法部门名字，长度超过限制、重名等');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (195, 'weixin_error', 82001, null, null, '发送消息或者邀请的参数全部为空或者全部不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (3, 'weixin_error', 0, null, null, '请求成功');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (4, 'weixin_error', 40001, null, null, '获取access_token时Secret错误，或者access_token无效');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (10, 'weixin_error', 40007, null, null, '不合法的媒体文件id');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (13, 'weixin_error', 40014, null, null, '不合法的access_token');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (16, 'weixin_error', 40017, null, null, '不合法的按钮类型');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (19, 'weixin_error', 40020, null, null, '不合法的按钮URL长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (22, 'weixin_error', 40023, null, null, '不合法的子菜单按钮个数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (24, 'weixin_error', 40025, null, null, '不合法的子菜单按钮名字长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (27, 'weixin_error', 40028, null, null, '不合法的自定义菜单使用成员');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (30, 'weixin_error', 40032, null, null, '不合法的UserID列表长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (33, 'weixin_error', 40038, null, null, '不合法的请求格式');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (35, 'weixin_error', 40040, null, null, '不合法的插件token');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (38, 'weixin_error', 40048, null, null, 'url中包含不合法domain');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (41, 'weixin_error', 40056, null, null, '不合法的agentid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (44, 'weixin_error', 40059, null, null, '不合法的上报地理位置标志位');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (46, 'weixin_error', 40061, null, null, '设置应用头像失败');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (49, 'weixin_error', 40064, null, null, '管理组名字已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (52, 'weixin_error', 40067, null, null, '标题长度不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (55, 'weixin_error', 40070, null, null, '列表中所有标签（成员）ID都不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (57, 'weixin_error', 40072, null, null, '不合法的标签名字长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (60, 'weixin_error', 40077, null, null, '不合法的预授权码');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (63, 'weixin_error', 40080, null, null, '不合法的suitesecret');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (66, 'weixin_error', 40084, null, null, '不合法的永久授权码');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (68, 'weixin_error', 40086, null, null, '不合法的第三方应用appid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (72, 'weixin_error', 41004, null, null, '缺少secret参数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (74, 'weixin_error', 41006, null, null, '缺少media_id参数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (78, 'weixin_error', 41010, null, null, '缺少url');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (80, 'weixin_error', 41012, null, null, '缺少应用头像mediaid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (85, 'weixin_error', 41017, null, null, '缺少标签ID');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (88, 'weixin_error', 41022, null, null, '缺少suitetoken');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (91, 'weixin_error', 41025, null, null, '缺少永久授权码');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (94, 'weixin_error', 42003, null, null, 'oauth_code超时');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (97, 'weixin_error', 42008, null, null, '临时授权码失效');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (100, 'weixin_error', 43002, null, null, '需要POST请求');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (104, 'weixin_error', 43006, null, null, '需要订阅');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (108, 'weixin_error', 43011, null, null, '需要企业授权');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (112, 'weixin_error', 44003, null, null, '图文消息内容为空');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (115, 'weixin_error', 45002, null, null, '消息内容超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (120, 'weixin_error', 45007, null, null, '语音播放时间超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (124, 'weixin_error', 45015, null, null, '回复时间超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (129, 'weixin_error', 45025, null, null, '同一个成员每周只能邀请一次');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (133, 'weixin_error', 45029, null, null, 'media_id对该应用不可见');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (138, 'weixin_error', 47001, null, null, '解析JSON/XML内容错误');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (146, 'weixin_error', 50004, null, null, '成员状态不正确，需要成员为企业验证中状态');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (153, 'weixin_error', 60006, null, null, '不允许删除有子部门的部门');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (158, 'weixin_error', 60011, null, null, '管理组权限不足，（user/department/agent）无权限');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (165, 'weixin_error', 60019, null, null, '不允许设置应用地理位置上报开关');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (168, 'weixin_error', 60102, null, null, 'UserID已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (173, 'weixin_error', 60107, null, null, '微信号不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (178, 'weixin_error', 60112, null, null, '成员姓名不合法');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (181, 'weixin_error', 60115, null, null, '已关注成员微信不能修改');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (183, 'weixin_error', 60118, null, null, '成员无有效邀请字段，详情参考(邀请成员关注)的接口说明');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (193, 'weixin_error', 60128, null, null, '字段不合法，可能存在主键冲突或者格式错误');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (197, 'weixin_error', 82003, null, null, '不合法的TagID列表长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (207, 'weixin_message', 1, '日志详细地址', '微信日志，详细地址', '/mobile/project/%%23/detail/%d');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (209, 'weixin_message', 4, '微信日报项目ID，即微信应用的agentId', '具体说明见http://qydev.weixin.qq.com/wiki/index.php?title=%E6%B6%88%E6%81%AF%E7%B1%BB%E5%9E%8B%E5%8F%8A%E6%95%B0%E6%8D%AE%E6%A0%BC%E5%BC%8F', '2');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (211, 'weixin_message', 3, '微信消息标题', null, '%1$s  %2$s');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (242, 'project_user', 1, null, '是否接受消息默认为1  1 接受0不接收', '1');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (243, 'project_user', 2, null, '为负责人                   1 为负责人0为不是负责人', '1');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (244, 'project_user', 3, null, '不为负责人                   1 为负责人0为不是负责人', '0');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (2, 'weixin_error', -1, null, null, '系统繁忙');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (5, 'weixin_error', 40002, null, null, '不合法的凭证类型');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (6, 'weixin_error', 40003, null, null, '不合法的UserID');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (7, 'weixin_error', 40004, null, null, '不合法的媒体文件类型');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (8, 'weixin_error', 40005, null, null, '不合法的文件类型');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (9, 'weixin_error', 40006, null, null, '不合法的文件大小');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (11, 'weixin_error', 40008, null, null, '不合法的消息类型');
commit;
prompt 100 records committed...
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (12, 'weixin_error', 40013, null, null, '不合法的corpid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (14, 'weixin_error', 40015, null, null, '不合法的菜单类型');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (15, 'weixin_error', 40016, null, null, '不合法的按钮个数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (17, 'weixin_error', 40018, null, null, '不合法的按钮名字长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (18, 'weixin_error', 40019, null, null, '不合法的按钮KEY长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (20, 'weixin_error', 40021, null, null, '不合法的菜单版本号');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (21, 'weixin_error', 40022, null, null, '不合法的子菜单级数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (23, 'weixin_error', 40024, null, null, '不合法的子菜单按钮类型');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (25, 'weixin_error', 40026, null, null, '不合法的子菜单按钮KEY长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (26, 'weixin_error', 40027, null, null, '不合法的子菜单按钮URL长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (28, 'weixin_error', 40029, null, null, '不合法的oauth_code');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (29, 'weixin_error', 40031, null, null, '不合法的UserID列表');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (31, 'weixin_error', 40033, null, null, '不合法的请求字符，不能包含\uxxxx格式的字符');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (32, 'weixin_error', 40035, null, null, '不合法的参数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (34, 'weixin_error', 40039, null, null, '不合法的URL长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (36, 'weixin_error', 40041, null, null, '不合法的插件id');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (37, 'weixin_error', 40042, null, null, '不合法的插件会话');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (39, 'weixin_error', 40054, null, null, '不合法的子菜单url域名');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (40, 'weixin_error', 40055, null, null, '不合法的按钮url域名');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (42, 'weixin_error', 40057, null, null, '不合法的callbackurl');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (43, 'weixin_error', 40058, null, null, '不合法的红包参数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (45, 'weixin_error', 40060, null, null, '设置上报地理位置标志位时没有设置callbackurl');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (47, 'weixin_error', 40062, null, null, '不合法的应用模式');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (48, 'weixin_error', 40063, null, null, '红包参数为空');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (50, 'weixin_error', 40065, null, null, '不合法的管理组名字长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (51, 'weixin_error', 40066, null, null, '不合法的部门列表');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (53, 'weixin_error', 40068, null, null, '不合法的标签ID');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (54, 'weixin_error', 40069, null, null, '不合法的标签ID列表');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (56, 'weixin_error', 40071, null, null, '不合法的标签名字，标签名字已经存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (58, 'weixin_error', 40073, null, null, '不合法的openid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (59, 'weixin_error', 40074, null, null, 'news消息不支持指定为高保密消息');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (61, 'weixin_error', 40078, null, null, '不合法的临时授权码');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (62, 'weixin_error', 40079, null, null, '不合法的授权信息');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (64, 'weixin_error', 40082, null, null, '不合法的suitetoken');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (65, 'weixin_error', 40083, null, null, '不合法的suiteid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (67, 'weixin_error', 40085, null, null, '不合法的suiteticket');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (69, 'weixin_error', 41001, null, null, '缺少access_token参数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (70, 'weixin_error', 41002, null, null, '缺少corpid参数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (71, 'weixin_error', 41003, null, null, '缺少refresh_token参数');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (73, 'weixin_error', 41005, null, null, '缺少多媒体文件数据');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (75, 'weixin_error', 41007, null, null, '缺少子菜单数据');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (76, 'weixin_error', 41008, null, null, '缺少oauth code');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (77, 'weixin_error', 41009, null, null, '缺少UserID');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (79, 'weixin_error', 41011, null, null, '缺少agentid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (81, 'weixin_error', 41013, null, null, '缺少应用名字');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (82, 'weixin_error', 41014, null, null, '缺少应用描述');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (83, 'weixin_error', 41015, null, null, '缺少Content');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (84, 'weixin_error', 41016, null, null, '缺少标题');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (86, 'weixin_error', 41018, null, null, '缺少标签名字');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (87, 'weixin_error', 41021, null, null, '缺少suiteid');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (89, 'weixin_error', 41023, null, null, '缺少suiteticket');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (90, 'weixin_error', 41024, null, null, '缺少suitesecret');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (92, 'weixin_error', 42001, null, null, 'access_token超时');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (93, 'weixin_error', 42002, null, null, 'refresh_token超时');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (95, 'weixin_error', 42004, null, null, '插件token超时');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (96, 'weixin_error', 42007, null, null, '预授权码失效');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (98, 'weixin_error', 42009, null, null, 'suitetoken失效');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (99, 'weixin_error', 43001, null, null, '需要GET请求');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (101, 'weixin_error', 43003, null, null, '需要HTTPS');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (102, 'weixin_error', 43004, null, null, '需要成员已关注');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (103, 'weixin_error', 43005, null, null, '需要好友关系');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (105, 'weixin_error', 43007, null, null, '需要授权');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (106, 'weixin_error', 43008, null, null, '需要支付授权');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (109, 'weixin_error', 43013, null, null, '应用对成员不可见');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (111, 'weixin_error', 44002, null, null, 'POST的数据包为空');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (114, 'weixin_error', 45001, null, null, '多媒体文件大小超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (117, 'weixin_error', 45004, null, null, '描述字段超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (118, 'weixin_error', 45005, null, null, '链接字段超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (121, 'weixin_error', 45008, null, null, '图文消息的文章数量不能超过10条');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (123, 'weixin_error', 45010, null, null, '创建菜单个数超过限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (126, 'weixin_error', 45017, null, null, '分组名字过长');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (127, 'weixin_error', 45018, null, null, '分组数量超过上限');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (130, 'weixin_error', 45026, null, null, '触发删除用户数的保护');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (132, 'weixin_error', 45028, null, null, '素材数量超过上限');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (135, 'weixin_error', 46002, null, null, '不存在的菜单版本');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (136, 'weixin_error', 46003, null, null, '不存在的菜单数据');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (140, 'weixin_error', 48002, null, null, 'Api禁用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (142, 'weixin_error', 48004, null, null, '授权关系无效');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (143, 'weixin_error', 50001, null, null, 'redirect_uri未授权');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (145, 'weixin_error', 50003, null, null, '应用已停用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (148, 'weixin_error', 60001, null, null, '部门长度不符合限制');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (150, 'weixin_error', 60003, null, null, '部门不存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (151, 'weixin_error', 60004, null, null, '父亲部门不存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (154, 'weixin_error', 60007, null, null, '不允许删除根部门');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (155, 'weixin_error', 60008, null, null, '部门名称已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (157, 'weixin_error', 60010, null, null, '部门存在循环关系');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (160, 'weixin_error', 60013, null, null, '不允许关闭应用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (161, 'weixin_error', 60014, null, null, '不允许开启应用');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (163, 'weixin_error', 60016, null, null, '不允许删除存在成员的标签');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (164, 'weixin_error', 60017, null, null, '不允许设置企业');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (167, 'weixin_error', 60023, null, null, '应用已授权予第三方，不允许通过分级管理员修改菜单');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (170, 'weixin_error', 60104, null, null, '手机号码已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (172, 'weixin_error', 60106, null, null, '邮箱已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (175, 'weixin_error', 60109, null, null, 'QQ号已存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (177, 'weixin_error', 60111, null, null, 'UserID不存在');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (179, 'weixin_error', 60113, null, null, '身份认证信息（微信号/手机/邮箱）不能同时为空');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (184, 'weixin_error', 60119, null, null, '成员已关注');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (186, 'weixin_error', 60121, null, null, '找不到该成员');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (188, 'weixin_error', 60123, null, null, '无效的部门id');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (189, 'weixin_error', 60124, null, null, '无效的父部门id');
commit;
prompt 200 records committed...
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (191, 'weixin_error', 60126, null, null, '创建部门失败');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (192, 'weixin_error', 60127, null, null, '缺少部门id');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (194, 'weixin_error', 80001, null, null, '可信域名没有IPC备案，后续将不能在该域名下正常使用jssdk');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (196, 'weixin_error', 82002, null, null, '不合法的PartyID列表长度');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (198, 'weixin_error', 82004, null, null, '微信版本号过低');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (222, 'user_sys', 1, null, '默认不启用 0  启用为1', '1');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (223, 'user_sys', 2, null, '默认登录密码', '123456');
insert into SYS_CODE (ID, TYPECODE, BM, MC, BZ, VALUE)
values (224, 'user_sys', 3, null, '默认角色', '3');
commit;
prompt 208 records loaded
prompt Loading SYS_RESOURCE...
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (1, '权限管理', null, 0, 1, 'authority', 2, 1);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (2, '资源管理', '/authority/resource.jsp', 5, 1, 'resource', 1, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (8, '部门人员管理', '/sys/departManger.jsp', 4, 1, 'depart', 1, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (9, '数据字典管理', '/sys/sysCodeConfigManager.jsp', 5, 1, 'sysCode', 1, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (10, '新增角色', null, 7, 1, 'addRole', 1, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (11, '修改角色', null, 7, 1, 'updateRole', 2, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (12, '删除角色', null, 7, 1, 'delRole', 3, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (13, '保存操作', null, 7, 1, 'save', 4, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (14, '取消操作', null, 7, 1, 'cancel', 5, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (15, '资源授权', null, 7, 1, 'authorize', 6, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (26, '个人信息管理', '/userManager/getCurrentUser', 6, 1, 'modifyMyData', 1, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (27, '项目通知管理', '/personalInfo/involProject.jsp', 6, 1, 'involProject', 2, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (37, '项目阶段编码管理', null, 36, 1, 'projectCode', 1, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (36, '项目维护', '/project/projectAintenance.jsp', 3, 1, 'ProjectAintenance', 1, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (39, '编辑项目', null, 36, 1, 'editProject', 2, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (40, '新增项目', null, 36, 1, 'addProject', 3, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (41, '删除项目', null, 36, 1, 'removeProject', 4, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (43, '参与项目人员管理', null, 36, 1, 'projectStaff', 5, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (48, '添加项目日志', null, 46, 1, 'addProjectLog', 2, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (46, '项目日志管理', '/project/projectLog.jsp', 3, 1, 'Projectlog', 2, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (47, '编辑项目日志', null, 46, 1, 'editProjectLog', 1, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (49, '删除项目日志', null, 46, 1, 'deleteProjectLog', 3, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (51, '导出excel', null, 46, 1, 'exportExcel', 4, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (54, '基本项目编码管理', '/project/baseCodeManager.jsp', 3, 1, 'baseCode', 3, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (55, '编辑基本项目编码', null, 54, 1, 'editBaseCode', 1, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (56, '删除基本项目编码', null, 54, 1, 'deleteBaseCode', 2, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (58, '新增基本项目编码', null, 54, 1, 'addBaseCode', 3, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (3, '项目管理', null, 0, 1, 'project', 1, 1);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (4, '组织结构管理', null, 0, 1, 'organization', 3, 1);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (5, '系统管理', null, 0, 1, 'sys', 4, 1);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (6, '个人数据管理', null, 0, 1, 'personalInfo', 5, 1);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (7, '角色管理', '/sys/roleManager.jsp', 1, 1, 'role', 2, 2);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (16, '添加字典', null, 9, 1, 'addSysCode', 1, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (17, '修改字典', null, 9, 1, 'editSysCode', 2, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (18, '删除字典', null, 9, 1, 'delSysCode', 3, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (19, '添加部门', null, 8, 1, 'addDepartment', 1, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (20, '删除部门', null, 8, 1, 'delDepartment', 2, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (21, '修改部门', null, 8, 1, 'editDepartment', 3, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (22, '同步部门和用户', null, 8, 1, 'syncDepUser', 4, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (23, '添加用户', null, 8, 1, 'addUser', 5, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (24, '删除用户', null, 8, 1, 'delUser', 6, 3);
insert into SYS_RESOURCE (ID, NAME, URL, PARENT_ID, IS_SHOW, IDENTITY, SORT, WIDTH)
values (25, '修改用户', null, 8, 1, 'editUser', 7, 3);
commit;
prompt 42 records loaded
prompt Loading SYS_ROLE...
insert into SYS_ROLE (ROLE_ID, ROLE_NAME, DESCRIPTION, ROLE)
values (3, '项目参与人', '参与项目的人员', 'participants');
insert into SYS_ROLE (ROLE_ID, ROLE_NAME, DESCRIPTION, ROLE)
values (4, '开发者', '具备所有权限', 'developer');
insert into SYS_ROLE (ROLE_ID, ROLE_NAME, DESCRIPTION, ROLE)
values (6, '项目负责人', '负责项目人员', 'projectleder');
insert into SYS_ROLE (ROLE_ID, ROLE_NAME, DESCRIPTION, ROLE)
values (1, '管理员', '管理系统配置', 'admin');
commit;
prompt 5 records loaded
prompt Loading SYS_ROLE_RESOURCE...
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (1, 1, 1);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (2, 1, 2);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (3, 1, 7);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (4, 1, 15);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (5, 1, 10);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (6, 1, 11);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (7, 1, 12);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (8, 1, 13);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (9, 1, 14);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (10, 1, 4);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (11, 1, 8);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (12, 1, 19);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (13, 1, 20);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (14, 1, 21);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (15, 1, 22);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (16, 1, 23);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (17, 1, 24);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (18, 1, 25);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (19, 1, 6);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (20, 1, 26);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (21, 1, 27);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (22, 3, 3);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (23, 3, 36);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (24, 3, 46);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (25, 3, 47);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (26, 3, 48);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (27, 3, 49);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (28, 3, 51);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (29, 3, 6);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (30, 3, 26);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (31, 3, 27);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (32, 6, 3);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (33, 6, 36);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (34, 6, 37);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (35, 6, 39);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (36, 6, 40);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (37, 6, 41);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (38, 6, 43);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (39, 6, 46);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (40, 6, 47);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (41, 6, 48);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (42, 6, 49);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (43, 6, 51);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (44, 6, 54);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (45, 6, 55);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (46, 6, 56);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (47, 6, 58);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (48, 6, 6);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (49, 6, 26);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (50, 6, 27);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (51, 4, 3);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (52, 4, 36);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (53, 4, 37);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (54, 4, 39);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (55, 4, 40);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (56, 4, 41);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (57, 4, 43);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (58, 4, 46);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (59, 4, 47);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (60, 4, 48);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (61, 4, 49);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (62, 4, 51);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (63, 4, 54);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (64, 4, 55);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (65, 4, 56);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (66, 4, 58);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (67, 4, 1);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (68, 4, 2);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (69, 4, 7);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (70, 4, 10);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (71, 4, 11);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (72, 4, 12);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (73, 4, 13);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (74, 4, 14);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (75, 4, 15);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (76, 4, 4);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (77, 4, 8);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (78, 4, 19);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (79, 4, 20);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (80, 4, 21);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (81, 4, 22);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (82, 4, 23);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (83, 4, 24);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (84, 4, 25);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (85, 4, 5);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (86, 4, 9);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (87, 4, 16);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (88, 4, 17);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (89, 4, 18);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (90, 4, 6);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (91, 4, 26);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (92, 4, 27);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (127, 43, 3);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (128, 43, 36);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (129, 43, 37);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (130, 43, 39);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (131, 43, 40);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (132, 43, 41);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (133, 43, 43);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (134, 43, 46);
commit;
prompt 100 records committed...
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (135, 43, 47);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (136, 43, 48);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (137, 43, 49);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (138, 43, 51);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (139, 43, 54);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (140, 43, 55);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (141, 43, 56);
insert into SYS_ROLE_RESOURCE (ID, ROLE_ID, RESOURCE_ID)
values (142, 43, 58);
commit;
prompt 108 records loaded
prompt Loading SYS_USER...
insert into SYS_USER (TEL, CREATEDATE, LASTLOGDATE, ROLE, LASTPWDDATE, IS_USED, DEVICEID, ID, PWD, NAME, WEIXIN, EMAIL, DEPARTMENT_ID, GENDER, DEPARTMENT_NAME, IMG_URL, ACCOUNT, QUANPIN, JIANPIN)
values (null, null, null, 1, null, 1, null, 0, '123456', '鹳狸猿', null, null, 1, null, '楚天龙武汉研发中心', null, 'admin', null, null);
commit;
prompt 36 records loaded
set feedback on
set define on
prompt Done.
