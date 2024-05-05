--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS binarfud_rahmi;
--
-- Name: binarfud_rahmi; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE binarfud_rahmi WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


ALTER DATABASE binarfud_rahmi OWNER TO postgres;

\connect binarfud_rahmi

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: get_all_products(); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.get_all_products()
    LANGUAGE sql
    AS $_$CREATE OR REPLACE PROCEDURE get_all_products()
LANGUAGE SQL
AS $$
BEGIN
    SELECT * FROM product;
END;
$$;$_$;


ALTER PROCEDURE public.get_all_products() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: merchant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merchant (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    merchant_location character varying(255),
    merchant_name character varying(255),
    open boolean
);


ALTER TABLE public.merchant OWNER TO postgres;

--
-- Name: order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_detail (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    quantity integer,
    total_price double precision,
    id_order uuid,
    id_product uuid
);


ALTER TABLE public.order_detail OWNER TO postgres;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    completed boolean,
    destination_address character varying(255),
    order_time timestamp(6) without time zone,
    id_user uuid
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    product_name character varying(255),
    price double precision,
    id_merchant uuid
);


ALTER TABLE public.product OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone NOT NULL,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone NOT NULL,
    email_address character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: merchant; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.merchant VALUES ('dec0be84-1619-49d7-add0-a191573a6652', '2024-05-05 21:41:53.453', NULL, '2024-05-05 21:41:53.453', 'Batu 2, Tanjungpinang', 'Geprek Mas Ucok', true) ON CONFLICT DO NOTHING;
INSERT INTO public.merchant VALUES ('25acc395-2b13-4ffe-b493-ff71ca2ab174', '2024-05-05 21:42:36.551', NULL, '2024-05-05 21:42:36.551', 'Bangau Sakti, Pekanbaru', 'Ampera Rasa Sayang', true) ON CONFLICT DO NOTHING;


--
-- Data for Name: order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.product VALUES ('218f0d71-37ad-49f7-a5ed-a2bdac25beb4', '2024-05-05 22:02:28.088', NULL, '2024-05-05 22:02:28.088', 'Geprek Sambal Matah', 13, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.product VALUES ('c7510681-86ed-48be-88ff-e4de07c59277', '2024-05-05 22:03:17.503', NULL, '2024-05-05 22:03:17.503', 'Geprek Original', 13, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.product VALUES ('29b17b73-af5a-4796-99bd-e886d32de46f', '2024-05-05 22:04:02.821', NULL, '2024-05-05 22:04:02.821', 'Ayam Bumbu', 10, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.product VALUES ('3129d6cd-f302-4d64-9801-ff08173eced7', '2024-05-05 22:04:09.272', NULL, '2024-05-05 22:04:09.272', 'Ayam Kecap', 10, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.product VALUES ('1ec5de86-ad37-453c-8263-a9afa450330c', '2024-05-05 22:04:19.089', NULL, '2024-05-05 22:04:19.089', 'Nila Bakar', 11, NULL) ON CONFLICT DO NOTHING;


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Name: merchant merchant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);


--
-- Name: order_detail order_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: order_detail fkicrtfcntxfkyrnoaqh1croidl; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkicrtfcntxfkyrnoaqh1croidl FOREIGN KEY (id_product) REFERENCES public.product(id);


--
-- Name: product fkr5b0l33kxqkovbqbexr4ub3sc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkr5b0l33kxqkovbqbexr4ub3sc FOREIGN KEY (id_merchant) REFERENCES public.merchant(id);


--
-- Name: order_detail fksta0q8hk1lt2vdu92u4e5vr4a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fksta0q8hk1lt2vdu92u4e5vr4a FOREIGN KEY (id_order) REFERENCES public.orders(id);


--
-- Name: orders fktb6jdc061vu6ydv1445lrigtb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fktb6jdc061vu6ydv1445lrigtb FOREIGN KEY (id_user) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

