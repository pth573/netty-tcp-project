DROP DATABASE IF EXISTS `netty_project`;
CREATE DATABASE  IF NOT EXISTS `netty_project`;
USE `netty_project`;
CREATE TABLE t1 (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    date_time VARCHAR(255),
    type_equip VARCHAR(255),
    version VARCHAR(255),
    serial VARCHAR(255),
    type VARCHAR(255),
    sim_num VARCHAR(255),
    phone_num VARCHAR(255),
    password VARCHAR(255),
    reason_restart VARCHAR(255),
    sequence VARCHAR(255),
    receive_message VARCHAR(500)
);
