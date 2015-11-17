-- ============================================================================
-- Initial version
-- ============================================================================

-- http://www.w3schools.com/sql/default.asp

-- ****************************************************************************
-- Company
-- ****************************************************************************

CREATE SEQUENCE SEQ_COMPANY_ID
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE TBL_COMPANY (
    ID BIGINT,
    NAME VARCHAR(255) NOT NULL,
    VERSION INTEGER,
    PRIMARY KEY (ID)
);
