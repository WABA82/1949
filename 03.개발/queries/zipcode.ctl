LOAD DATA
INFILE "C:\Users\owner\youngRepositories\1949\03.����\temp_query\zipcode.csv"
INTO TABLE zipcode
INSERT
FIELDS TERMINATED BY ','
(
zipcode, sido, gugun, dong, bunji, seq
)