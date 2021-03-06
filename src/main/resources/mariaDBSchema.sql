CREATE SCHEMA TEA_CATALOG
  AUTHORIZATION SA

  CREATE TABLE TEA_CATALOG.TYPE (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (
    START WITH 1
    INCREMENT BY 1 ) NOT NULL,
    name    CHAR(30)     NOT NULL,
    PRIMARY KEY (id)
  );

CREATE UNIQUE INDEX unique_type on TEA_CATALOG.TYPE (name);

CREATE TABLE TEA_CATALOG.COUNTRY (
  id INTEGER GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1
  INCREMENT BY 1 ) NOT NULL,
  name    CHAR(100)  NOT NULL,
  PRIMARY KEY (id)
  -- unique
);

CREATE UNIQUE INDEX unique_country on TEA_CATALOG.TEA (name);

CREATE TABLE TEA_CATALOG.TEA (
  id        INTEGER GENERATED BY DEFAULT AS IDENTITY (
  START WITH 1
  INCREMENT BY 1 )      NOT NULL,
  peopleEnjoyIt BIGINT NOT NULL,
  type_id       INTEGER NOT NULL,
  country_id    INTEGER NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (type_id) REFERENCES TEA_CATALOG.TYPE (id),
  FOREIGN KEY (country_id) REFERENCES TEA_CATALOG.COUNTRY (id)
);

