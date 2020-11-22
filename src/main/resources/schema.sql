create schema if not exists pay;
use pay;

CREATE TABLE if not exists pay.money_sprinkle
(
id int(10) NOT NULL AUTO_INCREMENT COMMENT '뿌리기 번호',
user_id int(10) NOT NULL COMMENT '뿌린유저',
room_id varchar(30) NOT NULL COMMENT '방 이이디',
amount int(8) NOT NULL COMMENT '뿌린 금액',
target_count int(4) NOT NULL COMMENT '대상 유저수',
token varchar(3) NOT NULL COMMENT '토큰',
sprinkled_time datetime NOT NULL COMMENT '뿌리기 시작일시',
PRIMARY KEY (id)
);

CREATE TABLE if not exists pay.money_sprinkle_item
(
seq int(10) NOT NULL AUTO_INCREMENT COMMENT '뿌리기 아이템 번호',
receiver_id int(10) NULL COMMENT '받은 유저',
amount int(8) NOT NULL COMMENT '받은 금액',
sprinkle_id int(10) NOT NULL COMMENT '뿌리기 번호',
PRIMARY KEY (seq)
);

CREATE TABLE if not exists pay.pay_user
(
user_id int(10) NULL COMMENT '유저 아이디',
PRIMARY KEY (user_id)
);