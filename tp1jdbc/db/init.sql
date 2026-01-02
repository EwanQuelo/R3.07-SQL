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
