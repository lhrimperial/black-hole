
CREATE TABLE `delivery_organization_node` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级节点ID',
  `node_name` varchar(255) NOT NULL DEFAULT '' COMMENT '组织节点名称',
  `node_type` varchar(255) NOT NULL DEFAULT '' COMMENT '节点类型',
  `node_level` varchar(4) NOT NULL DEFAULT '0'  COMMENT '节点层级',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_pid` (`pid`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构树节点';


CREATE TABLE `delivery_organization_node_attribute_mapping` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `node_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织节点id',
  `attribute_key` varchar(255) NOT NULL DEFAULT '' COMMENT '属性key',
  `attribute_value` varchar(5000) NOT NULL DEFAULT '' COMMENT '属性value',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_node_id_attribute_key` (`node_id`,`attribute_key`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构树节点属性表';


CREATE TABLE `delivery_organization_position` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `position_name` varchar(255) NOT NULL DEFAULT '' COMMENT '岗位名称',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构人员岗位表';


CREATE TABLE `delivery_organization_position_rank` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rank_name` varchar(255) NOT NULL DEFAULT '' COMMENT '职级名称',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构人员岗位职级表';


CREATE TABLE `delivery_organization_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名称',
  `work_code` varchar(255) NOT NULL DEFAULT '' COMMENT '用户工号',
  `position_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '岗位ID',
  `position_rank_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '岗位职级ID',
  `user_gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别,1:男,0:女',
  `mobile_no` varchar(255) NOT NULL DEFAULT '' COMMENT '手机号码',
  `user_mail` varchar(255) NOT NULL DEFAULT '' COMMENT '人员邮箱信息',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构人员信息表';


CREATE TABLE `delivery_organization_user_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '人员userId',
  `work_code` varchar(255) NOT NULL DEFAULT '' COMMENT '用户工号',
  `mobile_no` varchar(255) NOT NULL DEFAULT '' COMMENT '手机号码',
  `user_mail` varchar(255) NOT NULL DEFAULT '' COMMENT '人员邮箱信息',
  `password` varchar(255) NOT NULL DEFAULT '' COMMENT '密码',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_user_id` (`user_id`),
  KEY `ix_mobile_no` (`mobile_no`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构人员账号表';


CREATE TABLE `delivery_organization_node_user_mapping` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `node_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织节点id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '人员userId',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_node_id` (`node_id`),
  KEY `ix_user_id` (`user_id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织架构人员关联关系表';




************************************************************************************************************************

CREATE TABLE `delivery_resource_node` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父级ID',
  `resource_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源ID',
  `resource_name` varchar(255) NOT NULL DEFAULT '' COMMENT '资源节点名称',
  `resource_type` varchar(255) NOT NULL DEFAULT '' COMMENT '节点类型',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_pid` (`pid`),
  KEY `ix_resource_id` (`resource_id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源树节点表';


CREATE TABLE `delivery_resource_node_label` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `node_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源节点ID',
  `node_label` varchar(255) NOT NULL DEFAULT '' COMMENT '资源节点标签',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_node_id` (`node_id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源树节点标签表';


CREATE TABLE `delivery_org_role_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `org_node_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织节点ID',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织角色ID',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_org_node_id_role_id` (`org_node_id`,`role_id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织角色资源配置表';


CREATE TABLE `delivery_org_role_config_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织角色配置ID',
  `resource_node_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源节点ID',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_config_id` (`config_id`),
  KEY `ix_resource_node_id` (`resource_node_id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织角色资源配置明细表';


CREATE TABLE `delivery_user_role_config` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织角色ID',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_user_id_role_id` (`user_id`,`role_id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员角色资源配置表';


CREATE TABLE `delivery_user_role_config_detail` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户角色配置ID',
  `resource_node_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '资源节点ID',
  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '软删,1:有效,0:删除',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `drc_check_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '仅供DRC数据校验使用',
  PRIMARY KEY (`id`),
  KEY `ix_user_id_role_id` (`user_id`,`role_id`),
  KEY `ix_updated_at` (`updated_at`),
  KEY `ix_created_at` (`created_at`),
  KEY `ix_drc_check_time` (`drc_check_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员角色资源配置详情表';















