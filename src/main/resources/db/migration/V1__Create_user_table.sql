CREATE TABLE USER
(
  ID INT AUTO_INCREMENT PRIMARY KEY not null,
  ACCOUNT_ID varchar (100),
  name varchar (50),
  token varchar (36),
  gmt_create BIGINT,
  gmt_modified BIGINT
);