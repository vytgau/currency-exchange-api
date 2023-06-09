CREATE TABLE TEXCHANGERATE (
    FROM_CURRENCY VARCHAR(10) NOT NULL,
    TO_CURRENCY VARCHAR(10) NOT NULL,
    EXCHANGE_RATE NUMERIC(64, 32) NOT NULL,

    CONSTRAINT UNIQUE_FROM_TO_CURRENCIES_PAIR UNIQUE (FROM_CURRENCY, TO_CURRENCY)
);

INSERT INTO TEXCHANGERATE (FROM_CURRENCY, TO_CURRENCY, EXCHANGE_RATE) VALUES ('USD', 'EUR', 0.809552722);
INSERT INTO TEXCHANGERATE (FROM_CURRENCY, TO_CURRENCY, EXCHANGE_RATE) VALUES ('GBP', 'EUR', 1.126695);
INSERT INTO TEXCHANGERATE (FROM_CURRENCY, TO_CURRENCY, EXCHANGE_RATE) VALUES ('BTC', 'EUR', 6977.089657);
INSERT INTO TEXCHANGERATE (FROM_CURRENCY, TO_CURRENCY, EXCHANGE_RATE) VALUES ('FKE', 'EUR', 0.025);
INSERT INTO TEXCHANGERATE (FROM_CURRENCY, TO_CURRENCY, EXCHANGE_RATE) VALUES ('ETH', 'EUR', 685.2944747);