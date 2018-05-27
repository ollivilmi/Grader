CREATE TABLE GraderUser (
    id SERIAL PRIMARY KEY,
    username TEXT NOT NULL,
    pw_hash TEXT NOT NULL
);