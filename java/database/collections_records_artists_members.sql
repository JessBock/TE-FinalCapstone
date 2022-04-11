START TRANSACTION;

DROP TABLE IF EXISTS collections;
DROP TABLE IF EXISTS collections_records;
DROP TABLE IF EXISTS records;
DROP TABLE IF EXISTS users_records;
DROP TABLE IF EXISTS artists;
DROP TABLE IF EXISTS members_artists;
DROP TABLE IF EXISTS members; 	

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
	year INT,
	genre varchar (25),
	condition varchar (25),
	disc_size INT,						-- 12 or 7 inch
	speed INT,							-- 33.5 45
	records_comments varchar (500),
	artists_id INT,
	CONSTRAINT PK_records PRIMARY KEY (records_id)
);

CREATE TABLE collections_records(
	collections_id INT,
	records_id INT,
	CONSTRAINT FK_collections_records_collections FOREIGN KEY (collections_id) REFERENCES collections(collections_id),
	CONSTRAINT FK_collections_records_records FOREIGN KEY (records_id) REFERENCES records(records_id),
	CONSTRAINT PK_collections_records PRIMARY KEY (collections_id, records_id)
);

CREATE TABLE users_records(				-- library
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

-- modify tables once all tables are created as necessary
ALTER TABLE users ADD CONSTRAINT FK_users_collections FOREIGN KEY (collections_id) REFERENCES collections(collections_id);
ALTER TABLE collections ADD CONSTRAINT FK_collections_users FOREIGN KEY (user_id) REFERENCES users(user_id);
ALTER TABLE records ADD CONSTRAINT FK_records_artists FOREIGN KEY (artists_id) REFERENCES artists(artists_id);

COMMIT;
