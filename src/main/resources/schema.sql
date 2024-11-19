create table if not exists autor
(
    id_autor  smallint generated by default as identity
        primary key,
    biografia varchar(255),
    nombre    varchar(255)
);

alter table autor
    owner to postgres;

create table if not exists categoria
(
    id_categoria     smallint generated by default as identity
        primary key,
    descripcion      varchar(255),
    nombre_categoria varchar(255)
);

alter table categoria
    owner to postgres;

create table if not exists libro
(
    disponibilidad    boolean,
    id_autorfk        smallint
        constraint fkbcxjiqoh1b5vq9ku1yut8cg9v
            references autor,
    id_categoriafk    smallint
        constraint fk4firw8w3dluicgylprytb3oha
            references categoria,
    id_libro          smallint generated by default as identity
        primary key,
    descripcion       varchar(255),
    fecha_publicacion varchar(255),
    titulo            varchar(255)
);

alter table libro
    owner to postgres;

create table if not exists rol
(
    id_role bigint generated by default as identity
        primary key,
    rol     varchar(255) not null
        unique
        constraint rol_rol_check
            check ((rol)::text = ANY ((ARRAY ['ADMIN'::character varying, 'CLIENT'::character varying])::text[]))
);

alter table rol
    owner to postgres;

create table if not exists users
(
    id_user  bigint generated by default as identity
        primary key,
    password varchar(255) not null,
    username varchar(255) not null
        unique
);

alter table users
    owner to postgres;

create table if not exists cliente
(
    estado_cuenta boolean,
    id_cliente    smallint generated by default as identity
        primary key,
    id_userfk     bigint not null
        unique
        constraint fkiby38wsrtfewsynl57qcabkav
            references users,
    correo        varchar(255),
    nombre        varchar(255),
    telefono      varchar(255)
);

alter table cliente
    owner to postgres;

create table if not exists prestamo
(
    fecha_fin_prestamo    date,
    fecha_inicio_prestamo date,
    id_clientefk          smallint
        constraint fk76uh5xcup7exl781dmggk9fih
            references cliente,
    id_prestamo           smallint generated by default as identity
        primary key
);

alter table prestamo
    owner to postgres;

create table if not exists prestamo_libro
(
    id_libro    smallint not null
        constraint fk2lbu8ps67wfnm3mt9vcf7hnca
            references libro,
    id_prestamo smallint not null
        constraint fk7qpfddfr3vpiruqe7fnhggpv6
            references prestamo,
    primary key (id_libro, id_prestamo)
);

alter table prestamo_libro
    owner to postgres;

create table if not exists user_role
(
    id_role bigint not null
        constraint fkj2td0cq10l5g7jvs8wr9tttpm
            references rol,
    id_user bigint not null
        constraint fkr53t650tbjk5yipcm228wf1nc
            references users,
    primary key (id_role, id_user)
);

alter table user_role
    owner to postgres;