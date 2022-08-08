CREATE TABLE post_like (
                           id serial PRIMARY KEY,
                           time TIMESTAMP NOT NULL,
                           person_id INT,
                           post_id INT,
                           CONSTRAINT fk_person_like
                                FOREIGN KEY (person_id)
                                    REFERENCES person (id),
                           CONSTRAINT fk_like_post
                                FOREIGN KEY (post_id)
                                    REFERENCES post (id)
);