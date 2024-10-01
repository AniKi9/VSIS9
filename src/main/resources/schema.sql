create table if not exists Shawerma_Order (
    id identity,
    delivery_Name varchar(50) not null,
    delivery_Street varchar(50) not null,
    delivery_City varchar(50) not null,
    delivery_State varchar(2) not null,
    delivery_Zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null
    );
create table if not exists Shawerma (
    id identity,
    name varchar(50) not null,
    shawerma_order bigint not null,
    shawerma_order_key bigint not null,
    created_at timestamp not null
    );
create table if not exists Ingredient_Ref (
    ingredient varchar(4) not null,
    shawerma bigint not null,
    shawerma_key bigint not null
    );
create table if not exists Ingredient (
    id varchar(4) not null,
    name varchar(25) not null,
    type varchar(10) not null
    );
alter table Shawerma
    add foreign key (shawerma_order) references Shawerma_Order(id);
alter table Ingredient
    add primary key (id);
alter table Ingredient_Ref add foreign key (ingredient) references Ingredient(id);