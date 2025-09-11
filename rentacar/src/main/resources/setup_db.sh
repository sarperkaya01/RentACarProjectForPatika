#!/bin/bash

DBNAME="rent_a_car"
USER="postgres"
SQLFILE="rent_a_car.sql"

# PostgreSQL kurulu mu kontrol et
if ! command -v psql &> /dev/null
then
    echo "PostgreSQL is not installed."

    read -p "Do you want to install PostgreSQL? (y/N): " answer
    if [[ "$answer" =~ ^[Yy]$ ]]; then
        # Homebrew ile PostgreSQL kurulumu
        if command -v brew &> /dev/null; then
            echo "Installing PostgreSQL with Homebrew..."
            brew install postgresql
            brew services start postgresql
        else
            echo "Homebrew is not installed. Please install PostgreSQL manually."
            exit 1
        fi
    else
        echo "Installation aborted."
        exit 1
    fi
fi

# Veritabanı var mı kontrol et
DB_EXIST=$(psql -U $USER -tAc "SELECT 1 FROM pg_database WHERE datname='$DBNAME'")

if [ "$DB_EXIST" = "1" ]; then
    echo "Database $DBNAME already exists."
else
    read -p "Database $DBNAME does not exist. Do you want to create it? (y/N): " create_answer
    if [[ "$create_answer" =~ ^[Yy]$ ]]; then
        echo "Creating database $DBNAME..."
        createdb -U $USER $DBNAME
        psql -U $USER -d $DBNAME -f $SQLFILE
        echo "Database $DBNAME and tables created from $SQLFILE."
    else
        echo "Database creation aborted."
    fi
fi