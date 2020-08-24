CREATE TABLE "autorizador" (
    "id" BIGINT AUTO_INCREMENT,
    "ds_nome" VARCHAR(100) NOT NULL
);

ALTER TABLE "autorizador" ADD PRIMARY KEY ("id");

CREATE TABLE "servico" (
    "id" BIGINT AUTO_INCREMENT,
    "ds_nome" VARCHAR(100) NOT NULL
);

ALTER TABLE "servico" ADD PRIMARY KEY ("id");

CREATE TABLE "servico_status" (
    "id" BIGINT AUTO_INCREMENT,
    "id_autorizador" BIGINT NOT NULL,
    "id_servico" BIGINT NOT NULL,
    "dt_consulta" DATE NOT NULL,
    "st_status" VARCHAR(10) NOT NULL
);

ALTER TABLE "servico_status" ADD PRIMARY KEY ("id");
ALTER TABLE "servico_status" ADD FOREIGN KEY ("id_autorizador") REFERENCES "autorizador"("id");
ALTER TABLE "servico_status" ADD FOREIGN KEY ("id_servico") REFERENCES "servico"("id");

CREATE TABLE "servico_status_count" (
    "id" BIGINT AUTO_INCREMENT,
    "id_servico_status" BIGINT NOT NULL,
    "st_status" VARCHAR(10) NOT NULL,
    "qt_status" BIGINT NOT NULL
);

ALTER TABLE "servico_status_count" ADD PRIMARY KEY ("id");
ALTER TABLE "servico_status_count" ADD FOREIGN KEY ("id_servico_status") REFERENCES "servico_status"("id");