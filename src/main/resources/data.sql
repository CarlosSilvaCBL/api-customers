--drop database if exists api-customers;
--create database api-customers;

drop table if exists customers cascade;
drop sequence if exists customer_sequence;
create sequence customer_sequence start 1 increment 1;

create table customers 
	(id int8 not null, 
	birth_date date not null, 
	document_number varchar(40) not null, 
	document_type varchar(40) not null, 
	name varchar(70) not null, 
	primary key (id));

alter table customers add constraint uk_document_number unique (document_number);


