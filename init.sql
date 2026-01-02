CREATE TABLE IF NOT EXISTS Agence (
    numAgence INT PRIMARY KEY,
    telAgence VARCHAR(50),
    adAgence VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Agent (
    numAgent INT PRIMARY KEY,
    nomAgent VARCHAR(50),
    prenomAgent VARCHAR(50),
    salaire DECIMAL(10, 2),
    estDirecteur BOOLEAN,
    numAgence INT REFERENCES Agence(numAgence)
);

CREATE TABLE IF NOT EXISTS Client (
    numClient INT PRIMARY KEY,
    nomClient VARCHAR(50),
    prenomClient VARCHAR(50),
    adClient VARCHAR(100),
    dateNaissClient DATE,
    ageClient INT,
    numAgent INT REFERENCES Agent(numAgent)
);

CREATE TABLE IF NOT EXISTS Compte (
    numCompte INT PRIMARY KEY,
    solde REAL,
    typeCompte VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Operation (
    numOperation INT PRIMARY KEY,
    dateOperation DATE,
    typeOperation VARCHAR(50),
    montant REAL,
    numCompte INT REFERENCES Compte(numCompte)
);


-- Creation Table Compte_Client
CREATE TABLE IF NOT EXISTS Compte_Client (
    numCompte INT
        CONSTRAINT fk_numCompte REFERENCES Compte(numCompte),
    numClient INT
        CONSTRAINT fk_numClient REFERENCES Client(numClient),
    CONSTRAINT pk_Compte_Client PRIMARY KEY (numCompte, numClient)
);

-- Exemple pour la table Client
ALTER TABLE Client
    ALTER COLUMN nomClient SET NOT NULL,
    ALTER COLUMN prenomClient SET NOT NULL,
    ADD CONSTRAINT chk_age CHECK (ageClient >= 18);

-- Exemple pour la table Compte
ALTER TABLE Compte
    ADD CONSTRAINT ck_solde CHECK (solde >= 0);

-- Exemple pour la table Agence
ALTER TABLE Agence
    ADD CONSTRAINT unique_telAgence UNIQUE (telAgence);

-- Exemple pour la table Agent
ALTER TABLE Agent
    ALTER COLUMN estDirecteur SET DEFAULT false;

-- Données de test
INSERT INTO Agence (numAgence, telAgence, adAgence) VALUES (1, '0102030405', 'Paris');
INSERT INTO Agent (numAgent, nomAgent, prenomAgent, salaire, estDirecteur, numAgence) VALUES (1, 'Bond', 'James', 5000, false, 1);