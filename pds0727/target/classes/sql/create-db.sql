----------------------------------------------------------
-- Export file for user log                            --
-- Created by Administrator on 2015/10/13 星期二, 11:12:04 --
----------------------------------------------------------
spool create-db.log

prompt
prompt Creating table BASE_PROJECT_CODE
prompt ================================
prompt
create table BASE_PROJECT_CODE
(
  TYPECODE NUMBER not null,
  MC       VARCHAR2(50)
)
;
comment on column BASE_PROJECT_CODE.TYPECODE
  is '编码';
comment on column BASE_PROJECT_CODE.MC
  is '编码名称';
alter table BASE_PROJECT_CODE
  add constraint BASEPRO_TYPECODE_PK primary key (TYPECODE)
 

prompt
prompt Creating table SYS_CODE
prompt =======================
prompt
create table SYS_CODE
(
  ID       NUMBER not null,
  TYPECODE VARCHAR2(200) not null,
  BM       NUMBER not null,
  MC       VARCHAR2(2000),
  BZ       VARCHAR2(2000),
  VALUE    VARCHAR2(2000)
)
;
comment on column SYS_CODE.ID
  is '字典id';
comment on column SYS_CODE.TYPECODE
  is '类别编码';
comment on column SYS_CODE.BM
  is '字典编码';
comment on column SYS_CODE.MC
  is '字典名称';
comment on column SYS_CODE.BZ
  is '字典备注';
comment on column SYS_CODE.VALUE
  is '字典值';
alter table SYS_CODE
  add constraint SYSCODE_PK primary key (ID)
  
alter table SYS_CODE
  add constraint SYSCODE_TYPE_BM_UQ unique (TYPECODE, BM)
  

prompt
prompt Creating table SYS_DEPARTMENT
prompt =============================
prompt
create table SYS_DEPARTMENT
(
  DEPARTMENT_ID   NUMBER not null,
  DEPARTMENT_NAME VARCHAR2(50),
  PARENT_ID       NUMBER,
  WIDTH           NUMBER,
  HAS_CHILDREN    NUMBER,
  SORT            NUMBER
)
;
comment on column SYS_DEPARTMENT.DEPARTMENT_ID
  is '部门ID';
comment on column SYS_DEPARTMENT.DEPARTMENT_NAME
  is '部门名字';
comment on column SYS_DEPARTMENT.PARENT_ID
  is '父级ID';
comment on column SYS_DEPARTMENT.WIDTH
  is '级别';
comment on column SYS_DEPARTMENT.HAS_CHILDREN
  is '是否有字级';
comment on column SYS_DEPARTMENT.SORT
  is '排序编号';
alter table SYS_DEPARTMENT
  add constraint DEPARTMENT_ID_PK primary key (DEPARTMENT_ID)
  
 

prompt
prompt Creating table SYS_RESOURCE
prompt ===========================
prompt
create table SYS_RESOURCE
(
  ID        NUMBER not null,
  NAME      VARCHAR2(100),
  URL       VARCHAR2(200),
  PARENT_ID NUMBER,
  IS_SHOW   NUMBER,
  IDENTITY  VARCHAR2(100),
  SORT      NUMBER,
  WIDTH     NUMBER
)
;
comment on column SYS_RESOURCE.NAME
  is '资源名称';
comment on column SYS_RESOURCE.URL
  is '页面地址，可为空';
comment on column SYS_RESOURCE.IS_SHOW
  is '是否显示';
comment on column SYS_RESOURCE.IDENTITY
  is '所需操作权限';
comment on column SYS_RESOURCE.SORT
  is '排序';
comment on column SYS_RESOURCE.WIDTH
  is '级别';
alter table SYS_RESOURCE
  add constraint RESOURCE_ID_PK primary key (ID)

  
alter table SYS_RESOURCE
  add constraint RESOURCE_IDENTITY_UQ unique (PARENT_ID, IDENTITY)


prompt
prompt Creating table SYS_ROLE
prompt =======================
prompt
create table SYS_ROLE
(
  ROLE_ID     NUMBER not null,
  ROLE_NAME   VARCHAR2(100),
  DESCRIPTION VARCHAR2(100),
  ROLE        VARCHAR2(100)
)
;
comment on column SYS_ROLE.ROLE_ID
  is '角色ID';
comment on column SYS_ROLE.ROLE_NAME
  is '角色名';
comment on column SYS_ROLE.DESCRIPTION
  is '角色说明';
comment on column SYS_ROLE.ROLE
  is '角色值';
alter table SYS_ROLE
  add constraint ROLE_PK primary key (ROLE_ID)

  
alter table SYS_ROLE
  add constraint ROLE_UQ_NAME unique (ROLE_NAME)

  
alter table SYS_ROLE
  add constraint ROL_UQ_ROLE unique (ROLE)

 

