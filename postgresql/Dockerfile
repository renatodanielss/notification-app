FROM postgres:16.2

COPY scripts/init-db.sql /docker-entrypoint-initdb.d/
RUN chmod +x /docker-entrypoint-initdb.d/init-db.sql
