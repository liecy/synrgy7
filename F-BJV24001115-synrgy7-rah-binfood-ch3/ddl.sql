-- Table: public.merchant

-- DROP TABLE IF EXISTS public.merchant;

CREATE TABLE IF NOT EXISTS public.merchant
(
    id uuid NOT NULL,
    merchant_name character varying COLLATE pg_catalog."default",
    merchant_location character varying COLLATE pg_catalog."default",
    open boolean,
    CONSTRAINT merchant_pkey PRIMARY KEY (id)
)

-- Table: public.order

-- DROP TABLE IF EXISTS public."order";

CREATE TABLE IF NOT EXISTS public."order"
(
    id uuid NOT NULL,
    order_time date,
    destination_address character varying COLLATE pg_catalog."default",
    user_id uuid,
    completed boolean,
    CONSTRAINT order_pkey PRIMARY KEY (id),
    CONSTRAINT order_user_id_fk FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

-- Table: public.order_detail

-- DROP TABLE IF EXISTS public.order_detail;

CREATE TABLE IF NOT EXISTS public.order_detail
(
    id uuid NOT NULL,
    order_id uuid,
    product_id uuid,
    quantity integer,
    total_price double precision,
    CONSTRAINT order_detail_pkey PRIMARY KEY (id),
    CONSTRAINT order_detail_order_id_fk FOREIGN KEY (order_id)
        REFERENCES public."order" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT order_detail_product_id_fk FOREIGN KEY (product_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

-- Table: public.product

-- DROP TABLE IF EXISTS public.product;

CREATE TABLE IF NOT EXISTS public.product
(
    id uuid NOT NULL,
    product_name character varying COLLATE pg_catalog."default",
    price double precision,
    merchant_id uuid,
    CONSTRAINT product_pkey PRIMARY KEY (id),
    CONSTRAINT product_merchant_id_fk FOREIGN KEY (merchant_id)
        REFERENCES public.merchant (id) MATCH SIMPLE
        ON UPDATE SET DEFAULT
        ON DELETE SET DEFAULT
)

-- Table: public.users

-- DROP TABLE IF EXISTS public.users;

CREATE TABLE IF NOT EXISTS public.users
(
    id uuid NOT NULL,
    username character varying COLLATE pg_catalog."default",
    email_address character varying COLLATE pg_catalog."default",
    password character varying COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)