prompt
prompt Creating table SYS_ROLE_RESOURCE
prompt ================================
prompt
create table SYS_ROLE_RESOURCE
(
  ID          NUMBER not null,
  ROLE_ID     NUMBER,
  RESOURCE_ID NUMBER
)
;
comment on column SYS_ROLE_RESOURCE.ID
  is '中间表ID';
comment on column SYS_ROLE_RESOURCE.ROLE_ID
  is '角色ID';
comment on column SYS_ROLE_RESOURCE.RESOURCE_ID
  is '资源ID';
alter table SYS_ROLE_RESOURCE
  add constraint ROLE_RES_ID_PK primary key (ID)

 

prompt
prompt Creating table SYS_USER
prompt =======================
prompt
create table SYS_USER
(
  TEL             VARCHAR2(20),
  CREATEDATE      DATE,
  LASTLOGDATE     DATE,
  ROLE            NUMBER,
  LASTPWDDATE     DATE,
  IS_USED         NUMBER,
  DEVICEID        VARCHAR2(50),
  ID              NUMBER not null,
  PWD             VARCHAR2(50),
  NAME            VARCHAR2(50),
  WEIXIN          VARCHAR2(100),
  EMAIL           VARCHAR2(150),
  DEPARTMENT_ID   NUMBER,
  GENDER          NUMBER,
  DEPARTMENT_NAME VARCHAR2(100),
  IMG_URL         VARCHAR2(255),
  ACCOUNT         VARCHAR2(50),
  QUANPIN         VARCHAR2(50),
  JIANPIN         VARCHAR2(10)
)
;
comment on column SYS_USER.TEL
  is '电话';
comment on column SYS_USER.CREATEDATE
  is '创建时间';
comment on column SYS_USER.LASTLOGDATE
  is '最后登录时间';
comment on column SYS_USER.ROLE
  is '角色';
comment on column SYS_USER.LASTPWDDATE
  is '最后修改密码时间';
comment on column SYS_USER.IS_USED
  is '启用状态  0=不启用 1=启用';
comment on column SYS_USER.DEVICEID
  is '设备id';
comment on column SYS_USER.ID
  is '用户id';
comment on column SYS_USER.PWD
  is '密码';
comment on column SYS_USER.NAME
  is '名字';
comment on column SYS_USER.WEIXIN
  is '微信号';
comment on column SYS_USER.EMAIL
  is '邮箱';
comment on column SYS_USER.DEPARTMENT_ID
  is '部门id';
comment on column SYS_USER.GENDER
  is '性别 1男  0女';
comment on column SYS_USER.DEPARTMENT_NAME
  is '部门名称';
comment on column SYS_USER.IMG_URL
  is '头像url';
comment on column SYS_USER.ACCOUNT
  is '登录账号（微信接口里是ID）';
comment on column SYS_USER.QUANPIN
  is '名字全拼';
comment on column SYS_USER.JIANPIN
  is '名字简拼';
alter table SYS_USER
  add constraint USER_ID_PK primary key (ID)

 
alter table SYS_USER
  add constraint USER_ACCOUNT_UQ unique (ACCOUNT)


prompt
prompt Creating table T_PROJECT
prompt ========================
prompt
create table T_PROJECT
(
  PNAME      VARCHAR2(50),
  PFLAG      NUMBER,
  PINTRO     VARCHAR2(500),
  PERSON     VARCHAR2(200),
  PDATE      DATE,
  LASTPERSON VARCHAR2(20),
  LASTDATE   DATE,
  ISDELETE   NUMBER,
  PID        NUMBER not null
)
;
comment on column T_PROJECT.PNAME
  is '项目名';
comment on column T_PROJECT.PFLAG
  is '项目状态';
comment on column T_PROJECT.PINTRO
  is '项目介绍';
comment on column T_PROJECT.PERSON
  is '立项人';
comment on column T_PROJECT.PDATE
  is '立项时间';
comment on column T_PROJECT.LASTPERSON
  is '最后更新人';
comment on column T_PROJECT.LASTDATE
  is '最后更新时间';
comment on column T_PROJECT.ISDELETE
  is '是否删除  1为删除 0 为正常';
comment on column T_PROJECT.PID
  is '项目ID';
alter table T_PROJECT
  add constraint PID_PRIMARY primary key (PID)

  
alter table T_PROJECT
  add constraint PNAME_UNIQUE unique (PNAME)

  

prompt
prompt Creating table T_PROJECT_CODE
prompt =============================
prompt
create table T_PROJECT_CODE
(
  TYPECODE NUMBER,
  MC       VARCHAR2(40),
  TYPE     VARCHAR2(50),
  PID      NUMBER,
  CID      NUMBER not null
)
;
comment on column T_PROJECT_CODE.TYPECODE
  is '编码';
comment on column T_PROJECT_CODE.MC
  is '名称';
comment on column T_PROJECT_CODE.TYPE
  is '类型';
