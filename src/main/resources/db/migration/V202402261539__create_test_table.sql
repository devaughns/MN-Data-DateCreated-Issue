
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS test (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    date_processed timestamp NOT NULL
);

