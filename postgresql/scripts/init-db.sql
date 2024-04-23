SELECT 'CREATE DATABASE notification_db'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'notification_db')\gexec