DROP TABLE IF EXISTS users, event_compilation, events,
    categories, compilations, requests CASCADE;


CREATE TABLE IF NOT EXISTS users
(
    user_id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    email   VARCHAR(50) NOT NULL UNIQUE,
    name    VARCHAR(70) NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    category_id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS compilations
(
    compilation_id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    pinned BOOLEAN NOT NULL,
    title VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS events
(
    event_id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    annotation VARCHAR(512) NOT NULL,
    category_id BIGINT REFERENCES categories(category_id) ON DELETE SET NULL,
    confirmed_requests INT,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    latitude FLOAT NOT NULL,
    longitude FLOAT NOT NULL,
    description VARCHAR(1024),
    event_date TIMESTAMP NOT NULL,
    initiator_id BIGINT REFERENCES users(user_id),
    paid BOOLEAN NOT NULL,
    participant_limit INT NOT NULL,
    published_on TIMESTAMP,
    request_moderation BOOLEAN NOT NULL,
    state VARCHAR(24),
    title VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS comments
(
    comment_id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    text       VARCHAR   NOT NULL,
    author_id  BIGINT REFERENCES users (user_id) ON DELETE CASCADE,
    time       TIMESTAMP NOT NULL,
    event_id   BIGINT REFERENCES events (event_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS event_compilation
(
    compilation_id BIGINT REFERENCES compilations(compilation_id) ON DELETE CASCADE,
    event_id BIGINT REFERENCES events(event_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS requests
(
    request_id BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    created TIMESTAMP NOT NULL,
    event_id BIGINT REFERENCES events(event_id) ON DELETE CASCADE,
    requester_id BIGINT REFERENCES users(user_id) ON DELETE CASCADE,
    status VARCHAR(24) NOT NULL
);