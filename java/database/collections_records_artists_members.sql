START TRANSACTION;

DROP TABLE IF EXISTS collections, collections_records, records, users_records, artists, members_artists, members, tracks, genres, records_genres CASCADE;

CREATE TABLE collections(
	collections_id serial,
	collection_name varchar (25),
	count INT,
	share boolean DEFAULT false,
	collection_comments varchar (500),
	user_id INT,
	CONSTRAINT PK_collections PRIMARY KEY (collections_id)
);

CREATE TABLE records(
	records_id serial,
	title varchar (50),
	year DATE,
	condition varchar (25),
	records_comments varchar (500),
	image varchar (250),							-- url link to image
	artists_id INT,									-- FK to artists
	tracks_id INT,									-- FK to song tracks
	genres_id INT,									-- FK to genre
	CONSTRAINT PK_records PRIMARY KEY (records_id)
);

CREATE TABLE collections_records(
	collections_id INT,
	records_id INT,
	CONSTRAINT FK_collections_records_collections FOREIGN KEY (collections_id) REFERENCES collections(collections_id),
	CONSTRAINT FK_collections_records_records FOREIGN KEY (records_id) REFERENCES records(records_id),
	CONSTRAINT PK_collections_records PRIMARY KEY (collections_id, records_id)
);

CREATE TABLE users_records(							-- library
	user_id INT,
	records_id INT,
	CONSTRAINT FK_users_records_users FOREIGN KEY (user_id) REFERENCES users(user_id),
	CONSTRAINT FK_users_records_records FOREIGN KEY (records_id) REFERENCES records(records_id),
	CONSTRAINT PK_users_records PRIMARY KEY (user_id, records_id)
);

CREATE TABLE artists (
	artists_id serial,
	name varchar (50),
	--artists_members varchar
	records_id INT,
	image varchar (250),							-- url link to image
	CONSTRAINT PK_artists PRIMARY KEY (artists_id),
	CONSTRAINT FK_artists_records FOREIGN KEY (records_id) REFERENCES records(records_id)
);

CREATE TABLE members(
	members_id serial,
	name varchar(50),
	CONSTRAINT PK_members PRIMARY KEY (members_id)
);

CREATE TABLE members_artists(
	artists_id INT,
	members_id INT,
	CONSTRAINT FK_members_artists_artists FOREIGN KEY (artists_id) REFERENCES artists(artists_id),
	CONSTRAINT FK_members_artists_members FOREIGN KEY (members_id) REFERENCES members(members_id),
	CONSTRAINT PK_members_artists PRIMARY KEY (artists_id, members_id)
);

CREATE TABLE tracks (
	tracks_id serial,
	name varchar (50),
	records_id INT,
	duration varchar (15),
	position varchar (15),
	CONSTRAINT PK_tracks PRIMARY KEY (tracks_id),
	CONSTRAINT FK_tracks_records FOREIGN KEY (records_id) REFERENCES records(records_id)
);

CREATE TABLE genres(
	genres_id serial,
	name varchar(50),
	CONSTRAINT PK_genres PRIMARY KEY (genres_id)
);

CREATE TABLE records_genres(
	genres_id INT,
	records_id INT,
	CONSTRAINT FK_records_genres_genres FOREIGN KEY (genres_id) REFERENCES genres(genres_id),
	CONSTRAINT FK_records_genres_records FOREIGN KEY (records_id) REFERENCES records(records_id),
	CONSTRAINT PK_records_genres PRIMARY KEY (records_id, genres_id)
);

-- modify tables once all tables are created as necessary
ALTER TABLE users ADD CONSTRAINT FK_users_collections FOREIGN KEY (collections_id) REFERENCES collections(collections_id);
ALTER TABLE collections ADD CONSTRAINT FK_collections_users FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE records ADD CONSTRAINT FK_records_artists FOREIGN KEY (artists_id) REFERENCES artists(artists_id);
ALTER TABLE records ADD CONSTRAINT FK_records_tracks FOREIGN KEY (tracks_id) REFERENCES tracks(tracks_id);
ALTER TABLE records ADD CONSTRAINT FK_records_genres FOREIGN KEY (genres_id) REFERENCES genres(genres_id);

COMMIT;