comment on column T_PROJECT_CODE.PID
  is '项目ID';
comment on column T_PROJECT_CODE.CID
  is '编码ID';
alter table T_PROJECT_CODE
  add constraint CID_PRIMARY primary key (CID)
 
 

prompt
prompt Creating table T_PROJECT_LOG
prompt ============================
prompt
create table T_PROJECT_LOG
(
  PNAME    VARCHAR2(50),
  PERSON   VARCHAR2(50),
  DETAIL   VARCHAR2(2500),
  SDATE    DATE,
  CDATE    DATE,
  ISDELETE NUMBER,
  LID      NUMBER,
  PFLAG    NUMBER,
  CONTENTS CLOB,
  PID      NUMBER
)
;
comment on column T_PROJECT_LOG.PNAME
  is '项目名';
comment on column T_PROJECT_LOG.PERSON
  is '日志更新人';
comment on column T_PROJECT_LOG.DETAIL
  is '日报内容（50字不包含格式）';
comment on column T_PROJECT_LOG.SDATE
  is '日报时间';
comment on column T_PROJECT_LOG.CDATE
  is '提交时间';
comment on column T_PROJECT_LOG.ISDELETE
  is '是否删除 0否 1是';
comment on column T_PROJECT_LOG.LID
  is '日志ID';
comment on column T_PROJECT_LOG.PFLAG
  is '日志 项目阶段';
comment on column T_PROJECT_LOG.CONTENTS
  is '日志详细内容（包含格式）';
comment on column T_PROJECT_LOG.PID
  is '项目ID';

prompt
prompt Creating table T_PROJECT_USER
prompt =============================
prompt
create table T_PROJECT_USER
(
  PID          NUMBER,
  USERID       NUMBER not null,
  DEPARTMENTID NUMBER,
  ISRECEIVE    NUMBER,
  PUID         NUMBER not null,
  PNAME        VARCHAR2(50),
  USERNAME     VARCHAR2(50) not null,
  IS_RESPONSE  NUMBER
)
;
comment on column T_PROJECT_USER.PID
  is '项目ID';
comment on column T_PROJECT_USER.USERID
  is '用户ID';
comment on column T_PROJECT_USER.DEPARTMENTID
  is '部门ID';
comment on column T_PROJECT_USER.ISRECEIVE
  is '是否接受';
comment on column T_PROJECT_USER.PUID
  is '项目人员关系表ID';
comment on column T_PROJECT_USER.PNAME
  is '项目名';
comment on column T_PROJECT_USER.USERNAME
  is 'user姓名';
comment on column T_PROJECT_USER.IS_RESPONSE
  is '1表示为该项目的负责人 0不是';
alter table T_PROJECT_USER
  add constraint PUID_PRIARY primary key (PUID)

 
alter table T_PROJECT_USER
  add constraint UNIQUE_PID_USERID unique (PID, USERID)
 

prompt
prompt Creating sequence CODE_ID_SEQ
prompt =============================
prompt
create sequence CODE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 361
increment by 1
cache 20;

prompt
prompt Creating sequence DEPART_SEQ
prompt ============================
prompt
create sequence DEPART_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence LOG_ID_SEQ
prompt ============================
prompt
create sequence LOG_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence PID_SEQ
prompt =========================
prompt
create sequence PID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence PROJECT_USER_SEQ
prompt ==================================
prompt
create sequence PROJECT_USER_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence RESOURCE_ID_SEQ
prompt =================================
prompt
create sequence RESOURCE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 68
increment by 1
cache 20;

prompt
prompt Creating sequence ROLE_RESOURCE_SEQ
prompt ===================================
prompt
create sequence ROLE_RESOURCE_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 185
increment by 1
cache 20;

prompt
prompt Creating sequence ROLE_SEQ
prompt ==========================
prompt
create sequence ROLE_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 63
increment by 1
cache 20;

prompt
prompt Creating sequence SYSCODE_SEQ
prompt =============================
prompt
create sequence SYSCODE_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 262
increment by 1
cache 20
order;

prompt
prompt Creating sequence USER_SEQ
prompt ==========================
prompt
create sequence USER_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating trigger CODE_ID_TRIGGER
prompt ================================
prompt
create or replace trigger code_id_trigger
before insert on t_project_code
for each row
begin
select code_id_seq.nextval into :new.cid from dual;
end ;
/

prompt
prompt Creating trigger CODE_TRIGGER
prompt =============================
prompt
create or replace trigger code_trigger
before insert on sys_code
for each row
begin
select syscode_seq.nextval into :new.id from dual;
end ;
/

prompt
prompt Creating trigger PROJECT_USER_TRIGGER
prompt =====================================
prompt
create or replace trigger project_user_trigger
before insert on t_project_user
for each row
begin
select project_user_seq.nextval into :new.puid from dual;
end ;
/


spool off
