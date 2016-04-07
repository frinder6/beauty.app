INSERT INTO `beauty.one.page`.`beauty_resource` (`name`, `url`, `parent_id`, `remark`) SELECT
	`name`,
	`url`,
	`parent_id`,
	`remark`
FROM
	beauty.`beauty_menu`
WHERE
	parent_id > 0;