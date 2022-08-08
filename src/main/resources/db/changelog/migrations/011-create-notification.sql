CREATE TABLE notification (
                              id serial PRIMARY KEY,
                              type_id INT,
                              sent_time TIMESTAMP NOT NULL,
                              person_id INT,
                              entity_id INT,
                              contact VARCHAR (255),
                              CONSTRAINT fk_type
                                    FOREIGN KEY (type_id)
                                        REFERENCES notification_type (id),
                              CONSTRAINT fk_person_notification
                                    FOREIGN KEY (person_id)
                                        REFERENCES person (id)
);