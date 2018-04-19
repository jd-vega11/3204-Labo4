--------------------------------------------------------
--  DDL for Table BEBIDAS
--------------------------------------------------------

  CREATE TABLE "BEBIDAS" 
   (	"NOMBRE" VARCHAR2(255 BYTE), 
	"CANTIDAD" NUMBER, 
	"PRECIO" NUMBER
   );
--------------------------------------------------------
--  DDL for Index BEBIDAS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ISIS2304A1051810"."BEBIDAS_PK" ON "ISIS2304A1051810"."BEBIDAS" ("NOMBRE");
--------------------------------------------------------
--  Constraints for Table BEBIDAS
--------------------------------------------------------

  ALTER TABLE "ISIS2304A1051810"."BEBIDAS" MODIFY ("PRECIO" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A1051810"."BEBIDAS" MODIFY ("CANTIDAD" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A1051810"."BEBIDAS" MODIFY ("NOMBRE" NOT NULL ENABLE);
  ALTER TABLE "ISIS2304A1051810"."BEBIDAS" ADD CONSTRAINT "BEBIDAS_PK" PRIMARY KEY ("NOMBRE");
  
INSERT INTO BEBIDAS (NOMBRE,CANTIDAD,PRECIO) VALUES ('AguilAlpes',12,2100);
INSERT INTO BEBIDAS (NOMBRE,CANTIDAD,PRECIO) VALUES ('GuaroAlpes',18,12000);
